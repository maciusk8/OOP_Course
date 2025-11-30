package agh.ics.oop.simulation;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulationEngine
{
    private final List<Simulation> simulations;
    private List<Thread> threads;
    public SimulationEngine(List<Simulation> simulations) {this.simulations = simulations;}
    public void runSync() {simulations.forEach(Simulation::run);}
    public void runAsync()
    {
        threads = simulations.stream()
                .map(Thread::new)
                .toList();
        threads.forEach(Thread::start); //mógłbym w teorii robić to w streamie na górze ale chyba warto to odseparować
    }
    public void awaitSimulationsEnd() throws InterruptedException{
        if (threads == null){
            throw new IllegalStateException("Simulations are not running on Threads.");
        }
        for (Thread thread : threads) { thread.join();} //tutaj pętla jest czytelniejsza bo join rzuca wyjątek
    }

}
