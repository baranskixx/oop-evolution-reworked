package oop.evolution;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;

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
    public final int    MAP_MAX_DIMENSION           = 50;
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
    public final VBox mainVBox          = new VBox(mapWidthHBox, mapHeightHBox, jungleRatioHBox, startEnergyHBox, moveEnergyHBox, foodEnergyHBox, magicHBox, btnHBox);
    // startScene
    public final Scene startScene = new Scene(mainVBox);

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setting startStage properties
        startStage.setTitle(TITLE);
        startStage.getIcons().add(ICON);
        startStage.setWidth(START_STAGE_WIDTH);
        startStage.setHeight(START_STAGE_HEIGHT);
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
}
