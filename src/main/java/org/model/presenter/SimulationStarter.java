package org.model.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.model.Simulation;
import org.model.SimulationEngine;
import org.model.WorldSettings;
import org.model.map.WorldMap;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SimulationStarter {

    public TextField mapWidthTextField;
    public TextField mapHeightTextField;
    public TextField genotypeLengthTextField;
    public TextField startNumberOfAnimalsTextField;
    public TextField startingEnergyTextField;
    public TextField energyNeededToReproduceTextField;
    public TextField energyLossOnBreedTextField;
    public TextField energyLossOnMoveTextField;
    public TextField plantEnergyTextField;
    public TextField numberOfStartingPlantsTextField;
    public TextField newPlantsPerTicTextField;
    public CheckBox isMovingJungleCheckBox;
    public CheckBox isSmallCorrectCheckBox;

    public TextField minimalGensToMutateField;
    public TextField maximalGensToMutateField;

    private void configureStage(Stage primaryStage, HBox viewRoot){
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
    }

    public void initialize() {
        mapWidthTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(mapWidthTextField, 1, 50, 20));
        mapHeightTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(mapHeightTextField, 1, 50, 20));
        genotypeLengthTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateGenotypeLength(genotypeLengthTextField, 1, 20, 8));
        startNumberOfAnimalsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(startNumberOfAnimalsTextField, 1, 2000, 300));
        energyNeededToReproduceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(energyNeededToReproduceTextField, 1, 1000, 20));
        startingEnergyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(startingEnergyTextField, 1, 1000, 20));
        energyLossOnBreedTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(energyLossOnBreedTextField, 1, 1000, 5));
        energyLossOnMoveTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(energyLossOnMoveTextField, 1, 1000, 1));
        plantEnergyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(plantEnergyTextField, 1, 100, 5));
        numberOfStartingPlantsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(numberOfStartingPlantsTextField, 1, 1000, 15));
        newPlantsPerTicTextField.focusedProperty().addListener((observable, oldValue, newValue) -> validateRange(newPlantsPerTicTextField, 1, 1000, 10));
        minimalGensToMutateField.focusedProperty().addListener((observable, oldValue, newValue) -> validateMinimalGensToMutateField(minimalGensToMutateField));
        maximalGensToMutateField.focusedProperty().addListener((observable, oldValue, newValue) -> validateMaximalGensToMutateField(maximalGensToMutateField));
    }

    void validateRange(TextField mapTextField, int lowerRange, int upperRange, int defaultValue){
        int value;
        if (Objects.equals(mapTextField.getText(), "")){
            value = 0;
        } else {
            value = Integer.parseInt(mapTextField.getText());
        }
        if (value < lowerRange || value > upperRange){
            value = defaultValue;
        }
        mapTextField.setText(String.valueOf(value));
    }

    void validateMaximalGensToMutateField(TextField textField){
        int value;
        if (Objects.equals(textField.getText(), "")){
            value = 0;
        } else {
            value = Integer.parseInt(textField.getText());
        }

        if (value < 0 || value > Integer.parseInt(genotypeLengthTextField.getText())){
            value = Integer.parseInt(genotypeLengthTextField.getText());
        }
        if (value < Integer.parseInt(minimalGensToMutateField.getText())) {
            value = Integer.parseInt(minimalGensToMutateField.getText());
        }
        textField.setText(String.valueOf(value));
    }

    void validateMinimalGensToMutateField(TextField textField){
        int value;
        if (Objects.equals(textField.getText(), "")){
            value = 0;
        } else {
            value = Integer.parseInt(textField.getText());
        }

        if (value < 0 || value > Integer.parseInt(genotypeLengthTextField.getText())){
            value = Integer.parseInt(genotypeLengthTextField.getText());
        }
        if (value > Integer.parseInt(maximalGensToMutateField.getText())) {
            value = Integer.parseInt(maximalGensToMutateField.getText());
        }
        textField.setText(String.valueOf(value));
    }

    void validateGenotypeLength(TextField textField, int lowerRange, int upperRange, int defaultValue) {
        validateRange(textField, lowerRange, upperRange, defaultValue);
        validateMaximalGensToMutateField(maximalGensToMutateField);
        validateMinimalGensToMutateField(minimalGensToMutateField);
    }

    public void onStartClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Simulation.fxml"));
        Stage stage = new Stage();
        HBox viewRoot = loader.load();
        configureStage(stage, viewRoot);
        stage.show();


        WorldSettings worldSettings = initializeWorldSettings();
        WorldMap map = new WorldMap(worldSettings);
        SimulationPresenter simulationPresenter = loader.getController();
        simulationPresenter.initialize();
        simulationPresenter.setWorldMap(map);
        map.subscribe(simulationPresenter);
        Simulation simulation = new Simulation(map);
        simulationPresenter.setSimulation(simulation);
        stage.setOnCloseRequest(event -> simulation.stopSimulation());
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
    }

    WorldSettings initializeWorldSettings(){
        int mapWidth = Integer.parseInt(mapWidthTextField.getText());
        int mapHeight = Integer.parseInt(mapHeightTextField.getText());
        int genotypeLength = Integer.parseInt(genotypeLengthTextField.getText());
        int startNumberOfAnimals = Integer.parseInt(startNumberOfAnimalsTextField.getText());
        int startingEnergy = Integer.parseInt(startingEnergyTextField.getText());
        int energyNeededToReproduce = Integer.parseInt(energyNeededToReproduceTextField.getText());
        int energyLossOnBreed = Integer.parseInt(energyLossOnBreedTextField.getText());
        int energyLossOnMove = Integer.parseInt(energyLossOnMoveTextField.getText());
        int plantEnergy = Integer.parseInt(plantEnergyTextField.getText());
        int numberOfStartingPlants = Integer.parseInt(numberOfStartingPlantsTextField.getText());
        int newPlantsPerTic = Integer.parseInt(newPlantsPerTicTextField.getText());
        boolean isMovingJungle = isMovingJungleCheckBox.isSelected();
        boolean isSmallCorrect = isSmallCorrectCheckBox.isSelected();
        int minimalGensToMutate = Integer.parseInt(minimalGensToMutateField.getText());
        int maximalGensToMutate = Integer.parseInt(maximalGensToMutateField.getText());

        return new WorldSettings(mapWidth, mapHeight, genotypeLength, startNumberOfAnimals,
                startingEnergy, energyNeededToReproduce, energyLossOnBreed, energyLossOnMove, plantEnergy,
                numberOfStartingPlants, newPlantsPerTic, isMovingJungle, isSmallCorrect, minimalGensToMutate, maximalGensToMutate);
    }

//    public void saveConfiguration(String filename, WorldSettings worldSettings) {
//        ConfigurationSaver.saveConfiguration(filename, worldSettings);
//    }

//    public void onSaveClick() {
//        WorldSettings worldSettings = initializeWorldSettings();
//        FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter if you want to restrict file types
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Properties Files (*.properties)", "*.properties");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show save file dialog
//        Stage stage = (Stage) mapWidthTextField.getScene().getWindow();
//        java.io.File file = fileChooser.showSaveDialog(stage);
//
//        if (file != null) {
//            // Save configuration to the selected file
//            saveConfiguration(file.getAbsolutePath(), worldSettings);
//            System.out.println("Configuration saved to: " + file.getAbsolutePath());
//        }
//    }
}
