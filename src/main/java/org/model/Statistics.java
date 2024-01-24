package org.model;

import org.model.map.WorldMap;
import org.model.util.Animal;
import org.model.util.Genotype;
import org.model.util.Plant;
import org.model.util.Vector2d;

import java.text.DecimalFormat;
import java.util.*;

public class Statistics {
    DecimalFormat df = new DecimalFormat("#.##");
    private WorldMap map;

    private int numberOfAnimals;
    private int numberOfPlants = -1;
    private int freePositions = -1;
    private List<Integer> mostPopularGenom;

    private Map<List<Integer>, Integer> genoMap = new HashMap<>();
    private double averageAnimalsEnergy = -1;
    private double averageDeadAnimalsLifeTime = -1;

    private long deadAnimalsLifetimeSum = 0;
    private int deadAnimalSize = 0;


    private double averageKidsNumber = -1;

    private double averageAliveAnimalsLifeTime = -1;


    public Statistics( WorldMap map){
        this.map = map;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }

    public void setNumberOfPlants(int numberOfPlants) {
        this.numberOfPlants = numberOfPlants;
    }

    public int getFreePositions() {
        return freePositions;
    }

    public void setFreePositions(int freePositions) {
        this.freePositions = freePositions;
    }

    public List<Integer> getMostPopularGenom() {
        return mostPopularGenom;
    }

    public void setMostPopularGenom(ArrayList<Integer> mostPopularGenom) {
        this.mostPopularGenom = mostPopularGenom;
    }

    public String getaverageAnimalsEnergy() {

        String AAE = df.format(averageAnimalsEnergy);
        return AAE;
    }

    public void setaverageAnimalsEnergy(double averageAnimalsEnergy) {
        this.averageAnimalsEnergy = averageAnimalsEnergy;
    }

    public double getaverageAliveAnimalsLifeTime() {
        return averageAliveAnimalsLifeTime;
    }

    public void setaverageAliveAnimalsLifeTime(double averageAliveAnimalsLifeTime) {
        this.averageAliveAnimalsLifeTime = averageAliveAnimalsLifeTime;
    }



    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }


    public double getaverageDeadAnimalsLifeTime() {
        return averageDeadAnimalsLifeTime;
    }

    public void setaverageDeadAnimalsLifeTime(double averageDeadAnimalsLifeTime) {
        this.averageDeadAnimalsLifeTime = averageDeadAnimalsLifeTime;
    }

    public String getaverageKidsNumber() {

        String AKN = df.format(averageKidsNumber);
        return AKN;
    }

    public void setaverageKidsNumber(double averageKidsNumber) {
        this.averageKidsNumber = averageKidsNumber;
    }

    public void updateaverageKidsNumber(){
        double cnt = 0;
        for(Animal animal: map.getAllAnimals()){
            cnt+=animal.getNumberOfKids();
        }
        cnt/=map.getAllAnimals().size();
        setaverageKidsNumber(cnt);
    }

    public String getAverageDeadAnimalsLifeTime() {

        String ADALT = df.format(averageDeadAnimalsLifeTime);
        return ADALT;
    }

    public void setAverageAliveAnimalsLifeTime(double averageAliveAnimalsLifeTime) {
        this.averageAliveAnimalsLifeTime = averageAliveAnimalsLifeTime;
    }

    public long getDeadAnimalsLifetimeSum() {

        return deadAnimalsLifetimeSum;
    }

    public void setDeadAnimalsLifetimeSum(long deadAnimalsLifetimeSum) {
        this.deadAnimalsLifetimeSum = deadAnimalsLifetimeSum;
    }

    public void animalDead(Animal animal){
        int d = animal.getDaysAlive();
        deadAnimalSize += 1;
        setDeadAnimalsLifetimeSum(getDeadAnimalsLifetimeSum() + d);
        if(getDeadAnimalSize()==0){
            setaverageDeadAnimalsLifeTime(0);
        }else {
            setaverageDeadAnimalsLifeTime(getDeadAnimalsLifetimeSum() / getDeadAnimalSize());
        }
    }

    public void updateNumberOfAnimals(){
        setNumberOfAnimals(map.getAllAnimals().size());
    };

    public void updateNumberofPlants(){
        setNumberOfPlants(map.getPlants().size());
    }

    public void updateFreePosition(){
        int all_positions = (map.getMapHeight()+1)*(map.getMapWidth()+1);
        setFreePositions(all_positions - map.getElements().size());

    }

    public void updateaverageAnimalsEnergy(){
        int summary_energy = 0;
        for(Animal animal: map.getAllAnimals()){
            summary_energy += animal.getEnergy();
        }
        setaverageAnimalsEnergy(((double) summary_energy /map.getAnimals().size()));
    }

    public void updateaverageAliveAnimalsLifeTime(){
        int days_alive = 0;
        for(Animal animal: map.getAllAnimals()){
            days_alive += animal.getDaysAlive();
        }
        if (map.getAllAnimals().isEmpty()) {
            setaverageAliveAnimalsLifeTime(0);
            return;
        }
        setaverageAliveAnimalsLifeTime(days_alive / map.getAllAnimals().size());
    }

    public int getDeadAnimalSize() {
        return deadAnimalSize;
    }

    public void addToGenoMap(List<Integer> geno){
        if(genoMap.containsKey(geno) == true){
            int cnt = genoMap.get(geno);
            genoMap.put(geno, cnt+1);
        }else{
            genoMap.put(geno,1);
        }
    }

    public void deleteFromGenoMap(List<Integer> geno){
        if(genoMap.containsKey(geno)){
            int cnt = genoMap.get(geno);
            if(cnt>1){
                genoMap.put(geno,cnt-1);
            }else{
                genoMap.remove(geno);
            }
        }
    }
    public void updateMostPopularGenom(){
        int max_global_cnt = 0;
        List<Integer> popularGenom = new ArrayList<>();
        for(Map.Entry<List<Integer>, Integer> entry: genoMap.entrySet()){
            int max_cnt = entry.getValue();
            if(max_cnt>max_global_cnt){
                popularGenom = entry.getKey();
                max_global_cnt = max_cnt;
            }
        }
        setMostPopularGenom(popularGenom);

    }

    public void setMostPopularGenom(List<Integer> mostPopularGenom) {
        this.mostPopularGenom = mostPopularGenom;
    }

    public void setDeadAnimalSize(int deadAnimalSize) {
        this.deadAnimalSize = deadAnimalSize;
    }

    public void updateAllStats(){
        updateaverageAliveAnimalsLifeTime();
        updateaverageAnimalsEnergy();
        updateFreePosition();
        updateNumberOfAnimals();
        updateNumberofPlants();
        updateMostPopularGenom();
        updateaverageKidsNumber();
    }
}