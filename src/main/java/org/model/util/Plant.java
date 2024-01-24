package org.model.util;

import org.model.WorldElement;

public class Plant extends WorldElement {
    private int energy;
    private boolean alive;

    public Plant(Vector2d position, int energy){
        this.position = position;
        this.energy = energy;
        this.alive = true;
    }

    public Plant(Vector2d position){
        this(position, 1);
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isAlive() {
        return alive;
    }

    public String toString(){
        return "*";
    }
}
