package com.class_simulations;

import com.univocity.parsers.annotations.*;;

public class CustomerRecord {

    @Parsed(field = "Inter-Arrival Time")
    private float interArrivalTime;

    @Parsed(field = "Service Time")
    private float serviceTime;

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private float clockTime;

    private float serviceStartTime;

    private float serviceEndTime;

    private int noInSystem;

    private int noInQueue;

    private float queueWaitTime;

    private float systemTime;
    
    private float serverIdleTime;


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

    public float getQueueWaitTime() {
        return queueWaitTime;
    }

    public void setQueueWaitTime(float queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    public float getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(float systemTime) {
        this.systemTime = systemTime;
    }

    public float getServerIdleTime() {
        return serverIdleTime;
    }

    public void setServerIdleTime(float serverIdleTime) {
        this.serverIdleTime = serverIdleTime;
    }
}
