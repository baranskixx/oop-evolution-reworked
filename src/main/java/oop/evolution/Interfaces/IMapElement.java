package oop.evolution.Interfaces;

import oop.evolution.OnMapPositioning.Vector2d;

/**
 * Interface responsible for basic functionality of elements placed on the world map.
 * Assumes that Vector2d class is defined.
 *
 * @author baranskia
 *
 */
public interface IMapElement {
    public Vector2d getPosition();
    public String toString();
}