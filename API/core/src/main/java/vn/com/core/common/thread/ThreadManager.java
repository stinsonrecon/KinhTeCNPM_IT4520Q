package vn.com.core.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ThreadManager {
    public final int BATCH_SIZE = 10;
    public final long WAIT_TIME_OUT = 1000; // 1 seconds
    public final long TIME_OUT = 2 * 1000; // 2 seconds
    private final Logger logger = LoggerFactory.getLogger(ThreadManager.class);
    private final BlockingQueue sourceQueue = new LinkedBlockingQueue();
    protected ArrayList items = new ArrayList(BATCH_SIZE);
    protected AtomicBoolean shouldWork = new AtomicBoolean(true);
    protected AtomicBoolean isRunning = new AtomicBoolean(true);
    private boolean listening = false;
    protected ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Thread mainThread;

    public ThreadManager() {
        logger.debug("Start task manager named: " + getName());
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Queued job manager " + getName() + " is running and watching for queue... ");
                isRunning.set(true);
                int recNum = 0;
                long lgnStart = System.currentTimeMillis();
                while (shouldWork.get()) {
                    try {
                        Object item = sourceQueue.poll(WAIT_TIME_OUT, TimeUnit.MILLISECONDS);
                        if (item != null) {
                            items.add(item);
                            recNum++;
                        }

                        if (recNum >= BATCH_SIZE || timedOut(lgnStart)) {
                            if (items.size() > 0) {
                                logger.info(String.format("Thread %s: %s submits %d item(s)",
                                        Thread.currentThread().getName(), getName(), items.size()));
                                doProcess(items);
                                items = new ArrayList(BATCH_SIZE);
                                lgnStart = System.currentTimeMillis();
                                recNum = 0;
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    isRunning.set(false);
                }
                logger.info("Taskmanager " + getName() + " is stopped!!");
            }

            private boolean timedOut(Long startTime) {
                return System.currentTimeMillis() - startTime > TIME_OUT;
            }
        });

    }

    public abstract void doProcess(ArrayList items);

    public abstract String getName();

    public synchronized void listen() {
        if (!listening) {
            mainThread.start();
            listening = true;
        }
    }

    public BlockingQueue getSourceQueue() {
        return sourceQueue;
    }

    public void stop() {
        logger.info(String.format("%s received a termination signal, stopping ... ", getName()));
        this.shouldWork.set(false);
        int tryTime = 0;
        while (isRunning.get() && tryTime < 50) {
            try {
                Thread.currentThread().sleep(50L);
            } catch (Exception ex) {

            }
            tryTime++;
        }
    }

    public void submit(Object item) {
        sourceQueue.offer(item);
    }
}

