package oop.evolution;


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


    /**
     * Class constructor.
     * @param nMap NormalMap to run simulation on.
     * @param wMap WrappedMap to run simulation on.
     * @param nMagic Is simulation run on normalMap a magic one?
     * @param wMagic Is simulation run on wrappedMap a magic one?
     */
    public GameEngine(NormalMap nMap, WrappedMap wMap, boolean nMagic, boolean wMagic){
        normal = nMap;
        wrapped = wMap;
        normalMagic = nMagic;
        wrappedMagic = wMagic;
    }

    @Override
    public void run() {
        ;
    }
}
