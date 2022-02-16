package oop.evolution.OnMapObjects;


import oop.evolution.Interfaces.IMapElement;
import oop.evolution.MapDirection;
import oop.evolution.Vector2d;

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

    public void applyAnimalMove(int move){
        if (move != 0 && move != 4){
            direction = direction.rotate(move);
        } else{
            position = (move == 0? position.add(direction.toVector2D()) : position.add(direction.toVector2D().opposite()));
        }
    }

}
