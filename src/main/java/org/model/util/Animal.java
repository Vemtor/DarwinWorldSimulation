package org.model.util;

import org.model.WorldElement;
import org.model.WorldSettings;
import org.model.map.WorldMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Animal extends WorldElement {

    private static final int energyNeededToReproduce = 10;
    private Vector2d position;
    private int energy;
    private ArrayList<Animal> children;
    private MapDirection direction;
    private int eatenFoodCounter;
    private final int birthDate;
    private int deadDate;
    private boolean isAlive;
    private List<Integer> genoList;
    private int currentGenoNum;
    private int daysAlive;
    WorldSettings worldSettings;

    public Animal(Vector2d position, int energy, List<Integer> genoList, int birthDate, WorldSettings worldSettings){
        this.position = position;
        this.energy = energy;
        this.genoList = genoList;
        this.birthDate = birthDate;
        this.isAlive = true;
        this.direction = MapDirection.NORTH;
        daysAlive = 0;
        children = new ArrayList<>();
        this.worldSettings = worldSettings;
    }


    public Animal(Vector2d position, Animal animal1, Animal animal2, WorldSettings worldSettings){
        this(position, worldSettings.STARTING_ENERGY, new Genotype(animal1,animal2, worldSettings.GENOTYPE_LENGTH, worldSettings).getGenom(), 0, worldSettings);
    }

    public Animal(Vector2d position, WorldSettings worldSettings){
        this(position, worldSettings.STARTING_ENERGY,new Genotype(worldSettings).getGenom(),0, worldSettings);
    }

    public int getEnergy() {
        return energy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEatenFoodCounter() {
        return eatenFoodCounter;
    }

    public List<Integer> getGenotype() {
        return genoList;
    }

    public ArrayList<Animal> getChildren() {
        return children;
    }

    public void addChildren(Animal animal){
        getChildren().add(animal);
    }

    public int getDeadDate() {
        return deadDate;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public boolean getisAlive(){
        return isAlive;
    }

    public int getNumberOfKids(){
        return children.size();
    }


    public boolean canReproduce(){
        return this.energy >= energyNeededToReproduce && isAlive;
    }

    public String toString(){
        return direction.toString() + energy;
    }


    public int getDaysAlive(){
        return daysAlive;
    }

    public int getCurrentGenoNum() {
        return currentGenoNum;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void nextGenoNum(){
        currentGenoNum = (currentGenoNum+1)%worldSettings.GENOTYPE_LENGTH;
    }


    public void move(){
        int current_genom = this.genoList.get(currentGenoNum);
        Vector2d genoDirection = direction.toUnitVector();
        Vector2d potentialPosition = new Vector2d(position.getX(),position.getY()).add(genoDirection);
        int new_X = potentialPosition.getX();
        int new_Y = potentialPosition.getY();

        int new_direction_num = ((getDirection().fromDirectionToInt() + current_genom) % 8);


        if(potentialPosition.getX()>worldSettings.MAP_WIDTH){
            new_X = 0;

        } else if (potentialPosition.getX() < 0) {
            new_X = worldSettings.MAP_WIDTH;
        }


        if(potentialPosition.getY()>worldSettings.MAP_HEIGHT || potentialPosition.getY() < 0){
            new_Y = position.getY();
            new_direction_num = (new_direction_num + 4) % 8;
        }


        setDirection(MapDirection.fromIntToDirection(new_direction_num));
        setPosition(new Vector2d(new_X, new_Y));

        nextGenoNum();
        daysAlive += 1;
    }



    public void killAnimal() {
        if (isAlive) isAlive = false;
    }

    public void addEnergy(int value){
        energy += value;
        if (energy <= 0){
            isAlive = false;
        }
    }
}