package oop.evolution.Simulation;


import oop.evolution.Interfaces.IEngine;
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;
import oop.evolution.OnMapObjects.Animal;
import oop.evolution.OnMapPositioning.Vector2d;

/**
 * Class that defines game engine used to apply all operations during the simulation:
 * animals movement, breeding, eating ...
 *
 * @author baranskia
 */

public class GameEngine implements IEngine {
    private NormalMap normal;
    private WrappedMap wrapped;
    public final boolean normalMagic;
    public final boolean wrappedMagic;

    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;

    public static int startAnimalsNumber = 10;

    private boolean normalStop = false;
    private boolean wrappedStop = false;

    /**
     * Class constructor.
     * @param nMap NormalMap to run simulation on.
     * @param wMap WrappedMap to run simulation on.
     * @param nMagic Is simulation run on normalMap a magic one?
     * @param wMagic Is simulation run on wrappedMap a magic one?
     * @param sEnergy Start energy of every animal in the simulation.
     * @param mEnergy Animal move energy cost.
     * @param pEnergy Energy given to animals when eating single plant.
     */
    public GameEngine(NormalMap nMap, WrappedMap wMap, boolean nMagic, boolean wMagic, int sEnergy, int mEnergy, int pEnergy) throws Exception {
        normal = nMap;
        wrapped = wMap;
        normalMagic = nMagic;
        wrappedMagic = wMagic;
        startEnergy = sEnergy;
        moveEnergy = mEnergy;
        plantEnergy = pEnergy;

        for(int i=0; i < startAnimalsNumber; i++){
            Animal nAnimal = new Animal(startEnergy, Vector2d.generateRandomPosition(normal.jungleLowerLeft, normal.jungleUpperRight));
            Animal wAnimal = new Animal(startEnergy, Vector2d.generateRandomPosition(normal.jungleLowerLeft, normal.jungleUpperRight));
            normal.place(nAnimal);
            wrapped.place(wAnimal);
        }
    }

    @Override
    public void run() {
        if (!normalStop) normal.nextDay();
        if (!wrappedStop) wrapped.nextDay();
    }

    public void changeWrappedStop(){
        wrappedStop = !wrappedStop;
    }

    public void changeNormalStop(){
        normalStop = !normalStop;
    }

    public boolean getWrappedStop(){
        return wrappedStop;
    }

    public boolean getNormalStop(){
        return normalStop;
    }
}
