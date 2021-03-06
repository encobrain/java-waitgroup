package com.github.encobrain.waitgroup;

public class WaitGroup {

    public static class NegativeCounterException extends Exception {
        public final int counter;

        NegativeCounterException (int counter) {
            super(String.format("Counter < 0 : %d", counter));
            this.counter = counter;
        }
    }

    protected int counter = 0;

    synchronized public void add (int delta) throws NegativeCounterException {
        counter += delta;

        if (counter < 0) throw new NegativeCounterException(counter);

        if (counter == 0) notifyAll();
    }

    public void done () throws NegativeCounterException {
        add(-1);
    }

    synchronized public void await () throws InterruptedException {
        while (counter>0) wait();
    }
}
