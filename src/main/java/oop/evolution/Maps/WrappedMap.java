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
}
