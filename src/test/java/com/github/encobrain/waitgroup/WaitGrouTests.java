package com.github.encobrain.waitgroup;

import org.junit.Test;

public class WaitGrouTests {

    @Test
    public void Negative () throws Exception {
        WaitGroup wg = new WaitGroup();

        try { wg.done(); } catch (WaitGroup.NegativeCounterException ignored) { }
    }

    @Test
    public void NoWaitOnZero () throws Exception {
        WaitGroup wg = new WaitGroup();

        wg.await();
    }

    @Test
    public void Await () throws Exception {
        final WaitGroup wg = new WaitGroup();

        final byte[] i = new byte[] { 0 };

        wg.add(2);

        new Thread() {
            public void run () {
                try {
                    Thread.sleep(100);
                    i[0] = (byte) (i[0]+1);
                    wg.done();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        }.start();

        new Thread() {
            public void run () {
                try {
                    Thread.sleep(100);
                    i[0] = (byte) (i[0]+1);
                    wg.done();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        }.start();

        wg.await();

        if (i[0] == 0) throw new Exception("await not work");
    }
}
