package oop.evolution;

import com.sun.javafx.scene.control.SizeLimitedList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import oop.evolution.GUI.Graph;
import oop.evolution.GUI.MapVisualiser;
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;
import oop.evolution.OnMapObjects.Animal;
import oop.evolution.OnMapPositioning.Vector2d;
import oop.evolution.Simulation.GameEngine;

public class App extends Application {
    // TITLE AND ICON OF APP WINDOW
    public static String TITLE = "Evolution";
    public static Image  ICON  = new Image("img/evolution.png");

    // START STAGE PROPERTIES
    public final Stage startStage               = new Stage();
    public final int START_STAGE_WIDTH          = 580;
    public final int START_STAGE_HEIGHT         = 480;
    public final boolean START_STAGE_RESIZABLE  = false;

    // FONTS
    public final Font settingsFont  = new Font("Segoe UI", 20);
    public final Font checkBoxFont  = new Font("Segoe UI", 12);

    // START STAGE ELEMENTS
    // labels
    public final Label mapWidthLabel            = new Label("Map width:");
    public final Label mapHeightLabel           = new Label("Map height:");
    public final Label jungleRatioLabel         = new Label("Jungle ratio:");
    public final Label moveEnergyLabel          = new Label("Move energy:");
    public final Label startEnergyLabel         = new Label("Start energy:");
    public final Label foodEnergyLabel          = new Label("Food energy:");
    // sliders
    public final int    SLIDER_WIDTH            = 400;
    public final int    SLIDER_HEIGHT           = 20;

    public final int    MAP_MIN_DIMENSION           = 5;
    public final int    MAP_MAX_DIMENSION           = 30;
    public final int    MAP_DIMENSION_MAJOR_TICK    = 5;

    public final int    MIN_START_ENERGY        = 10;
    public final int    MAX_START_ENERGY        = 50;
    public final int    DEFAULT_START_ENERGY    = 25;
    public final int    START_ENERGY_MAJOR_TICK = 5;

    public final double MIN_JUNGLE_RATIO        = 0.1;
    public final double MAX_JUNGLE_RATIO        = 0.9;
    public final double JUNGLE_RATIO_MAJOR_TICK = 0.1;

    public final int    MIN_MOVE_ENERGY         = 5;
    public final int    MAX_MOVE_ENERGY         = 50;
    public final int    MOVE_ENERGY_MAJOR_TICK  = 5;

    public final int    MIN_FOOD_ENERGY         = 5;
    public final int    MAX_FOOD_ENERGY         = 50;
    public final int    DEFAULT_FOOD_ENERGY     = 25;
    public final int    FOOD_ENERGY_MAJOR_TICK  = 5;

    public final Slider mapWidthSlider    = new Slider(MAP_MIN_DIMENSION, MAP_MAX_DIMENSION, MAP_MIN_DIMENSION);
    public final Slider mapHeightSlider   = new Slider(MAP_MIN_DIMENSION, MAP_MAX_DIMENSION, MAP_MIN_DIMENSION);
    public final Slider startEnergySlider = new Slider(MIN_START_ENERGY, MAX_START_ENERGY, DEFAULT_START_ENERGY);
    public final Slider moveEnergySlider  = new Slider(MIN_MOVE_ENERGY, MAX_MOVE_ENERGY, MIN_MOVE_ENERGY);
    public final Slider foodEnergySlider  = new Slider(MIN_FOOD_ENERGY, MAX_FOOD_ENERGY, DEFAULT_FOOD_ENERGY);
    public final Slider jungleRatioSlider = new Slider(MIN_JUNGLE_RATIO, MAX_JUNGLE_RATIO, MIN_JUNGLE_RATIO);
    // checkboxes
    public final CheckBox magicNormalCheckbox  = new CheckBox("Magic on normal map?");
    public final CheckBox magicWrappedCheckbox = new CheckBox("Magic on wrapped map?");
    // run button
    public final Button runBtn = new Button("RUN!");
    // HBoxes
    public final HBox mapWidthHBox      = new HBox(mapWidthLabel, mapWidthSlider);
    public final HBox mapHeightHBox     = new HBox(mapHeightLabel, mapHeightSlider);
    public final HBox startEnergyHBox   = new HBox(startEnergyLabel, startEnergySlider);
    public final HBox moveEnergyHBox    = new HBox(moveEnergyLabel, moveEnergySlider);
    public final HBox foodEnergyHBox    = new HBox(foodEnergyLabel, foodEnergySlider);
    public final HBox jungleRatioHBox   = new HBox(jungleRatioLabel, jungleRatioSlider);
    public final HBox magicHBox         = new HBox(magicNormalCheckbox, magicWrappedCheckbox);
    public final HBox btnHBox           = new HBox(runBtn);
    //  mainVBox
    public VBox mainVBox          = new VBox(mapWidthHBox, mapHeightHBox, jungleRatioHBox, startEnergyHBox, moveEnergyHBox, foodEnergyHBox, magicHBox, btnHBox);
    // startScene
    public final Scene startScene = new Scene(mainVBox, START_STAGE_WIDTH, START_STAGE_HEIGHT);

    //  properties of maps and simulation
    private int     mapWidth;
    private int     mapHeight;
    private double  jungleRatio;
    private int     startEnergy;
    private int     moveEnergy;
    private int     foodEnergy;
    private boolean normalSimMagic;
    private boolean wrappedSimMagic;

    // maps
    private NormalMap   normalMap;
    private WrappedMap  wrappedMap;

    // engine
    private GameEngine engine;

    // SIMULATION STAGE PROPERTIES
    public final Stage simulationStage              = new Stage();
    public final int SIMULATION_STAGE_WIDTH         = 1200;
    public final int SIMULATION_STAGE_HEIGHT        = 1000;
    public final boolean SIMULATION_STAGE_RESIZABLE = true;

    // SIMULATION STAGE ELEMENTS
    private MapVisualiser normalMapVisualiser;
    private MapVisualiser wrappedMapVisualiser;

    // NORMAL MAP GRAPHS
    private Graph normalMapAnimalsAlive = new Graph("Day", "Animals Alive", 200, 200);
    private Graph normalMapPlants       = new Graph("Day", "Plants", 200, 200);

    // WRAPPED MAP GRAPHS
    private Graph wrappedMapAnimalsAlive = new Graph("Day", "Animals Alive", 200, 200);
    private Graph wrappedMapPlants       = new Graph("Day", "Plants", 200, 200);

    // SIMULATION_PROPERTIES
    public final int SLEEP_TIME = 1000;
    private boolean guiReady = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setting startStage properties
        startStage.setTitle(TITLE);
        startStage.getIcons().add(ICON);
        startStage.setResizable(START_STAGE_RESIZABLE);

        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // read values from sliders and checkBoxes
                mapWidth        = (int) mapWidthSlider.getValue();
                mapHeight       = (int) mapHeightSlider.getValue();
                jungleRatio     = jungleRatioSlider.getValue();
                startEnergy     = (int) startEnergySlider.getValue();
                moveEnergy      = (int) moveEnergySlider.getValue();
                foodEnergy      = (int) foodEnergySlider.getValue();
                normalSimMagic  = magicNormalCheckbox.isSelected();
                wrappedSimMagic = magicWrappedCheckbox.isSelected();

                startStage.close();
                try {
                    prepareSimulation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    prepareSimulationGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        startStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                startStage.close();
                System.exit(0);
            }
        });

        mapWidthLabel.setFont(settingsFont);
        mapHeightLabel.setFont(settingsFont);
        jungleRatioLabel.setFont(settingsFont);
        startEnergyLabel.setFont(settingsFont);
        moveEnergyLabel.setFont(settingsFont);
        foodEnergyLabel.setFont(settingsFont);

        mapWidthSlider.setMajorTickUnit(MAP_DIMENSION_MAJOR_TICK);
        mapWidthSlider.setMinorTickCount(0);
        mapWidthSlider.setSnapToTicks(true);
        mapWidthSlider.setShowTickLabels(true);
        mapWidthSlider.setShowTickMarks(true);

        mapHeightSlider.setMajorTickUnit(MAP_DIMENSION_MAJOR_TICK);
        mapHeightSlider.setMinorTickCount(0);
        mapHeightSlider.setSnapToTicks(true);
        mapHeightSlider.setShowTickLabels(true);
        mapHeightSlider.setShowTickMarks(true);

        jungleRatioSlider.setMajorTickUnit(JUNGLE_RATIO_MAJOR_TICK);
        jungleRatioSlider.setMinorTickCount(0);
        jungleRatioSlider.setSnapToTicks(true);
        jungleRatioSlider.setShowTickLabels(true);
        jungleRatioSlider.setShowTickMarks(true);

        startEnergySlider.setMajorTickUnit(START_ENERGY_MAJOR_TICK);
        startEnergySlider.setMinorTickCount(4);
        startEnergySlider.setSnapToTicks(true);
        startEnergySlider.setShowTickLabels(true);
        startEnergySlider.setShowTickMarks(true);

        moveEnergySlider.setMajorTickUnit(MOVE_ENERGY_MAJOR_TICK);
        moveEnergySlider.setMinorTickCount(4);
        moveEnergySlider.setSnapToTicks(true);
        moveEnergySlider.setShowTickLabels(true);
        moveEnergySlider.setShowTickMarks(true);

        foodEnergySlider.setMajorTickUnit(FOOD_ENERGY_MAJOR_TICK);
        foodEnergySlider.setMinorTickCount(4);
        foodEnergySlider.setSnapToTicks(true);
        foodEnergySlider.setShowTickLabels(true);
        foodEnergySlider.setShowTickMarks(true);


        mapWidthHBox.setAlignment(Pos.CENTER_RIGHT);
        mapWidthSlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        mapWidthHBox.setSpacing(20);

        mapHeightHBox.setAlignment(Pos.CENTER_RIGHT);
        mapHeightSlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        mapHeightHBox.setSpacing(20);

        jungleRatioHBox.setAlignment(Pos.CENTER_RIGHT);
        jungleRatioSlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        jungleRatioHBox.setSpacing(20);

        startEnergyHBox.setAlignment(Pos.CENTER_RIGHT);
        startEnergySlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        startEnergyHBox.setSpacing(20);

        moveEnergyHBox.setAlignment(Pos.CENTER_RIGHT);
        moveEnergySlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        moveEnergyHBox.setSpacing(20);

        foodEnergyHBox.setAlignment(Pos.CENTER_RIGHT);
        foodEnergySlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        foodEnergyHBox.setSpacing(20);

        magicHBox.setAlignment(Pos.CENTER);
        magicHBox.setSpacing(30);
        magicNormalCheckbox.setFont(checkBoxFont);
        magicWrappedCheckbox.setFont(checkBoxFont);

        btnHBox.setAlignment(Pos.CENTER);

        mainVBox.setSpacing(22);
        mainVBox.setPadding(new Insets(10));

        // setting properties of startStageElements
        startStage.setScene(startScene);
        startStage.show();
    }

    private void prepareSimulation() throws Exception {
        normalMap = new NormalMap(mapWidth, mapHeight, jungleRatio);
        wrappedMap = new WrappedMap(mapWidth, mapHeight, jungleRatio);
        engine = new GameEngine(normalMap, wrappedMap, normalSimMagic, wrappedSimMagic, startEnergy, moveEnergy, foodEnergy);
    }

    private void prepareSimulationGUI() throws Exception {
        simulationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                simulationStage.close();
                System.exit(0);
            }
        });

        simulationStage.getIcons().add(ICON);
        simulationStage.setTitle(TITLE);
        simulationStage.setWidth(SIMULATION_STAGE_WIDTH);
        simulationStage.setHeight(SIMULATION_STAGE_HEIGHT);
        simulationStage.setResizable(SIMULATION_STAGE_RESIZABLE);

        normalMapVisualiser = new MapVisualiser(normalMap);
        wrappedMapVisualiser = new MapVisualiser(wrappedMap);

        updateGUI();
        simulationStage.show();

        Thread threadGUI = new Thread(() -> {
            while(true) {
                synchronized (this) {
                    while (!guiReady) {
                        try {
                            wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateCharts();
                        updateGUI();
                        simulationStage.show();
                    });
                    guiReady = false;
                    notifyAll();
                }
            }
        });

        Thread simThread = new Thread(() -> {
            while (true) {
                synchronized (this) {
                    while (guiReady) {
                        try {
                            wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    engine.run();
                    guiReady = true;
                    notifyAll();
                }
            }
        });
        threadGUI.start();
        simThread.start();
    }

    public void updateGUI(){
        normalMapVisualiser.refresh();
        wrappedMapVisualiser.refresh();

        HBox simHBox = new HBox(normalMapVisualiser.getMapVisualisation(), wrappedMapVisualiser.getMapVisualisation());
        simHBox.setAlignment(Pos.TOP_CENTER);
        simHBox.setSpacing(20);

        HBox normalChartsHBox = new HBox(normalMapAnimalsAlive.getLineChart(), normalMapPlants.getLineChart());
        normalChartsHBox.setSpacing(10);
        HBox wrappedChartsHBox = new HBox(wrappedMapAnimalsAlive.getLineChart(), wrappedMapPlants.getLineChart());
        wrappedChartsHBox.setSpacing(10);
        HBox chartsHBox = new HBox(normalChartsHBox, wrappedChartsHBox);
        chartsHBox.setSpacing(20);
        chartsHBox.setAlignment(Pos.BASELINE_CENTER);

        mainVBox = new VBox(simHBox, chartsHBox);
        simulationStage.setScene(new Scene(mainVBox));
    }

    public void updateCharts(){
        normalMapAnimalsAlive.updateData(normalMap.getDays(), normalMap.getAnimalsCnt());
        wrappedMapAnimalsAlive.updateData(wrappedMap.getDays(), wrappedMap.getAnimalsCnt());
        normalMapPlants.updateData(normalMap.getDays(), normalMap.getPlantsCnt());
        wrappedMapPlants.updateData(wrappedMap.getDays(), wrappedMap.getPlantsCnt());
    }
}
