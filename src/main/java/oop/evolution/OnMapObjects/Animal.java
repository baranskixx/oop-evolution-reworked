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

    private Vector2d position;
    private MapDirection direction;

    private int energy;
    private int children = 0;
    private int lifetime = 0;
    private int ID;
    private final int [] genome;

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
        energy = Math.max(energy + change, 0);
    }

    public void rotateAnimal(int rotation){
        direction = direction.rotate(rotation);
    }

    public void applyMove(Vector2d newPos){
        moveInformObservers(position, newPos);
        this.position = newPos;
    }

    /**
     * Setter for ID attribute.
     * @param id New animal ID.
     */
    public void setID(int id){
        ID = id;
    }

    /**
     * Increase children number by 1.
     */
    public void addChildren(){
        children++;
    }

    /**
     * Increase animal's lifetime by 1.
     */
    public void increaseLifetime(){
        lifetime++;
    }

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

    /**
     * Animals equals when they have same ID.
     */
    public boolean equals(Animal other){
        return (this.ID == other.getID());
    }

    /**
     * Make this animal copulate with another. Function returns new Animal - child.
     * Asserts that animals are on the same field on the map and both parents have non-zero energy level.
     * @param matePartner Partner for this animal to copulate with.
     * @return Child of two given animals.
     */
    public Animal copulate (Animal matePartner) throws Exception {
        int sumEnergy = this.getEnergy() + matePartner.getEnergy();

        int genesFromPartner = ((int)(matePartner.getEnergy() / sumEnergy)) * 32;
        int partnerSide = new Random().nextInt(2) + 1;
        int [] newGenome = new int[32];

        if(partnerSide == 2){
            System.arraycopy(this.getGenome(), 0, newGenome, 0, 32 - genesFromPartner);
            System.arraycopy(matePartner.getGenome(), 32-genesFromPartner, newGenome, 32-genesFromPartner, genesFromPartner);
        } else{
            System.arraycopy(matePartner.getGenome(), 0, newGenome, 0, genesFromPartner);
            System.arraycopy(this.getGenome(), genesFromPartner, newGenome, genesFromPartner, 32-genesFromPartner);
        }

        this.changeEnergyLevel((int)(this.energy * (-0.25)));
        matePartner.changeEnergyLevel((int)(matePartner.getEnergy() * (-0.25)));

        this.addChildren();
        matePartner.addChildren();

        return new Animal((int)(sumEnergy * 0.25), this.position, newGenome);
    }

    // All getters below

    /**
     * Getter for ID attribute.
     * @return ID number of animal.
     */
    public int getID(){ return ID; }

    /**
     * Animal's genome getter.
     * @return Genome of the animal - int [32] array.
     */
    public int[] getGenome(){
        return genome;
    }

    /**
     * Getter for energy attribute.
     * @return Current energy of the animal.
     */
    public int getEnergy(){
        return energy;
    }


    /**
     * Get int value of next animal move. Thi int is a random pick from animals genome.
     * @return number from 0 to 7 picked randomly from animals genes.
     */
    public int getNextMove(){
        return genome[new Random().nextInt(32)];
    }

    /**
     * Get current animal direction.
     * @return MapDirection enum value indicating the direction animal is pointing at.
     */
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
     * Return number of children of animal.
     * @return Number of animal's children.
     */
    public int getChildren(){
        return children;
    }

    /**
     * Get lifetime of an animal.
     * @return Lifetime on animal in days.
     */
    public int getLifetime(){
        return lifetime;
    }


}
