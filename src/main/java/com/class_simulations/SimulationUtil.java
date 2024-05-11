package com.class_simulations;

import java.util.List;

public class SimulationUtil {

    static float getArrivalClockTime(float prev_clock_time, float curr_customer_iat) {
        // return Float.sum(prev_clock_time, curr_customer_iat);
        return prev_clock_time + curr_customer_iat;
    }

    static float getServiceStartTime(float prev_service_end, float curr_arrival_time) {
        return Float.max(prev_service_end, curr_arrival_time);
    }

    static float getServiceEndTime(float service_start_time, float service_time) {
        // return Float.sum(service_start_time, service_time);
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

    static float queueWaitTime(float clock_time, float service_start_time) {
        return service_start_time - clock_time;
    }

}
