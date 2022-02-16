package oop.evolution.Maps;

import oop.evolution.Interfaces.IWorldMap;
import oop.evolution.OnMapObjects.Animal;
import oop.evolution.Vector2d;

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
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
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

    /**
     * Check if given Vector2d is positioned within map borders.
     * @param position Vector2D.
     * @return True if position is not crossing map borders. False otherwise.
     */
    public boolean onMap(Vector2d position){
        return mapLowerLeft.precedes(position) && mapUpperRight.follows(position);
    }


}
