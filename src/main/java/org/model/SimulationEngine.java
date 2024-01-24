package org.model;

import java.util.List;

public class SimulationEngine implements Runnable{

    private final List<Simulation> simulations;

    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
    }
    @Override
    public void run() {}

    public void runAsync(){
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            thread.start();
        }
    }
}
