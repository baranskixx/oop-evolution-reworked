package oop.evolution.Simulation;


import oop.evolution.Interfaces.IEngine;
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;

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
    public GameEngine(NormalMap nMap, WrappedMap wMap, boolean nMagic, boolean wMagic, int sEnergy, int mEnergy, int pEnergy){
        normal = nMap;
        wrapped = wMap;
        normalMagic = nMagic;
        wrappedMagic = wMagic;
        startEnergy = sEnergy;
        moveEnergy = mEnergy;
        plantEnergy = pEnergy;
    }

    @Override
    public void run() {
        normal.moveAnimals();
        wrapped.moveAnimals();
    }
}
