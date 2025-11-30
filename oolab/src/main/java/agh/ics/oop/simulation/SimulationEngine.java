package agh.ics.oop.simulation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine
{
    private final static int THREADS_CNT = 4;
    private final List<Simulation> simulations;
    private List<Thread> threads;
    private boolean started = false;
    private ExecutorService threadPool;

    public SimulationEngine(List<Simulation> simulations) {this.simulations = simulations;}
    private void assertNotStarted()
    {
        if (started) {
            throw new IllegalStateException("SimulationEngine is already running or has been run.");
        }
        started = true;
    }
    public void runSync()
    {
        assertNotStarted();
        simulations.forEach(Simulation::run);
    }

    public void runAsync()
    {
        assertNotStarted();
        threads = simulations.stream()
                .map(Thread::new)
                .toList();
        threads.forEach(Thread::start);
    }
    public void runAsyncInThreadPool()
    {
        assertNotStarted();
        threadPool = Executors.newFixedThreadPool(THREADS_CNT);
        simulations.forEach(threadPool::submit);
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        if (threads != null) {
            for (Thread thread : threads) {
                thread.join();
            }
        }

        if (threadPool != null) {
            threadPool.shutdown();
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        }
    }
}
