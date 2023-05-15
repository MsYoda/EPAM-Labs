package com.javalabs.univer.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestsCounter {
    private AtomicInteger countOfRequests;
    private AtomicInteger atomicCounter;
    private Integer simpleCounter = 0;

    public RequestsCounter()
    {
        countOfRequests = new AtomicInteger(0);
        atomicCounter = new AtomicInteger(0);
    }

    public void clearCounters()
    {
        countOfRequests.set(0);
        atomicCounter.set(0);
        simpleCounter = 0;
    }

    public AtomicInteger getAtomicCounter() {
        return atomicCounter;
    }

    public void addAtomicCounter() {
        this.atomicCounter.getAndAdd(1);
    }
    public synchronized int getCountOfRequests() {
        return countOfRequests.get();
    }

    public synchronized void addRequest() {
        countOfRequests.getAndAdd(1);
    }

    public void simpleAdd()
    {
        simpleCounter++;
    }

    public int simpleGet()
    {
        return simpleCounter;
    }



}
