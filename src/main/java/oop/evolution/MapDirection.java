package oop.evolution;

import java.util.Random;

/**
 * Enum used to describe and modify positions of animals on the map.
 *
 * @author baranskia
 *
 */
public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    final int MAP_DIR_LENGTH = 8;

    /**
     * Convert MapDirection to Int.
     * 0 == North, 1 == NORTH_EAST, 2 == EAST ... (clockwise).
     * @return Integer representing direction.
     */
    public int toInt(){
        return switch (this) {
            case NORTH -> 0;
            case NORTH_EAST -> 1;
            case EAST -> 2;
            case SOUTH_EAST -> 3;
            case SOUTH -> 4;
            case SOUTH_WEST -> 5;
            case WEST -> 6;
            case NORTH_WEST -> 7;
        };
    }

    /**
     * Rotate current direction by rotation * (45 degrees). Used to rotate animals during the simulation.
     */
    public MapDirection rotate(int rotation){
        int val = this.toInt();
        return MapDirection.values()[(val + rotation) % this.MAP_DIR_LENGTH];
    }

    /**
     * Get Vector2d representation of the MapDirection.
     * @return Vector2d corresponding to MapDirection value.
     */
    public Vector2d toVector2D(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }

    /**
     * Pick a random value of the MapDirection enum.
     * @return a random MapDirection.
     */
    public static MapDirection getRandomDirection(){
        return values()[new Random().nextInt(values().length)];
    }
}
