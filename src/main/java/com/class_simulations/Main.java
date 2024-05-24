package com.class_simulations;

import com.codepoetics.protonpack.StreamUtils;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class Main {
        public static void main(String[] args) throws IOException {

                InputStream csvFileStream = com.class_simulations.Main.class.getClassLoader()
                                .getResourceAsStream("values.csv");

                List<CustomerRecord> inputs = readInput(csvFileStream);

                StreamUtils
                                .zipWithIndex(inputs.stream())
                                .forEach((customer) -> {
                                        float prev_clock_time = customer.getIndex() == 0 ? 0
                                                        : inputs.get((int) customer.getIndex() - 1).getClockTime();

                                        float prev_service_end = customer.getIndex() == 0 ? 0
                                                        : inputs.get((int) customer.getIndex() - 1).getServiceEndTime();

                                        float clock_time = SimulationFunctions.SimulationParameters.getArrivalClockTime(
                                                        prev_clock_time,
                                                        customer.getValue().getInterArrivalTime());

                                        float service_start_time = SimulationFunctions.SimulationParameters
                                                        .getServiceStartTime(prev_service_end,
                                                                        clock_time);

                                        float service_end_time = SimulationFunctions.SimulationParameters.getServiceEndTime(
                                                        service_start_time,
                                                        customer.getValue().getServiceTime());

                                        int no_in_system = SimulationFunctions.SimulationParameters.getNumberInSystem(
                                                        inputs.subList(0, (int) customer.getIndex()),
                                                        clock_time);

                                        int no_in_queue = SimulationFunctions.SimulationParameters
                                                        .getNoInQueue(no_in_system);

                                        float queue_wait_time = SimulationFunctions.SimulationParameters.getQueueWaitTime(
                                                        clock_time,
                                                        service_start_time);

                                        float system_time = SimulationFunctions.SimulationParameters.getQueueWaitTime(
                                                        clock_time,
                                                        service_end_time);

                                        float server_idle_time = SimulationFunctions.SimulationParameters.getServerIdleTime(
                                                        prev_service_end,
                                                        clock_time);

                                        customer.getValue().setNumber((int) (customer.getIndex() + 1));
                                        customer.getValue().setClockTime(clock_time);
                                        customer.getValue().setServiceStartTime(service_start_time);
                                        customer.getValue().setServiceEndTime(service_end_time);
                                        customer.getValue().setNoInSystem(no_in_system);
                                        customer.getValue().setNoInQueue(no_in_queue);
                                        customer.getValue().setQueueWaitTime(queue_wait_time);
                                        customer.getValue().setSystemTime(system_time);
                                        customer.getValue().setServerIdleTime(server_idle_time);
                                });
                printTable(inputs);
                printPerfomanceStats(inputs);
        }

        private static List<CustomerRecord> readInput(InputStream csvFileStream) throws IOException {
                try (Reader reader = new InputStreamReader(csvFileStream);) {

                        BeanListProcessor<CustomerRecord> rowProcessor = new BeanListProcessor<CustomerRecord>(
                                        CustomerRecord.class);

                        CsvParserSettings parserSettings = new CsvParserSettings();
                        parserSettings.getFormat().setLineSeparator("\n");
                        parserSettings.setProcessor(rowProcessor);
                        parserSettings.setHeaderExtractionEnabled(true);

                        CsvParser parser = new CsvParser(parserSettings);
                        parser.parse(reader);

                        return rowProcessor.getBeans();

                }
        }

        static void printTable(List<CustomerRecord> customerRows) {

                System.out.println(AsciiTable.getTable(customerRows, Arrays.asList(
                                new Column().header("Customer")
                                                .with(customer -> Integer.toString(customer.getNumber())),
                                new Column().header("IAT").with(
                                                customer -> String.format("%.01f", customer.getInterArrivalTime())),
                                new Column().header("Clock Time")
                                                .with(customer -> String.format("%.01f", customer.getClockTime())),
                                new Column().header("Service Time")
                                                .with(customer -> String.format("%.01f", customer.getServiceTime())),
                                new Column().header("Service Start")
                                                .with(customer -> String.format("%.01f",
                                                                customer.getServiceStartTime())),
                                new Column().header("Service End")
                                                .with(customer -> String.format("%.01f", customer.getServiceEndTime())),
                                new Column().header("No. in System")
                                                .with(customer -> Integer.toString(customer.getNoInSystem())),
                                new Column().header("No. in Queue")
                                                .with(customer -> Integer.toString(customer.getNoInQueue())))));

                System.out.println(AsciiTable.getTable(customerRows, Arrays.asList(
                                new Column().header("Customer")
                                                .with(customer -> Integer.toString(customer.getNumber())),
                                new Column().header("Queue Wait Time").with(
                                                customer -> String.format("%.01f", customer.getQueueWaitTime())),
                                new Column().header("Time in System")
                                                .with(customer -> String.format("%.01f", customer.getSystemTime())),
                                new Column().header("Server Idle Time")
                                                .with(customer -> String.format("%.01f", customer.getServerIdleTime())))));
        }

        static void printPerfomanceStats(List<CustomerRecord> inputs) {
                List<Float> wait_times = inputs.stream().map((customer) -> customer.getQueueWaitTime()).toList();
                List<Float> idle_times = inputs.stream().map((customer) -> customer.getServerIdleTime()).toList();
                List<Float> service_times = inputs.stream().map((customer) -> customer.getServiceTime()).toList();
                List<Float> system_times = inputs.stream().map((customer) -> customer.getSystemTime()).toList();
                List<Float> inter_arrival_times = inputs.stream().map((customer) -> customer.getInterArrivalTime())
                                .toList();

                System.out.println("\nSimulation model performance stats:\n");

                System.out.println(String.format("Average wait time: %.02f",
                                SimulationFunctions.PerformanceStatistics.getAverageWaitTime(wait_times)));

                System.out.println(String.format("Probability of waiting: %.02f",
                                SimulationFunctions.PerformanceStatistics.getWaitingProbability(wait_times)));

                System.out.println(String.format("Proportion of Server Idle Time: %.02f",
                                SimulationFunctions.PerformanceStatistics.getIdleTimeProportion(idle_times,
                                                inputs.get(inputs.size() - 1).getServiceEndTime())));
                System.out.println(String.format("Average Service Time: %.02f",
                                SimulationFunctions.PerformanceStatistics.getAverageServiceTime(service_times)));

                System.out.println(String.format("Average Waiting Time (for those that wait): %.02f",
                                SimulationFunctions.PerformanceStatistics
                                                .getWaitingCustomersAvereageWaitingTime(wait_times)));

                System.out.println(String.format("Average Time Spent In System: %.02f",
                                SimulationFunctions.PerformanceStatistics.getAverageSystemTime(system_times)));

                System.out.println(String.format("Average Time Between Arrivals: %.02f",
                                SimulationFunctions.PerformanceStatistics.getAverageInterArrivalTime(inter_arrival_times)));
        }
}