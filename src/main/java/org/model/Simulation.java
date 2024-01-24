package org.model;

import org.model.map.WorldMap;

public class Simulation implements Runnable {
    boolean isSimulationStopped;
    boolean isSimulationEnded;
    WorldMap map;
    private final Object pauseLock = new Object();

    public Simulation(WorldMap map){
        isSimulationStopped = false;
        isSimulationEnded = false;
        this.map = map;
        map.putStartAnimals();
        map.putStartPlants();
    }

    public void run(){
        while (!isSimulationEnded){
            if (map.getAnimals().isEmpty()) break;
            synchronized (pauseLock){
                if (isSimulationStopped){
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e){
                        return;
                    }
                }
            }
            map.removeDeadAnimals();
            map.reproduce();
            map.moveAnimals();
            map.removeDeadAnimals();
            map.eat();
            map.plantNewPlants();
            map.getStatistics().updateAllStats();
//            System.out.println(map.getAnimals());
            map.notifyObservers();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("jooo");
        map.notifyObservers();
    }

    public void stopSimulation(){
        isSimulationEnded = true;
    }

    public void pauseSimulation(){
        isSimulationStopped = true;
    }

    public void unpauseSimulation(){
        synchronized (pauseLock){
            isSimulationStopped = false;
            pauseLock.notify();
        }
    }
}
