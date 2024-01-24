package org.model.map;

import org.model.*;
import org.model.util.Animal;
import org.model.util.MapDirection;
import org.model.util.Plant;
import org.model.util.Vector2d;
import java.util.*;


public class WorldMap {
    private final int mapHeight;
    private final int mapWidth;

    ArrayList<MapChangeListener> observers = new ArrayList<>();
    private ArrayList<Animal> allAnimals = new ArrayList<>();

    Map<Vector2d, Plant> plants = new HashMap<>();
    Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>(); //na danej pozycji moze stać kilka zwierząt
    Set<Vector2d> preferedEmptyFields = new HashSet<>();
    Set<Vector2d> emptyFields = new HashSet<>();
    public final WorldSettings worldSettings;

    private Statistics statistics;

    public WorldMap(WorldSettings worldSettings){
        this.mapHeight = worldSettings.MAP_HEIGHT;
        this.mapWidth = worldSettings.MAP_WIDTH;
        statistics = new Statistics(this);
        this.worldSettings = worldSettings;
    }

    public boolean canMoveTo(Vector2d position){
        return position.getX() >= 0 && position.getX() <= mapWidth && position.getY() >= 0 && position.getY() <= mapHeight;
    }

    void initializeGrassPositions(){
        for (int i = 0; i <= mapWidth; i ++){
            for (int j = 0; j <= mapHeight; j++){
                Vector2d position = new Vector2d(i, j);
                if (isOnEquator(position)) {
                    preferedEmptyFields.add(position);
                } else {
                    emptyFields.add(position);
                }
            }
        }
    }

    public void putStartPlants(){
        initializeGrassPositions();
        for (int i = 0; i < worldSettings.NUMBER_OF_STARTING_PLANTS; i++){
            addPlant();
        }
//        System.out.println(preferedEmptyFields);
//        System.out.println(emptyFields);
//        System.out.println("Plants: " + plants);
    }

    public void plantNewPlants() {
        for (int i = 0; i < worldSettings.NEW_PLANTS_PER_TIC; i++){
            addPlant();
        }
    }

    public void putStartAnimals(){
        for(int i = 0; i < worldSettings.START_NUMBER_OF_ANIMALS; i++) {
            Random random = new Random();
            Vector2d position = new Vector2d(random.nextInt(mapWidth), random.nextInt(mapHeight));
            ArrayList<Animal> animalsOnThisPosition;
            if (!isOccupiedAnimal(position)) {
                animalsOnThisPosition = new ArrayList<Animal>();
            } else {
                animalsOnThisPosition = animals.get(position);
            }
            Animal animal = new Animal(position, worldSettings);
            animalsOnThisPosition.add(animal);
            animals.put(position, animalsOnThisPosition);
            statistics.addToGenoMap(animal.getGenotype());
        }
    }

    public void moveAnimals(){
        updateAllAnimals();

        for (Animal animal : allAnimals){

            animal.move();
            animal.addEnergy(-worldSettings.ENERGY_LOSS_ON_MOVE);
        }

        Map<Vector2d, ArrayList<Animal>> newAnimalsMap = new HashMap<>();
        for (Animal animal : allAnimals){
            ArrayList<Animal> animalsOnThisPosition = new ArrayList<>();
            if (newAnimalsMap.containsKey(animal.getPosition())){
                animalsOnThisPosition = newAnimalsMap.get(animal.getPosition());
            }
            animalsOnThisPosition.add(animal);
            newAnimalsMap.put(animal.getPosition(), animalsOnThisPosition);
        }

        animals = newAnimalsMap;
    }

    public void updateAllAnimals(){
        allAnimals = new ArrayList<>(animals.values().stream()
                .flatMap(ArrayList::stream)
                .toList());
    }

    public List<Animal> getAllAnimals() {
        return allAnimals;
    }

    public void eat(){
        List<Vector2d> plantEaten = new ArrayList<>();
        for (Vector2d position : plants.keySet()){
            if (animals.containsKey(position)){
                Animal strongestAnimal = animals.get(position).stream()
                        .sorted(new AnimalCompere())
                        .toList()
                        .get(0);
                strongestAnimal.addEnergy(worldSettings.PLANT_ENERGY);
                plantEaten.add(position);
            }
        }
        if (worldSettings.IS_MOVING_JUNGLE) updateEmptyFields();
        for (Vector2d position : plantEaten){
            removePlant(position);
        }
    }

    Vector2d generateRandomPosition(Set<Vector2d> positionsSet){
        int size = positionsSet.size();
        Random random = new Random();
        int randomInt = random.nextInt(size);
        int it = 0;
        Vector2d positionToSpawn = new Vector2d(-1, -1);
        for (Vector2d position : positionsSet){
            if (it == randomInt){
                positionToSpawn = position;
                break;
            }
            it ++;
        }
        return positionToSpawn;
    }

    public void addPlant(){
        if (preferedEmptyFields.isEmpty() && emptyFields.isEmpty()) return;
        Vector2d position;
        if (preferedEmptyFields.isEmpty() || emptyFields.isEmpty()){
            if (preferedEmptyFields.isEmpty()) {
                position = generateRandomPosition(emptyFields);
            } else {
                position = generateRandomPosition(preferedEmptyFields);
            }
        } else {
            Random random = new Random();
            int randomInt = random.nextInt(100);
            if (randomInt > 20) {
                position = generateRandomPosition(preferedEmptyFields);
            } else {
                position = generateRandomPosition(emptyFields);
            }
        }
        plants.put(position, new Plant(position, worldSettings.PLANT_ENERGY));
        preferedEmptyFields.remove(position);
        emptyFields.remove(position);
    }

    public void removePlant(Vector2d position){
        plants.remove(position);
        if (isOnEquator(position)){
            preferedEmptyFields.add(position);
        } else {
            emptyFields.add(position);
        }
    }

    public void updateEmptyFields(){
        for (int i = 0; i < mapWidth; i++){
            for (int j = 0; j < mapHeight; j++){
                Vector2d position = new Vector2d(i, j);
                if (plants.containsKey(position) || isOnEquator(position)) continue;
                boolean preferedFieldAdded = false;
                for (int a = 0; a < 8; a+=2){
                    Vector2d newPosition = position.add(MapDirection.fromIntToDirection(a).toUnitVector());
                    if (!canMoveTo(newPosition)) continue;
                    if (plants.containsKey(newPosition)) {
                        preferedFieldAdded = true;
                        preferedEmptyFields.add(position);
                        break;
                    }
                }
                if (!preferedFieldAdded && !isOnEquator(position)){
                    emptyFields.add(position);
                }
            }
        }
    }

    public boolean isOnEquator(Vector2d position){
        int equatorHeight = (mapHeight + 1) / 5;
        if (mapHeight % 5 > 0) equatorHeight += 1;
        int y1 = mapHeight / 2 - equatorHeight / 2;
        int y2 = y1 + equatorHeight - 1;
        return position.getY() <= y2 && position.getY() >= y1;
    }

    public void removeDeadAnimals(){
        Map<Vector2d, ArrayList<Animal>> newAnimals = new HashMap<>();
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry: animals.entrySet()) {
            Vector2d animal_position = entry.getKey();
            ArrayList<Animal> animals_on_position = entry.getValue();
            ArrayList<Animal> animalsList = new ArrayList<Animal>();
            for(Animal animal: animals_on_position){
                if(animal.getisAlive() && animal.getEnergy()>0){
                    animalsList.add(animal);
                } else {
                    statistics.deleteFromGenoMap(animal.getGenotype());
                    statistics.animalDead(animal);
                }
            }

            if(animalsList.size()>0) {
                newAnimals.put(animal_position, animalsList);
            }
        }
        animals = newAnimals;
        updateAllAnimals();
    }


    public void reproduce(){
        Map<Vector2d, ArrayList<Animal>> newAnimals = new HashMap<>();
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry: animals.entrySet()) {
            Vector2d animal_position = entry.getKey();
            ArrayList<Animal> animals_on_position = entry.getValue();
            ArrayList<Animal> animalsList = animals.get(animal_position);
            newAnimals.put(animal_position, animalsList);
            if(animalsList.size()>1){
                ArrayList<Animal> reproduce_animals = new ArrayList<>();
                for(Animal animal: animalsList){
                    if(animal.getEnergy()>worldSettings.ENERGY_NEEDED_TO_REPRODUCE){
                        if(reproduce_animals.size()==2){
                            break;
                        }else {
                            reproduce_animals.add(animal);
                        }
                    }
                }
                if(reproduce_animals.size()==2) {
                    Animal kid = new Animal(animal_position, reproduce_animals.get(0), reproduce_animals.get(1), worldSettings);
                    animalsList.add(kid);
                    reproduce_animals.get(0).addChildren(kid);
                    int x = worldSettings.ENERGY_LOSS_ON_BREED;
                    reproduce_animals.get(0).setEnergy(reproduce_animals.get(0).getEnergy()-x);
                    reproduce_animals.get(1).addChildren(kid);
                    reproduce_animals.get(1).setEnergy(reproduce_animals.get(1).getEnergy()-x);
                    newAnimals.put(animal_position, animalsList);
                    statistics.addToGenoMap(kid.getGenotype());
                }
            }
        }
        animals = newAnimals;
    }

    public void subscribe(MapChangeListener mapChangeListener){
        if (observers.contains(mapChangeListener)) return;
        observers.add(mapChangeListener);
    }

    public void notifyObservers(){
        for (MapChangeListener mapChangeListener : observers){
            mapChangeListener.mapChanged(this);
        }
    }

    public Map<Vector2d, List<WorldElement>> getElements(){
        Map<Vector2d, List<WorldElement>> elements = new HashMap<>();
        for (Vector2d position : animals.keySet()){
            ArrayList<WorldElement> tempElementsList = new ArrayList<>(animals.get(position).stream().sorted(new AnimalCompere()).toList());
            if (plants.containsKey(position)) tempElementsList.add(plants.get(position));
            elements.put(position, tempElementsList);
        }

        for (Vector2d position : plants.keySet()){
            if (elements.containsKey(position)) continue;
            elements.put(position, List.of(plants.get(position)));
        }
        return elements;
    }


    public boolean isOccupiedAnimal(Vector2d position){
        return animals.containsKey(position);
    }

    public boolean isOccupiedPlant(Vector2d position){
        return plants.containsKey(position);
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimals() {
        return animals;
    }

    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }

    public Statistics getStatistics(){
        return statistics;
    }
}
