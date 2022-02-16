package oop.evolution.Interfaces;

import oop.evolution.OnMapObjects.Animal;
import oop.evolution.Vector2d;

/**
 * Interface responsible for realising Observer design patter. Used to inform other objects about
 * changes in animal position.
 *
 * @author baranskia
 */

public interface IPositionChangeObserver{
    /**
     * Define how observer should behave when observed animal made a move.
     * @param oldPos Position from which animal moved.
     * @param newPos Destination of animal move.
     */
    public void positionChanged(Vector2d oldPos, Vector2d newPos);
}