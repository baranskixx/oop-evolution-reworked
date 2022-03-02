package oop.evolution.GUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Class responsible for visualisation of single mapField.
 */
public class MapField {
    public final int    sizePx;
    private ImageView   imgView = new ImageView();
    private Button      btn = new Button();
    private boolean     special = false;

    private static final String backgroundJungleStr = "-fx-background-color: #8ae234";
    private static final String backgroundSteppeStr = "-fx-background-color: #bcf5ee";
    private static final String backgroundSpecialStr = "-fx-background-color: #6666ff";

    private final String backgroundDefaultStr;

    private double opacityUnder20 = 0.2;
    private double opacityUnder50 = 0.4;
    private double opacityUnder100 = 0.6;
    private double opacityUnder500 = 0.8;
    private double opacityUnder1000 = 1.0;

    /**
     * Class constructor.
     * @param size Width and height of the field.
     * @param insideJungle Is this field inside the jungle?
     */
    public MapField(int size, boolean insideJungle){
        sizePx = size;
        btn.setMinSize(sizePx, sizePx);
        btn.setPrefSize(sizePx, sizePx);
        btn.setMaxSize(sizePx, sizePx);

        if(insideJungle)    backgroundDefaultStr = backgroundJungleStr;
        else                backgroundDefaultStr = backgroundSteppeStr;
        btn.setStyle(backgroundDefaultStr);
    }

    /**
     * Set given Img as new Button's image.
     * @param newImg Image to be set as button's image. Can be null.
     */
    public void updateView(Image newImg, int energy){
        imgView.setImage(newImg);
        imgView.setFitHeight(sizePx/2);
        imgView.setFitWidth(sizePx/2);
        if (energy != -1){
            if(energy < 20){
                imgView.setOpacity(opacityUnder20);
            } else if (energy < 50){
                imgView.setOpacity(opacityUnder50);
            } else if (energy < 100){
                imgView.setOpacity(opacityUnder100);
            } else if (energy < 500){
                imgView.setOpacity(opacityUnder500);
            } else imgView.setOpacity(opacityUnder1000);
        } else imgView.setOpacity(1);
        btn.setGraphic(imgView);
        btn.setAlignment(Pos.CENTER);
    }

    /**
     * Get the button representing this map field.
     * @return Button object.
     */
    public Node getView(){
        return btn;
    }

    public void enableSpecial(){
        btn.setStyle(backgroundSpecialStr);
    }

    public void disableSpecial(){
        btn.setStyle(backgroundDefaultStr);
    }
}
