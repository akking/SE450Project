package myproject.model;

import java.util.Random;

/**
 * A utility class for random parameters
 */
public class RandomGenerator {
    public static final double MIN = 0.3;
    public static final double MAX = 1;
    public static final int SEED = 201506;
    public static final double ROAD_LENGTH_MAX = 500;
    public static final double ROAD_LENGTH_MIN = 200;
    private static Random rg = new Random(SEED);

    static public double generateNextRandomDouble() {
        double toReturn = MIN + (MAX - MIN) * rg.nextDouble();
        return toReturn;
    }

    public static double generateRandomRoadLength() {
        double toReturn = ROAD_LENGTH_MIN + (ROAD_LENGTH_MAX - ROAD_LENGTH_MIN) * rg.nextDouble();
        //use for debug
        return toReturn;
    }
}
