package oop.evolution.OnMapObjects;

import oop.evolution.Interfaces.IMapElement;
import oop.evolution.OnMapPositioning.Vector2d;

import java.util.Objects;

/**
 * Class representing single field of grass on the map.
 *
 * @author baranskia
 */
public class Grass implements IMapElement {
    public final Vector2d position;

    /**
     * Default constructor.
     * @param pos Position where grass is placed on.
     */
    public Grass(Vector2d pos){
        position = pos;
    }

    /**
     * Position getter.
     * @return Position where grass is placed.
     */
    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grass grass = (Grass) o;
        return position.equals(grass.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
