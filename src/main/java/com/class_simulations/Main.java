package com.class_simulations;

import com.codepoetics.protonpack.StreamUtils;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        InputStream csvFileStream = com.class_simulations.Main.class.getClassLoader().getResourceAsStream("values.csv");

        ArrayList<CustomerInput> inputs = readInput(csvFileStream);

        StreamUtils
                .zipWithIndex(inputs.stream())
                .forEach((customer) -> {
                    float prev_clock_time = customer.getIndex() == 0 ? 0 : inputs.get((int) customer.getIndex() - 1).getClockTime();

                    float prev_service_end = customer.getIndex() == 0 ? 0
                            : inputs.get((int) customer.getIndex() - 1).getServiceEndTime();

                    float clock_time = SimulationUtil.getArrivalClockTime(prev_clock_time,
                            customer.getValue().getInterArrivalTime());

                    float service_start_time = SimulationUtil.getServiceStartTime(prev_service_end, clock_time);

                    float service_end_time = SimulationUtil.getServiceEndTime(service_start_time,
                            customer.getValue().getServiceTime());

                    int no_in_system = SimulationUtil.getNumberInSystem(inputs.subList(0, (int) customer.getIndex()),
                            clock_time);

                            int no_in_queue = SimulationUtil.getNoInQueue(no_in_system);

                    System.out.println(
                            "Index:" + (customer.getIndex() + 1) + ", P_CT:" + prev_clock_time + ", P_SE:" + prev_service_end
                                    + ", C_SS:" + service_start_time + ", C_SE:" + service_end_time + ", C_NS:"
                                    + no_in_system);

                    customer.getValue().setClockTime(clock_time);
                    customer.getValue().setServiceStartTime(service_start_time);
                    customer.getValue().setServiceEndTime(service_end_time);
                    customer.getValue().setNoInSystem(no_in_system);
                    customer.getValue().setNoInQueue(no_in_queue);
                });

    }

    private static ArrayList<CustomerInput> readInput(InputStream csvFileStream) throws IOException {
        try (Reader reader = new InputStreamReader(csvFileStream);) {

            BeanListProcessor<CustomerInput> rowProcessor = new BeanListProcessor<CustomerInput>(CustomerInput.class);

            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setProcessor(rowProcessor);
            parserSettings.setHeaderExtractionEnabled(true);

            CsvParser parser = new CsvParser(parserSettings);
            parser.parse(reader);

            return (ArrayList<CustomerInput>) rowProcessor.getBeans();

        }
    }
}