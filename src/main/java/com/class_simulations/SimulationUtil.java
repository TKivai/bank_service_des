package com.class_simulations;

import java.util.List;


public class SimulationUtil {

    // Functions to generate the different event parameters

    class SimulationParameters {
        static float getArrivalClockTime(float prev_clock_time, float curr_customer_iat) {
            return prev_clock_time + curr_customer_iat;
        }

        static float getServiceStartTime(float prev_service_end, float curr_arrival_time) {
            return Float.max(prev_service_end, curr_arrival_time);
        }

        static float getServiceEndTime(float service_start_time, float service_time) {
            return service_start_time + service_time;
        }

        static int getNumberInSystem(List<CustomerInput> prev_inputs, float clock_time) {
            if (!prev_inputs.isEmpty()) {
                return (int) prev_inputs.stream().filter(i -> i.getServiceEndTime() > clock_time).count() + 1;
            } else
                return 1;
        }

        static int getNoInQueue(int no_in_system) {
            return no_in_system - 1;
        }

        static float getQueueWaitTime(float clock_time, float service_start_time) {
            return service_start_time - clock_time;
        }

        static float getSystemTime(float clock_time, float service_end_time) {
            return service_end_time - clock_time;
        }

        static float getServerIdleTime(float prev_service_end, float clock_time) {
            if (prev_service_end == 0) {
                return Float.valueOf(0).floatValue();
            } else {
                return Math.max(0, (clock_time - prev_service_end));
            }
        }
    }

    // Functions to generate the simulation performance statistics

    class PerformanceStatistics {
        public static float getAverageWaitTime(List<Float> wait_times) {
            float total_wait_times = (float) wait_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_wait_times / wait_times.size());
        }

        public static float getWaitingProbability(List<Float> wait_times) {
            int waited_count = (int) wait_times.stream().filter(time -> time > 0).count();
            return ((float) waited_count / wait_times.size());
        }

        public static float getIdleTimeProportion(List<Float> server_idle_times, float server_run_time) {
            float total_idle_time = (float) server_idle_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_idle_time / server_run_time);
        }

        public static float getAverageServiceTime(List<Float> service_times) {
            float total_service_times = (float) service_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_service_times / service_times.size());
        }

        public static float getWaitingCustomersAvereageWaitingTime(List<Float> wait_times) {
            int waited_count = (int) wait_times.stream().filter(time -> time > 0).count();
            float total_wait_times = (float) wait_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_wait_times / waited_count);
        }

        public static float getAverageSystemTime (List<Float> system_times) {
            float total_system_times = (float) system_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_system_times / system_times.size());
        }

        public static float getAverageInterArrivalTime (List<Float> inter_arrival_times) {
            float total_inter_arrival_times = (float) inter_arrival_times.stream().mapToDouble(time -> time.doubleValue()).sum();
            return (total_inter_arrival_times / (inter_arrival_times.size() - 1));
        }
    }

}
