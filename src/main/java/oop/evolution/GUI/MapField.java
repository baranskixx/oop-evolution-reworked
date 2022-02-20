package oop.evolution.GUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oop.evolution.Maps.AbstractMap;
import oop.evolution.OnMapPositioning.Vector2d;

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

    public void updateView(Image newImg){
        if(newImg != null){
            imgView.setImage(newImg);
            imgView.setFitHeight(sizePx/2);
            imgView.setFitWidth(sizePx/2);
            btn.setGraphic(imgView);
            btn.setAlignment(Pos.CENTER);
        } else btn.setGraphic(null);
    }

    public Node getView(){
        return btn;
    }
}
