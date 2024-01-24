package org.model;

import org.model.util.Animal;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Random;

public class AnimalCompere implements Comparator<Animal> {
    @Override
    public int compare(Animal a1, Animal a2){
        if(a1.getEnergy() == a2.getEnergy()){
            if(a1.getDaysAlive()==a2.getDaysAlive()){
                if(a1.getNumberOfKids()==a2.getNumberOfKids()){
                    Random rand = new Random();
                    double randomNum = rand.nextDouble();
                    return randomNum>0.5 ? -1 : 1;
                }
                return a1.getNumberOfKids()>a2.getNumberOfKids() ? -1 : 1;
            }
            return a1.getDaysAlive()>a2.getDaysAlive() ? -1: 1;
        }
        return a1.getEnergy()>a2.getEnergy() ? -1 : 1;
    }
}
