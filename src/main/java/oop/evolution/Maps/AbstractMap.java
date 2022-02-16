package oop.evolution.Maps;

import oop.evolution.Interfaces.IWorldMap;
import oop.evolution.OnMapObjects.Animal;
import oop.evolution.OnMapObjects.Grass;
import oop.evolution.Vector2d;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Abstract class that defines properties of the map and operations on those properties.
 *
 * @author baranskia
 *
 */
public abstract class AbstractMap implements IWorldMap {
    // map and jungle size
    public final int mapWidth, mapHeight;
    public final double jungleRatio;
    public final int jungleWidth, jungleHeight;
    public final Vector2d mapLowerLeft, mapUpperRight;
    public final Vector2d jungleLowerLeft, jungleUpperRight;

    private int animalsCnt = 0;
    private int plantsCnt  = 0;

    private ArrayList<Vector2d> emptyFieldsJungle = new ArrayList<>();
    private ArrayList<Vector2d> emptyFieldsSteppe = new ArrayList<>();
    private LinkedHashMap<Vector2d, Integer> elementsOnField = new LinkedHashMap<>();

    private LinkedList<Animal> animals = new LinkedList<>();
    private LinkedList<Grass> plants = new LinkedList<>();
    /**
     * Class constructor.
     * @param width Number of fields in single row of a map.
     * @param height Number of fields in single column of a map.
     * @param jRatio How many % of map fields are set as a jungle.
     */
    public AbstractMap(int width, int height, double jRatio){
        mapWidth = width;
        mapHeight = height;
        jungleRatio = jRatio;

        mapLowerLeft = new Vector2d(0, 0);
        mapUpperRight = new Vector2d(mapWidth-1, mapHeight-1);

        // calculate centre of a map in both dimensions
        int xCentre = mapWidth / 2;
        int yCentre = mapHeight / 2;

        // minimal size of a jungle is 1 field
        jungleWidth = Math.min(mapWidth - 2, Math.max(1, (int) (mapWidth * jungleRatio)));
        jungleHeight = Math.min(mapHeight - 2, Math.max(1, (int) (mapHeight * jungleRatio)));

        // calculate jungle borders
        int jLeft, jRight, jBottom, jTop;

        if (mapWidth % 2 == 1){
            if (jungleWidth % 2 == 1){
                jLeft = xCentre - (jungleWidth / 2);
                jRight = xCentre + (jungleWidth / 2);
            } else {
                jLeft = xCentre - (jungleWidth / 2);
                jRight = xCentre + (jungleWidth / 2) - 1;
            }
        } else{
            if (jungleWidth % 2 == 1){
                jLeft = xCentre - (jungleWidth / 2) - 1;
            } else {
                jLeft = xCentre - (jungleWidth / 2);
            }
            jRight = xCentre + (jungleWidth / 2) - 1;
        }

        if (mapHeight % 2 == 1){
            if (jungleHeight % 2 == 1){
                jBottom = yCentre - (jungleHeight / 2);
                jTop = yCentre + (jungleHeight / 2);
            } else {
                jBottom = yCentre - (jungleHeight / 2);
                jTop = yCentre + (jungleHeight / 2) - 1;
            }
        } else{
            if (jungleHeight % 2 == 1){
                jBottom = yCentre - (jungleHeight / 2) - 1;
            } else {
                jBottom = yCentre - (jungleHeight / 2);
            }
            jTop = yCentre + (jungleHeight / 2) - 1;
        }

        jungleLowerLeft = new Vector2d(jLeft, jBottom);
        jungleUpperRight = new Vector2d(jRight, jTop);

        // fill emptyFields arrayLists and elementsOnFields LinkedHashMap
        for (int x=0; x < jungleWidth; x++){
            for (int y=0; y < jungleHeight; y++){
                Vector2d pos = new Vector2d(x, y);
                elementsOnField.put(pos, 0);
                if (insideJungle(pos)){
                    emptyFieldsJungle.add(pos);
                } else{
                    emptyFieldsSteppe.add(pos);
                }
            }
        }
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d animalPos = animal.getPosition();
        if (!onMap(animalPos)){
            return false;
        } else {
            animalsCnt++;
            animals.add(animal);
            elementsOnField.put(animalPos, elementsOnField.get(animalPos) + 1);
            if(insideJungle(animalPos)){
                emptyFieldsJungle.remove(animalPos);
            } else {
                emptyFieldsSteppe.remove(animalPos);
            }
            return true;
        }
    }
    
    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }

    public String toString(){
        return "Wymiary mapy: " + mapWidth + "x" + mapHeight +
                "\nLewy dolny punkt mapy: " + mapLowerLeft +
                "\nPrawy górny punkt mapy: " + mapUpperRight +
                "\nWymiary dżungli: " + jungleWidth + "x" + jungleHeight +
                "\nLewy dolny punkt dżungli: " + jungleLowerLeft +
                "\nPrawy górny punkt dżungli: " + jungleUpperRight;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    /**
     * Check if given Vector2D lies inside maps jungle.
     * @param pos Vector2D to be checked.
     * @return True if given Vector2D lies inside the jungle. False otherwise.
     */
    public boolean insideJungle(Vector2d pos){
        return jungleLowerLeft.precedes(pos) && jungleUpperRight.follows(pos);
    }

    /**
     * Check if given Vector2d is positioned within map borders.
     * @param position Vector2D.
     * @return True if position is not crossing map borders. False otherwise.
     */
    public boolean onMap(Vector2d position){
        return mapLowerLeft.precedes(position) && mapUpperRight.follows(position);
    }

}
