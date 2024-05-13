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
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
        public static void main(String[] args) throws IOException {

                InputStream csvFileStream = com.class_simulations.Main.class.getClassLoader()
                                .getResourceAsStream("values.csv");

                ArrayList<CustomerInput> inputs = readInput(csvFileStream);

                StreamUtils
                                .zipWithIndex(inputs.stream())
                                .forEach((customer) -> {
                                        float prev_clock_time = customer.getIndex() == 0 ? 0
                                                        : inputs.get((int) customer.getIndex() - 1).getClockTime();

                                        float prev_service_end = customer.getIndex() == 0 ? 0
                                                        : inputs.get((int) customer.getIndex() - 1).getServiceEndTime();

                                        float clock_time = SimulationUtil.getArrivalClockTime(prev_clock_time,
                                                        customer.getValue().getInterArrivalTime());

                                        float service_start_time = SimulationUtil.getServiceStartTime(prev_service_end,
                                                        clock_time);

                                        float service_end_time = SimulationUtil.getServiceEndTime(service_start_time,
                                                        customer.getValue().getServiceTime());

                                        int no_in_system = SimulationUtil.getNumberInSystem(
                                                        inputs.subList(0, (int) customer.getIndex()),
                                                        clock_time);

                                        int no_in_queue = SimulationUtil.getNoInQueue(no_in_system);

                                        float queue_wait_time = SimulationUtil.getQueueWaitTime(clock_time,
                                                        service_start_time);

                                        float system_time = SimulationUtil.getQueueWaitTime(clock_time,
                                                        service_end_time);

                                        float server_idle_time = SimulationUtil.getServerIdleTime(prev_service_end,
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
        }

        private static ArrayList<CustomerInput> readInput(InputStream csvFileStream) throws IOException {
                try (Reader reader = new InputStreamReader(csvFileStream);) {

                        BeanListProcessor<CustomerInput> rowProcessor = new BeanListProcessor<CustomerInput>(
                                        CustomerInput.class);

                        CsvParserSettings parserSettings = new CsvParserSettings();
                        parserSettings.getFormat().setLineSeparator("\n");
                        parserSettings.setProcessor(rowProcessor);
                        parserSettings.setHeaderExtractionEnabled(true);

                        CsvParser parser = new CsvParser(parserSettings);
                        parser.parse(reader);

                        return (ArrayList<CustomerInput>) rowProcessor.getBeans();

                }
        }

        static void printTable(ArrayList<CustomerInput> customerRows) {

                System.out.println(AsciiTable.getTable(customerRows, Arrays.asList(
                                new Column().with(customer -> Integer.toString(customer.getNumber())),
                                new Column().header("IAT").with(
                                                customer -> String.format("%.01f", customer.getInterArrivalTime())),
                                new Column().header("Clock Time")
                                                .with(customer -> String.format("%.01f", customer.getClockTime())),
                                new Column().header("Service Time")
                                                .with(customer -> String.format("%.01f", customer.getServiceTime())),
                                new Column().header("Service Start")
                                                .with(customer -> String.format("%.01f", customer.getServiceStartTime())),
                                                new Column().header("Service End")
                                                .with(customer -> String.format("%.01f", customer.getServiceEndTime())),
                                                new Column().header("No. in System")
                                                .with(customer -> Integer.toString(customer.getNoInSystem())),
                                                new Column().header("No. in Queue")
                                                .with(customer -> Integer.toString(customer.getNoInQueue()))
                                                )));
        }
}