package com.github.encobrain.waitgroup;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitGroup {

    public static class NegativeCounterException extends Exception {
        public final int counter;

        NegativeCounterException (int counter) {
            super(String.format("Counter < 0 : %d", counter));
            this.counter = counter;
        }
    }

    protected AtomicInteger counter = new AtomicInteger(0);

    synchronized public void add (int delta) throws NegativeCounterException {
        delta = counter.addAndGet(delta);

        if (delta < 0) throw new NegativeCounterException(delta);

        if (delta == 0) notifyAll();
    }

    public void done () throws NegativeCounterException {
        add(-1);
    }

    synchronized public void await () throws InterruptedException {
        while (counter.get()>0) wait();
    }
}
