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

    private static final String backgroundJungleStr = "-fx-background-color: #8ae234";
    private static final String backgroundSteppeStr = "-fx-background-color: #d9f5bc";

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
        if(insideJungle)    btn.setStyle(backgroundJungleStr);
        else                btn.setStyle(backgroundSteppeStr);
    }

    /**
     * Set given Img as new Button's image.
     * @param newImg Image to be set as button's image. Can be null.
     */
    public void updateView(Image newImg, int energy){
        imgView.setImage(newImg);
        imgView.setFitHeight(sizePx/2);
        imgView.setFitWidth(sizePx/2);
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
}
