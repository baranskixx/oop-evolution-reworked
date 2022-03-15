package oop.evolution.OnMapPositioning;

import java.util.Objects;
import java.util.Random;

/**
 * Representation of objects positions on the map.
 * @author baranskia
 *
 */
public class Vector2d {
    public final int x, y;

    /**
     * Class constructor.
     * @param x X map coordinate.
     * @param y Y map coordinate.
     */
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Convert Vector2d to string.
     * @return String value of Vector2d.
     */
    public String toString(){
        return "(" + this.x +  "," + this.y + ")";
    }

    /**
     * Determinate if vector precedes the other one.
     * @param other Vector for comparison.
     * @return True iff this.x <= other.x && this.x <= other.y. False otherwise.
     */
    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    /**
     * Determinate if vector follows the other one.
     * @param other Vector for comparison.
     * @return True iff this.x >= other.x and this.x >= other.y. False otherwise.
     */
    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    /**
     * Return Vector2d built of max x and y values from both vector.
     * @param other Vector for comparison.
     * @return top-right coordinates of given vectors combination.
     */
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x) , Math.max(this.y, other.y));
    }

    /**
     * Return Vector2d
     * @param other Vector for comparison.
     * @return bottom-left coo
     */
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    /**
     * Sum x and y coordinates of two vectors.
     * @param other Second sum element.
     * @return Subtraction of two vectors.
     */
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtract x and y of other vector from this vector.
     * @param other Subtrahend vector.
     * @return Difference of two Vectors.
     */
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    /**
     * Determinate if this vector is equal to other vector.
     * @param other Vector to compare this vector with.
     * @return True iff this.x == other.x && this.y == other.y. False otherwise.
     */
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d otherVec))
            return false;
        return this.x == otherVec.x && this.y == otherVec.y;
    }

    /**
     * Multiply both coordinates by (-1).
     * @return Result of multiplying both coordinates by -1.
     */
    public Vector2d opposite(){
        return new Vector2d((-1)*this.x, (-1)*this.y);
    }

    /**
     * Generate integer value, generated by hashing algorithm.
     * If two vectors are equal they return the same hashCode.
     * If two vectors are not equal they do not need to return different hashCodes.
     * @return integer value.
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    /**
     * Randomly choose position.
     * @param lowLeft Position that generated vector has to follow.
     * @param upRight Position that generated vector has to precede.
     */
    public static Vector2d generateRandomPosition(Vector2d lowLeft, Vector2d upRight){
        return new Vector2d(new Random().nextInt(lowLeft.x, upRight.x + 1),
                new Random().nextInt(lowLeft.y, upRight.y + 1));


    }
}