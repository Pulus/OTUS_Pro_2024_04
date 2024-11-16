import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DecimalPrinter {
    private static final Logger log = LoggerFactory.getLogger(DecimalPrinter.class);
    private final Lock lock = new ReentrantLock();
    private String last = "Second";

    public static void main(String[] args) {
        DecimalPrinter printer = new DecimalPrinter();
        new Thread(() -> printer.action("First")).start();
        halfSleep();
        new Thread(() -> printer.action("Second")).start();
    }

    private void action(String threadName) {
        int value = 1;
        boolean isIncrement = true;

        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                if (!threadName.equals(last)) {
                    log.info("Thread name: {}. Value: {}", threadName, value);
                    last = threadName;

                    if (value == 10) {
                        isIncrement = false;
                    }

                    if (value == 1) {
                        isIncrement = true;
                    }

                    value = isIncrement ? value + 1 : value - 1;
                }

            } finally {
                lock.unlock();
            }

            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    private static void halfSleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }
}