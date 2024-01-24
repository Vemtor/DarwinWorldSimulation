package org.model;

public class WorldSettings {
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;
    public final int GENOTYPE_LENGTH;
    public final int START_NUMBER_OF_ANIMALS;
    public final int STARTING_ENERGY;
    public final int ENERGY_NEEDED_TO_REPRODUCE;
    public final int ENERGY_LOSS_ON_BREED;
    public final int ENERGY_LOSS_ON_MOVE;
    public final int PLANT_ENERGY;
    public final int NUMBER_OF_STARTING_PLANTS;
    public final int NEW_PLANTS_PER_TIC;
    public final boolean IS_MOVING_JUNGLE;
    public final boolean IS_SMALL_CORRECT;

    public final int MINIMAL_GENS_TO_MUTATE;
    public final int MAXIMAL_GENS_TO_MUTATE;

    public WorldSettings(int mapWidth, int mapHeight, int genotypeLength, int startNumberOfAnimals,
                         int startingEnergy, int energyNeededToReproduce, int energyLossOnBreed,
                         int energyLossOnMove, int plantEnergy, int numberOfStartingPlants,
                         int newPlantsPerTic, boolean isMovingJungle, boolean isSmallCorrect, int minimalGensToMutate, int maximalGensToMutate) {
        MAP_WIDTH = mapWidth;
        MAP_HEIGHT = mapHeight;
        GENOTYPE_LENGTH = genotypeLength;
        START_NUMBER_OF_ANIMALS = startNumberOfAnimals;
        STARTING_ENERGY = startingEnergy;
        ENERGY_NEEDED_TO_REPRODUCE = energyNeededToReproduce;
        ENERGY_LOSS_ON_BREED = energyLossOnBreed;
        ENERGY_LOSS_ON_MOVE = energyLossOnMove;
        PLANT_ENERGY = plantEnergy;
        NUMBER_OF_STARTING_PLANTS = numberOfStartingPlants;
        NEW_PLANTS_PER_TIC = newPlantsPerTic;
        IS_MOVING_JUNGLE = isMovingJungle;
        IS_SMALL_CORRECT = isSmallCorrect;
        MINIMAL_GENS_TO_MUTATE = minimalGensToMutate;
        MAXIMAL_GENS_TO_MUTATE = maximalGensToMutate;
    }
}
