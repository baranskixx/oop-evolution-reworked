package oop.evolution;

import oop.evolution.Maps.AbstractWorldMap;
import oop.evolution.Maps.NormalMap;
import oop.evolution.Maps.WrappedMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class responsible for writing simulation statistics about
 */
public class CsvWriter {

    private static DateTimeFormatter   dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private static LocalDateTime       time = LocalDateTime.now();

    private static String   filenameNormalMap =  System.getProperty("user.dir") + "/src/main/resources/csv/" + "normal" + dtf.format(time) + ".csv";
    private static String   filenameWrappedMap = System.getProperty("user.dir") + "/src/main/resources/csv/" + "wrapped" + dtf.format(time) + ".csv";

    private static long     animalSumNormal             = 0;
    private static long     plantSumNormal              = 0;
    private static long     sumAvgEnergyNormal          = 0;
    private static long     sumAvgDeadLifetimeNormal    = 0;
    private static long     sumAvgChildrenNormal        = 0;
    private static int      daysNormal                  = 0;

    private static long   animalSumWrapped              = 0;
    private static long   plantSumWrapped               = 0;
    private static long   sumAvgEnergyWrapped           = 0;
    private static long   sumAvgDeadLifetimeWrapped     = 0;
    private static long   sumAvgChildrenWrapped         = 0;
    private static int    daysWrapped                   = 0;

    public static void appendCsvNormal(NormalMap map) throws IOException {
        FileWriter writer = new FileWriter(filenameNormalMap, true);

        daysNormal                   = map.getDays();
        animalSumNormal             += map.getAnimalsCnt();
        plantSumNormal              += map.getPlantsCnt();
        sumAvgEnergyNormal          += map.getAverageEnergy();
        sumAvgDeadLifetimeNormal    += map.getDeadAnimalsAverageLifetime();
        sumAvgChildrenNormal        += map.getAverageChildrenNumber();

        writer.append(Integer.toString(map.getDays())).append(",")
                .append(Integer.toString(map.getAnimalsCnt())).append(",")
                .append(Integer.toString(map.getPlantsCnt())).append(",")
                .append(Integer.toString(map.getAverageEnergy())).append(",")
                .append(Integer.toString(map.getDeadAnimalsAverageLifetime())).append(",")
                .append(Integer.toString(map.getAverageChildrenNumber())).append("\n");

        writer.close();
    }

    public static void appendCsvWrapped(WrappedMap map) throws IOException {
        FileWriter writer = new FileWriter(filenameWrappedMap, true);

        daysWrapped                  = map.getDays();
        animalSumWrapped            += map.getAnimalsCnt();
        plantSumWrapped             += map.getPlantsCnt();
        sumAvgEnergyWrapped         += map.getAverageEnergy();
        sumAvgDeadLifetimeWrapped   += map.getDeadAnimalsAverageLifetime();
        sumAvgChildrenWrapped       += map.getAverageChildrenNumber();

        writer.append(Integer.toString(map.getDays())).append(",")
                .append(Integer.toString(map.getAnimalsCnt())).append(",")
                .append(Integer.toString(map.getPlantsCnt())).append(",")
                .append(Integer.toString(map.getAverageEnergy())).append(",")
                .append(Integer.toString(map.getDeadAnimalsAverageLifetime())).append(",")
                .append(Integer.toString(map.getAverageChildrenNumber())).append("\n");

        writer.close();
    }

    public static void closeCSV(boolean normalSaved, boolean wrappedSaved) throws IOException {
        if(normalSaved) {
            FileWriter writerNormal  = new FileWriter(filenameNormalMap, true);
            writerNormal.append("avg,")
                    .append(Long.toString(animalSumNormal / daysNormal)).append(",")
                    .append(Long.toString(plantSumNormal / daysNormal)).append(",")
                    .append(Long.toString(sumAvgEnergyNormal / daysNormal)).append(",")
                    .append(Long.toString(sumAvgDeadLifetimeNormal / daysNormal)).append(",")
                    .append(Long.toString(sumAvgChildrenNormal / daysNormal)).append("\n");
            writerNormal.close();
        }
        if(wrappedSaved) {
            FileWriter writerWrapped = new FileWriter(filenameWrappedMap, true);
            writerWrapped.append("avg,")
                    .append(Long.toString(animalSumWrapped / daysWrapped)).append(",")
                    .append(Long.toString(plantSumWrapped / daysWrapped)).append(",")
                    .append(Long.toString(sumAvgEnergyWrapped / daysWrapped)).append(",")
                    .append(Long.toString(sumAvgDeadLifetimeWrapped / daysWrapped)).append(",")
                    .append(Long.toString(sumAvgChildrenWrapped / daysWrapped)).append("\n");
            writerWrapped.close();
        }
    }

}
