package com.class_simulations;

import com.univocity.parsers.annotations.*;;

public class CustomerInput {

    @Parsed(field = "Inter-Arrival Time")
    private float interArrivalTime;

    @Parsed(field = "Service Time")
    private float serviceTime;

    private float clockTime;

    private float serviceStartTime;

    private float serviceEndTime;

    private int noInSystem;

    private int noInQueue;

    private int queueWaitTime;

    private int systemTime;
    
    private int serverIdleTime;


    public int getNoInSystem() {
        return noInSystem;
    }

    public void setNoInSystem(int noInSystem) {
        this.noInSystem = noInSystem;
    }

    public float getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(float serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public float getClockTime() {
        return clockTime;
    }

    public void setClockTime(float clockTime) {
        this.clockTime = clockTime;
    }

    public float getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(float serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public String getInputValues() {
        return String.format("""
                    Inter_arrival_time: %s,
                    Service_time: %s,
                    Clock_time: %s,
                """, interArrivalTime, serviceTime, clockTime);
    }

    public float getArrivalTime() {
        return serviceTime;
    }

    public float getInterArrivalTime() {
        return interArrivalTime;
    }

    public void setInterArrivalTime(float interArrivalTime) {
        this.interArrivalTime = interArrivalTime;
    }

    public float getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(float serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getNoInQueue() {
        return noInQueue;
    }

    public void setNoInQueue(int noInQueue) {
        this.noInQueue = noInQueue;
    }

    public int getQueueWaitTime() {
        return queueWaitTime;
    }

    public void setQueueWaitTime(int queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    public int getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(int systemTime) {
        this.systemTime = systemTime;
    }

    public int getServerIdleTime() {
        return serverIdleTime;
    }

    public void setServerIdleTime(int serverIdleTime) {
        this.serverIdleTime = serverIdleTime;
    }

}
