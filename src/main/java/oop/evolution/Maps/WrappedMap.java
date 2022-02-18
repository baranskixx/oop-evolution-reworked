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
    public void moveAnimals() {
        for (Animal animal : animals){
            int moveDest = animal.getNextMove();
            if (moveDest != 0 && moveDest != 4){
                animal.rotateAnimal(moveDest);
            }
            else {
                Vector2d animalNewPos = moveDest == 0 ? adjustPos(animal.getPosition().add(animal.getDirection().toVector2D()))
                        : adjustPos(animal.getPosition().add(animal.getDirection().toVector2D().opposite()));
                animal.applyMove(animalNewPos);
            }
        }
    }

    /**
     * If Vector2D position is outside the map change its coordinates to
     * place it on map again, stick to the rules of wrappedMap.
     * @param pos Position (Vector2D) to adjust.
     * @return Adjusted Vector2D.
     */
    public Vector2d adjustPos(Vector2d pos){
        return new Vector2d(pos.x % mapWidth, pos.y % mapHeight);
    }
}
