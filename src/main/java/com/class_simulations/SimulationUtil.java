package com.class_simulations;

import java.util.List;

// Functions to generate the different event parameters

public class SimulationUtil {

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
