package oop.evolution.Maps;

import oop.evolution.OnMapObjects.Animal;
import oop.evolution.Vector2d;

/**
 * Map with wrapped edges. When animal crosses the border of a map it appears on opposite border.
 **/
public class WrappedMap extends AbstractMap{
    public WrappedMap(int width, int height, double jRatio){
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
