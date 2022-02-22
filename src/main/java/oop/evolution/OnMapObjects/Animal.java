package oop.evolution.OnMapObjects;


import oop.evolution.Interfaces.IMapElement;
import oop.evolution.Interfaces.IPositionChangeObserver;
import oop.evolution.OnMapPositioning.MapDirection;
import oop.evolution.OnMapPositioning.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Class representing single animal on the map.
 *
 * @author baranskia
 */
public class Animal implements IMapElement {
    private int energy;
    private Vector2d position;
    private MapDirection direction;
    private final int [] genome;
    private int ID;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    /**
     * Animal default class constructor.
     * @param startEnergy Level of energy animal starts with. Must be a positive number.
     * @param pos Starting position of animal.
     */
    public Animal(int startEnergy, Vector2d pos) throws Exception {
        if(startEnergy <= 0){
            throw new Exception("Tried to create an animal with non-positive energy level!");
        }

        energy = startEnergy;
        position = pos;
        direction = MapDirection.getRandomDirection();

        genome = new Random().ints(32, 0, 8).toArray();
        Arrays.sort(genome);
    }

    /**
     * Animal constructor with specified genome.
     * @param startEnergy Level of energy animal starts with. Must be a positive number.
     * @param pos Starting position of animal.
     * @param gen Array of integers, will be set as animal genome. Must contain integer numbers from 0 to 32 and
     *            have exactly 32 elements.
     */

    public Animal(int startEnergy, Vector2d pos, int [] gen) throws Exception {
        if(startEnergy <= 0){
            throw new Exception("Tried to create an animal with non-positive energy level!");
        }
        if (gen.length != 32){
            throw new Exception("Genome length differs from 32!");
        }
        Arrays.sort(gen);
        if (gen[0] < 0 || gen[31] > 7){
            throw new Exception("Wrong values in genome array!");
        }

        energy = startEnergy;
        position = pos;
        direction = MapDirection.getRandomDirection();
        genome = gen;
    }

    /**
     * Used to change animal energy during eating, moving or copulating.
     * @param change Value added to animal energy (can be negative).
     */
    public void changeEnergyLevel(int change){
        energy += change;
    }

    public MapDirection getDirection(){
        return direction;
    }

    /**
     * Get current animal position.
     * @return Vector2d - animal position.
     */
    @Override
    public Vector2d getPosition() {
        return position;
    }

    /**
     * Get int value of next animal move. Thi int is a random pick from animals genome.
     * @return number from 0 to 7 picked randomly from animals genes.
     */
    public int getNextMove(){
        return genome[new Random().nextInt(32)];
    }

    public void rotateAnimal(int rotation){
        direction = direction.rotate(rotation);
    }

    public void applyMove(Vector2d newPos){
        moveInformObservers(position, newPos);
        this.position = newPos;
    }

    /**
     * Getter for energy attribute.
     * @return Current energy of the animal.
     */
    public int getEnergy(){
        return energy;
    }

    /**
     * Setter for ID attribute.
     * @param id New animal ID.
     */
    public void setID(int id){
        ID = id;
    }

    /**
     * Getter for ID attribute.
     * @return ID number of animal.
     */
    public int getID(){ return ID; }

    /**
     * Add a new Observer. Observer now will be informed about every move of this animal.
     * @param observer New observer tracking animal's moves.
     */
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    /**
     * Inform all observers of the animal about movement.
     * @param oldPos Position from which animal moved.
     * @param newPos Position animal moved to.
     */
    public void moveInformObservers(Vector2d oldPos, Vector2d newPos){
        for(IPositionChangeObserver ob : observers){
            ob.positionChanged(oldPos, newPos);
        }
    }
}
