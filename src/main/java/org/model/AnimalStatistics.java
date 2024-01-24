package org.model;

import org.model.util.Animal;
import org.model.util.MapDirection;

import java.util.ArrayList;
import java.util.List;

public class AnimalStatistics {
    private Animal current_animal;
    private List<Integer> AnimalGenom = new ArrayList<>();
    private int genoIndex = -1;
    private int currentEnergy = -1;
    private int kidNumber = 0;
    private int daysAlive = 0;
    private int deadDay = -1;

    public void setCurrent_animal(Animal current_animal) {
        this.current_animal = current_animal;
    }

    public Animal getCurrent_animal() {
        return current_animal;
    }

    public void setAnimalGenom(List<Integer> animalGenom) {
        AnimalGenom = animalGenom;
    }

    public List<Integer> getAnimalGenom() {
        return AnimalGenom;
    }





    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getKidNumber() {
        return kidNumber;
    }

    public void setKidNumber() {
        kidNumber = current_animal.getChildren().size();
    }

    public int getDaysAlive() {
        return daysAlive;
    }

    public void setDaysAlive(int daysAlive) {
        this.daysAlive = daysAlive;
    }

    public void setDeadDay(int deadDay) {
        this.deadDay = deadDay;
    }

    public int getDeadDay() {
        return deadDay;
    }

    public int getGenoIndex() {
        return genoIndex;
    }

    public void setGenoIndex() {
        this.genoIndex = current_animal.getGenotype().get((current_animal.getCurrentGenoNum()));
    }

    public void updateStats(Animal animal){
        setCurrent_animal(animal);
        setKidNumber();
        setAnimalGenom(animal.getGenotype());
        setGenoIndex();
        setDeadDay(animal.getDeadDate());
        setDaysAlive(animal.getDaysAlive());
        setCurrentEnergy(animal.getEnergy());
    }
}