package org.model.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.model.*;
import org.model.map.WorldMap;
import org.model.util.Animal;
import org.model.util.Plant;
import org.model.util.Vector2d;

import java.util.List;
import java.util.Map;

public class SimulationPresenter implements MapChangeListener {
    public VBox animalStatisticsBox;
    @FXML
    private Button pauseButton;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label numberOfAnimalsLabel;

    @FXML
    private Label numberOfPlantsLabel;

    @FXML
    private Label freePositionsLabel;

    @FXML
    private Label mostPopularGenomeLabel;

    @FXML
    private Label averageAnimalsEnergyLabel;

    @FXML
    private Label averageAliveAnimalsLifeTimeLabel;

    @FXML
    private Label averageDeadAnimalsLifeTimeLabel;

    @FXML
    private Label averageKidsNumberLabel;

    @FXML
    private Label animalGenomeLabel;

    @FXML
    private Label numDirectionLabel;

    @FXML
    private Label currentEnergyLabel;

    @FXML
    private Label kidNumberLabel;

    @FXML
    private Label daysAliveLabel;

    @FXML
    private Label deadDayLabel;

    WorldElement animalToFollow = null;

    Simulation simulation;
    WorldMap map;
    boolean simulationPaused = false;
    AnimalStatistics animalStatistics = new AnimalStatistics();

    public void initialize(){
        animalStatisticsBox.setVisible(false);
    }

    void drawMap(WorldMap map){
        clearGrid();
        int mapWith = map.worldSettings.MAP_WIDTH;
        int mapHeight = map.worldSettings.MAP_HEIGHT;

        for (int i = 0; i <= mapWith + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(50));
        }

        for (int i = 0; i <= mapHeight + 1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(50));
        }

        Label label;
        for (int i = 0; i < mapWith + 1; i++){
            label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }
        for (int i = 0; i < mapHeight + 1; i++){
            label = new Label(String.valueOf(mapHeight - i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }
        label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);

        for (Map.Entry<Vector2d, List<WorldElement>> entry : map.getElements().entrySet()) {
            WorldElement object = entry.getValue().get(0);
            Node objectLook;
            if (object instanceof Plant){
                objectLook = new Rectangle(50, 50, Color.GREEN);
            } else {
                objectLook = new Circle(20, Color.BLACK);
//                System.out.println("-------");
//                System.out.println(object);
//                System.out.println(animalToFollow);
                if (object.equals(animalToFollow)){
                    System.out.println("dddddd");
                    objectLook = new Circle(20, Color.BLUE);
                    animalStatistics.updateStats((Animal) object);
                    updateLabels(animalStatistics);
                }
                objectLook.setMouseTransparent(false);
                objectLook.setOnMouseClicked(event -> animalClicked(object));
            }
            GridPane.setHalignment(objectLook, HPos.CENTER);
            int x = object.getPosition().getX();
            int y = object.getPosition().getY();
            mapGrid.add(objectLook, x + 1, mapHeight - y + 1);
        }
        //todo dodać funkcję wojtka
        updateStatistics(map);
    }

    void animalClicked(WorldElement animal){
        animalToFollow = animal;
        animalStatisticsBox.setVisible(true);
        animalStatistics.setCurrent_animal((Animal) animal);
        animalStatistics.updateStats((Animal) animal);
        updateLabels(animalStatistics);
        drawMap(map);
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void pauseAndUnPauseSimulation(){
        if (simulationPaused){
            simulationPaused = false;
            simulation.unpauseSimulation();
            pauseButton.setText("Pause");
        } else {
            simulationPaused = true;
            simulation.pauseSimulation();
            pauseButton.setText("Unpause");
        }
    }

    private void clearGrid(){
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    @Override
    public void mapChanged(WorldMap map) {
        Platform.runLater(() -> drawMap(map));
    }

    public void updateStatistics(WorldMap map){
        Statistics statistics = map.getStatistics();
        numberOfAnimalsLabel.setText("Number of Animals: " + statistics.getNumberOfAnimals());
        numberOfPlantsLabel.setText("Number of Plants: " + statistics.getNumberOfPlants());
        freePositionsLabel.setText("Free Positions: " + statistics.getFreePositions());
        averageAnimalsEnergyLabel.setText("Average Animals Energy: " + statistics.getaverageAnimalsEnergy());
        mostPopularGenomeLabel.setText("Most popular genotype: " + statistics.getMostPopularGenom());
        averageAliveAnimalsLifeTimeLabel.setText("Average Alive Animals Life Time: " + statistics.getaverageAliveAnimalsLifeTime());
        averageDeadAnimalsLifeTimeLabel.setText("Average Dead Animals Life Time: " + statistics.getAverageDeadAnimalsLifeTime());
        averageKidsNumberLabel.setText("Average Kids Number: " + statistics.getaverageKidsNumber());
    }

    private void updateLabels(AnimalStatistics animalStatistics) {
        animalGenomeLabel.setText("Genome: " + animalStatistics.getAnimalGenom());
        numDirectionLabel.setText("Num Direction: " + animalStatistics.getGenoIndex());
        currentEnergyLabel.setText("Current Energy: " + animalStatistics.getCurrentEnergy());
        kidNumberLabel.setText("Kid Number: " + animalStatistics.getKidNumber());
        daysAliveLabel.setText("Days Alive: " + animalStatistics.getDaysAlive());
        deadDayLabel.setText("Dead Day: " + animalStatistics.getDeadDay());
    }
}
