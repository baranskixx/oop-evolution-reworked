package oop.evolution.Maps;

import oop.evolution.OnMapObjects.Animal;
import oop.evolution.Vector2d;

/**
 * Class representing normal map.
 * Normal Map is fenced and animals can't make a move outside a map.
 */
public class NormalMap extends AbstractMap{

    public NormalMap(int width, int height, double jRatio){
        super(width, height, jRatio);
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
}
