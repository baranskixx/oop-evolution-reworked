package oop.evolution;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import oop.evolution.GUI.Graph;
import oop.evolution.GUI.MapVisualiser;
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;
import oop.evolution.Simulation.GameEngine;

import java.io.IOException;
import java.util.Arrays;

public class App extends Application {
    // TITLE AND ICON OF APP WINDOW
    public static String TITLE = "Evolution";
    public static Image  ICON  = new Image("img/evolution.png");

    // START STAGE PROPERTIES
    public final Stage startStage               = new Stage();
    public final int START_STAGE_WIDTH          = 580;
    public final int START_STAGE_HEIGHT         = 520;
    public final boolean START_STAGE_RESIZABLE  = false;

    // FONTS
    public final Font settingsFont  = new Font("Segoe UI", 20);
    public final Font checkBoxFont  = new Font("Segoe UI", 12);
    public final Font magicInfoFont = new Font("Segoe UI", 24);

    // START STAGE ELEMENTS
    // labels
    public final Label mapWidthLabel            = new Label("Map width:");
    public final Label mapHeightLabel           = new Label("Map height:");
    public final Label jungleRatioLabel         = new Label("Jungle ratio:");
    public final Label moveEnergyLabel          = new Label("Move energy:");
    public final Label startEnergyLabel         = new Label("Start energy:");
    public final Label foodEnergyLabel          = new Label("Food energy:");
    public final Label animalsAtStartLabel      = new Label("Animals: ");
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

    public final int    MIN_MOVE_ENERGY         = 0;
    public final int    MAX_MOVE_ENERGY         = 50;
    public final int    MOVE_ENERGY_MAJOR_TICK  = 10;

    public final int    MIN_FOOD_ENERGY         = 5;
    public final int    MAX_FOOD_ENERGY         = 50;
    public final int    DEFAULT_FOOD_ENERGY     = 25;
    public final int    FOOD_ENERGY_MAJOR_TICK  = 5;

    public final int    MIN_ANIMALS_START           = 10;
    public final int    MAX_ANIMALS_START           = 30;
    public final int    ANIMALS_START_MAJOR_TICK    = 5;

    public final Slider mapWidthSlider     = new Slider(MAP_MIN_DIMENSION, MAP_MAX_DIMENSION, MAP_MIN_DIMENSION);
    public final Slider mapHeightSlider    = new Slider(MAP_MIN_DIMENSION, MAP_MAX_DIMENSION, MAP_MIN_DIMENSION);
    public final Slider startEnergySlider  = new Slider(MIN_START_ENERGY, MAX_START_ENERGY, DEFAULT_START_ENERGY);
    public final Slider moveEnergySlider   = new Slider(MIN_MOVE_ENERGY, MAX_MOVE_ENERGY, MIN_MOVE_ENERGY);
    public final Slider foodEnergySlider   = new Slider(MIN_FOOD_ENERGY, MAX_FOOD_ENERGY, DEFAULT_FOOD_ENERGY);
    public final Slider jungleRatioSlider  = new Slider(MIN_JUNGLE_RATIO, MAX_JUNGLE_RATIO, MIN_JUNGLE_RATIO);
    public final Slider startAnimalsSlider = new Slider(MIN_ANIMALS_START, MAX_ANIMALS_START, MIN_ANIMALS_START);

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
    public final HBox animalsStartHBox  = new HBox(animalsAtStartLabel, startAnimalsSlider);
    public final HBox magicHBox         = new HBox(magicNormalCheckbox, magicWrappedCheckbox);
    public final HBox btnHBox           = new HBox(runBtn);
    //  main VBox
    public VBox mainVBox          = new VBox(mapWidthHBox, mapHeightHBox, jungleRatioHBox, startEnergyHBox, moveEnergyHBox, foodEnergyHBox, animalsStartHBox, magicHBox, btnHBox);
    //  main HBox
    public HBox mainHBox;
    // startScene
    public final Scene startScene = new Scene(mainVBox, START_STAGE_WIDTH, START_STAGE_HEIGHT);

    //  properties of maps and simulation
    private int     mapWidth;
    private int     mapHeight;
    private double  jungleRatio;
    private int     startEnergy;
    private int     moveEnergy;
    private int     foodEnergy;
    private int     animalsAtStart;
    private boolean normalSimMagic;
    private boolean wrappedSimMagic;

    // maps
    private NormalMap   normalMap;
    private WrappedMap  wrappedMap;

    // engine
    private GameEngine engine;

    // SIMULATION STAGE PROPERTIES
    public final Stage simulationStage              = new Stage();
    public final int SIMULATION_STAGE_WIDTH         = 1800;
    public final int SIMULATION_STAGE_HEIGHT        = 1000;
    public final boolean SIMULATION_STAGE_RESIZABLE = true;

    // SIMULATION STAGE ELEMENTS
    private MapVisualiser normalMapVisualiser;
    private MapVisualiser wrappedMapVisualiser;

    // LABELS
    private Label magicNormalMapLabel;
    private Label magicWrappedMapLabel;
    private Label dominatingGenomeNormal;
    private Label dominatingGenomeWrapped;

    // NORMAL MAP GRAPHS
    private final Graph normalMapAnimalsAlive           = new Graph("Day", "Animals Alive", 200, 200);
    private final Graph normalMapPlants                 = new Graph("Day", "Plants", 200, 200);
    private final Graph normalMapAverageEnergy          = new Graph("Day", "Average energy", 200, 200);
    private final Graph normalMapAverageChildrenNumber  = new Graph("Day", "Avg Children", 200, 200);
    private final Graph normalMapAverageLifetimeDead    = new Graph("Day", "Avg Lifetime", 200, 200);

    // WRAPPED MAP GRAPHS
    private final Graph wrappedMapAnimalsAlive = new Graph("Day", "Animals Alive", 200, 200);
    private final Graph wrappedMapPlants       = new Graph("Day", "Plants", 200, 200);
    private final Graph wrappedMapAverageEnergy          = new Graph("Day", "Average energy", 200, 200);
    private final Graph wrappedMapAverageChildrenNumber  = new Graph("Day", "Avg Children", 200, 200);
    private final Graph wrappedMapAverageLifetimeDead    = new Graph("Day", "Avg Lifetime", 200, 200);

    // BUTTONS
    private final Button pauseNormalSimBtn  = new Button("Pause");
    private final Button pauseWrappedSimBtn = new Button("Pause");

    private final Button toCsvNormalBtn     = new Button("Save to CSV");
    private final Button toCsvWrappedBtn    = new Button("Save to CSV");

    // SIMULATION_PROPERTIES
    public final int SLEEP_TIME = 500;
    private boolean guiReady = true;

    // CSV WRITING PROPERTIES
    private boolean saveNormalCsv = false;
    private boolean saveWrappedCsv = false;



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
                animalsAtStart  = (int) startAnimalsSlider.getValue();
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
        animalsAtStartLabel.setFont(settingsFont);

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
        moveEnergySlider.setMinorTickCount(9);
        moveEnergySlider.setSnapToTicks(true);
        moveEnergySlider.setShowTickLabels(true);
        moveEnergySlider.setShowTickMarks(true);

        foodEnergySlider.setMajorTickUnit(FOOD_ENERGY_MAJOR_TICK);
        foodEnergySlider.setMinorTickCount(FOOD_ENERGY_MAJOR_TICK-1);
        foodEnergySlider.setSnapToTicks(true);
        foodEnergySlider.setShowTickLabels(true);
        foodEnergySlider.setShowTickMarks(true);

        startAnimalsSlider.setMajorTickUnit(START_ENERGY_MAJOR_TICK);
        startAnimalsSlider.setMinorTickCount(START_ENERGY_MAJOR_TICK-1);
        startAnimalsSlider.setShowTickLabels(true);
        startAnimalsSlider.setShowTickMarks(true);

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

        animalsStartHBox.setAlignment(Pos.CENTER_RIGHT);
        startAnimalsSlider.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        animalsStartHBox.setSpacing(20);

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
        engine = new GameEngine(normalMap, wrappedMap, normalSimMagic, wrappedSimMagic, startEnergy, moveEnergy, foodEnergy, animalsAtStart);
    }

    private void prepareSimulationGUI() throws Exception {
        simulationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                simulationStage.close();
                try {
                    CsvWriter.closeCSV(saveNormalCsv, saveWrappedCsv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        pauseWrappedSimBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.changeWrappedStop();
                if(!saveWrappedCsv) toCsvWrappedBtn.setDisable(!toCsvWrappedBtn.isDisable());
            }
        });

        pauseNormalSimBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.changeNormalStop();
                if(!saveNormalCsv)  toCsvNormalBtn.setDisable(!toCsvNormalBtn.isDisable());
            }
        });

        toCsvNormalBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveNormalCsv = true;
                toCsvNormalBtn.setDisable(true);
            }
        });

        toCsvWrappedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveWrappedCsv = true;
                toCsvWrappedBtn.setDisable(true);
            }
        });

        toCsvNormalBtn.setDisable(true);
        toCsvWrappedBtn.setDisable(true);

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
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                        if (saveNormalCsv && !engine.getNormalStop()) {
                            System.out.println('a');
                            CsvWriter.appendCsvNormal(normalMap);
                        }
                        if (saveWrappedCsv && !engine.getWrappedStop()){
                            System.out.println('b');
                            CsvWriter.appendCsvWrapped(wrappedMap);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
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
        // refresh the maps
        normalMapVisualiser.refresh();
        wrappedMapVisualiser.refresh();

        // initialize HBoxes of buttons
        HBox normalBtnHBox = new HBox(toCsvNormalBtn, pauseNormalSimBtn);
        normalBtnHBox.setSpacing(30);
        HBox wrappedBtnHBox = new HBox(pauseWrappedSimBtn, toCsvWrappedBtn);
        wrappedBtnHBox.setSpacing(30);
        HBox btnBox = new HBox(normalBtnHBox, wrappedBtnHBox);
        btnBox.setSpacing(120);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(30));



        dominatingGenomeNormal = new Label("Normal map genome dominant \n" + Arrays.toString(normalMap.getAnimalsGenomeDominant()));
        dominatingGenomeWrapped = new Label("Wrapped map genome dominant \n" + Arrays.toString(wrappedMap.getAnimalsGenomeDominant()));

        HBox dominatingGenomeHBox = new HBox(dominatingGenomeNormal, dominatingGenomeWrapped);
        dominatingGenomeHBox.setSpacing(40);
        dominatingGenomeHBox.setAlignment(Pos.CENTER);
        // initialize HBox with maps visualisation
        HBox simHBox = new HBox(normalMapVisualiser.getMapVisualisation(), wrappedMapVisualiser.getMapVisualisation());
        simHBox.setAlignment(Pos.TOP_CENTER);
        simHBox.setSpacing(20);

        // initialize charts relating to the normal map
        VBox normalChartsLeftVBox = new VBox(normalMapAnimalsAlive.getLineChart(), normalMapPlants.getLineChart(), normalMapAverageEnergy.getLineChart());
        normalChartsLeftVBox.setSpacing(20);
        VBox normalChartsRightVBox;

        normalChartsRightVBox = new VBox(normalMapAverageChildrenNumber.getLineChart(), normalMapAverageLifetimeDead.getLineChart());
        if(normalSimMagic) {
            magicNormalMapLabel = new Label("Magic: " + Integer.toString(3 - engine.getMagicLeftNormal()) + " / 3");
            magicNormalMapLabel.setFont(magicInfoFont);
            normalChartsRightVBox.getChildren().add(0, magicNormalMapLabel);
        }
        normalChartsRightVBox.setSpacing(20);
        normalChartsRightVBox.setAlignment(Pos.TOP_CENTER);

        // initialize charts relating to the wrapped map
        VBox wrappedChartsRightVBox = new VBox(wrappedMapAnimalsAlive.getLineChart(), wrappedMapPlants.getLineChart(), wrappedMapAverageEnergy.getLineChart());
        wrappedChartsRightVBox.setSpacing(20);
        wrappedChartsRightVBox.setAlignment(Pos.TOP_CENTER);

        VBox wrappedChartsLeftVBox = new VBox(wrappedMapAverageChildrenNumber.getLineChart(), wrappedMapAverageLifetimeDead.getLineChart());
        if(wrappedSimMagic){
            magicWrappedMapLabel = new Label("Magic: " + Integer.toString(3 - engine.getMagicLeftWrapped()) + " / 3");
            magicWrappedMapLabel.setFont(magicInfoFont);
            wrappedChartsLeftVBox.getChildren().add(0, magicWrappedMapLabel);
        }

        wrappedChartsLeftVBox.setSpacing(20);
        wrappedChartsLeftVBox.setAlignment(Pos.TOP_CENTER);

        // initialize HBox containing maps and buttons
        VBox mapsAndButtonsVBox = new VBox(simHBox, btnBox, dominatingGenomeHBox);
        mapsAndButtonsVBox.setSpacing(40);
        mapsAndButtonsVBox.setAlignment(Pos.TOP_CENTER);

        // initialize mainHBox - containing every element of the scene
        mainHBox = new HBox(normalChartsLeftVBox, normalChartsRightVBox,  mapsAndButtonsVBox, wrappedChartsLeftVBox, wrappedChartsRightVBox);
        mainHBox.setPadding(new Insets(10));
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(20);
        simulationStage.setScene(new Scene(mainHBox));
    }

    public void updateCharts(){
        if(!engine.getNormalStop()) {
            int days = normalMap.getDays();
            normalMapAnimalsAlive.updateData(days, normalMap.getAnimalsCnt());
            normalMapPlants.updateData(days, normalMap.getPlantsCnt());
            normalMapAverageEnergy.updateData(days, normalMap.getAverageEnergy());
            normalMapAverageChildrenNumber.updateData(days, normalMap.getAverageChildrenNumber());
            normalMapAverageLifetimeDead.updateData(days, normalMap.getDeadAnimalsAverageLifetime());
        }
        if(!engine.getWrappedStop()) {
            int days = wrappedMap.getDays();
            wrappedMapAnimalsAlive.updateData(days, wrappedMap.getAnimalsCnt());
            wrappedMapPlants.updateData(days, wrappedMap.getPlantsCnt());
            wrappedMapAverageEnergy.updateData(days, wrappedMap.getAverageEnergy());
            wrappedMapAverageChildrenNumber.updateData(days, wrappedMap.getAverageChildrenNumber());
            wrappedMapAverageLifetimeDead.updateData(days, wrappedMap.getDeadAnimalsAverageLifetime());
        }
    }
}
