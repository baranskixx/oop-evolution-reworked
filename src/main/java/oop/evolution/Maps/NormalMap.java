package oop.evolution.Maps;

import oop.evolution.OnMapObjects.Animal;
import oop.evolution.OnMapPositioning.Vector2d;

/**
 * Class representing normal map.
 * Normal Map is fenced and animals can't make a move outside a map.
 */
public class NormalMap extends AbstractWorldMap {

    public NormalMap(int width, int height, double jRatio){
        super(width, height, jRatio);
    }

    @Override
    public void moveAnimals() {
        for(Animal animal : animals){
            animal.increaseLifetime();
            int moveDest = animal.getNextMove();
            if(moveDest != 0 && moveDest != 4){
                animal.rotateAnimal(moveDest);
            }
            else {
                Vector2d animalNewPos = moveDest == 0 ? animal.getPosition().add(animal.getDirection().toVector2D()) :
                        animal.getPosition().add(animal.getDirection().toVector2D().opposite());
                if(canMoveTo(animalNewPos)){
                    animal.applyMove(animalNewPos);
                }
            }
        }
    }

    public boolean canMoveTo(Vector2d pos){
        return mapLowerLeft.precedes(pos) && mapUpperRight.follows(pos);
    }
}
