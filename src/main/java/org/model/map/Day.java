package org.model.map;

import org.model.util.Animal;
import org.model.util.Plant;
import org.model.util.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day {
    private static int dayZero;
    private int dayNumber;
    private WorldMap map;
    Map<Vector2d, Plant> plants = map.getPlants();
    Map<Vector2d, ArrayList<Animal>> animals = map.getAnimals();

    public Day(WorldMap map){
        this.dayNumber = dayZero++;
        this.map = map;
    }

    public void runDay(){
//        Usunięcie martwych zwierzaków z mapy.
//        Skręt i przemieszczenie każdego zwierzaka.
//        Konsumpcja roślin, na których pola weszły zwierzaki.
//        Rozmnażanie się najedzonych zwierzaków znajdujących się na tym samym polu.
//        Wzrastanie nowych roślin na wybranych polach mapy.


    }

    public void moveAnimals(){
        Map<Vector2d, ArrayList<Animal>> movedAnimals = new HashMap<>();
        for(Vector2d position : animals.keySet()){
            ArrayList<Animal> animalsHere = animals.get(position);

            for(Animal animalToMove: animalsHere){

            }
        }

    }


    public void removeDeadAnimals(){

    }





}
