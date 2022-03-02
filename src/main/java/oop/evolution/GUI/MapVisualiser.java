package oop.evolution.GUI;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import oop.evolution.Maps.AbstractWorldMap;
import oop.evolution.OnMapObjects.Animal;
import oop.evolution.OnMapObjects.Grass;
import oop.evolution.OnMapPositioning.Vector2d;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Class is responsible for visualisation of the whole map.
 */
public class MapVisualiser {
    public final static Image animalImg = new Image("img/animal.png");
    public final static Image plantImg  = new Image("img/plant.png");

    public final static int MAP_GRID_SIZE = 440;
    public final int FIELD_SIZE;

    private AbstractWorldMap map;
    private LinkedHashMap<Vector2d, MapField> mapFields = new LinkedHashMap<>();
    
    public MapVisualiser(AbstractWorldMap m){
        map = m;
        FIELD_SIZE = MAP_GRID_SIZE / Math.max(map.mapWidth, map.mapHeight);

        for (int x=0; x < map.mapWidth; x++){
            for (int y=0; y < map.mapHeight; y++){
                Vector2d vec = new Vector2d(x, y);
                if (map.insideJungle(vec)) mapFields.put(vec, new MapField(FIELD_SIZE, true));
                else mapFields.put(vec, new MapField(FIELD_SIZE, false));
            }
        }
    }

    public void refresh(){
        for (int y = 0; y < map.mapHeight; y++) {
            for (int x = 0; x < map.mapWidth; x++) {
                Vector2d pos = new Vector2d(x, y);
                Object ob = map.objectAt(pos);
                MapField field = mapFields.get(pos);

                if (ob == null) field.updateView(null, -1);
                else if (ob.getClass() == Grass.class) field.updateView(plantImg, -1);
                else {
                    field.updateView(animalImg, ((Animal) ob).getEnergy());
                }

                mapFields.put(pos, field);
            }
        }
    }

    public GridPane getMapVisualisation(){
        GridPane mapGridPane = new GridPane();

        for (int y = 0; y < map.mapHeight; y++) {
            int row = map.mapHeight - y - 1;
            for (int x = 0; x < map.mapWidth; x++) {
                mapGridPane.add(mapFields.get(new Vector2d(x, y)).getView(), x, row);
            }
        }

        return mapGridPane;
    }

    public void showSpecialFields(){
        int [] dominatingGenome = map.getAnimalsGenomeDominant();
        LinkedList<Animal> mapAnimals = map.getAnimals();

        for(Animal a: mapAnimals){
            if(Arrays.equals(dominatingGenome, a.getGenome()))
               mapFields.get(a.getPosition()).enableSpecial();
        }
    }

    public void hideSpecialFields(){
        for(Vector2d pos : mapFields.keySet()){
            mapFields.get(pos).disableSpecial();
        }
    }
}
