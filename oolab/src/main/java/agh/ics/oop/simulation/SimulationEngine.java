package agh.ics.oop.simulation;

import java.util.List;

public class SimulationEngine
{
    private final List<Simulation> simulations;
    public SimulationEngine(List<Simulation> simulations) {this.simulations = simulations;}
    public void runSync() {simulations.forEach(Simulation::run);}
}
