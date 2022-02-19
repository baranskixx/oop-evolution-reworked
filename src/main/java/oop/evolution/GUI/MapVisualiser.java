package oop.evolution.GUI;

import javafx.scene.layout.GridPane;
import oop.evolution.Maps.AbstractMap;
import oop.evolution.OnMapPositioning.Vector2d;

import java.util.LinkedHashMap;

/**
 * Class is responsible for visualisation of the whole map.
 */
public class MapVisualiser {
    public final static int MAP_GRID_SIZE = 440;


    private AbstractMap map;
    private GridPane mapGrid;
    private LinkedHashMap<Vector2d, MapField> mapFields = new LinkedHashMap<>();
    
    public MapVisualiser(AbstractMap m){
        map = m;

        for (int x=0; x < map.mapWidth; x++){
            for (int y=0; y < map.mapHeight; y++){
                Vector2d vec = new Vector2d(x, y);
                if (map.insideJungle(vec)) mapFields.put(vec, new MapField(MAP_GRID_SIZE / Math.max(map.mapWidth, map.mapHeight), true));
                else mapFields.put(vec, new MapField(MAP_GRID_SIZE / Math.max(map.mapWidth, map.mapHeight), false));
            }
        }
    }
}
