package myproject.model;

/**
 * Static class for model parameters.
 */
public class MP {
    public static final int GREENNS_REDEW = 0;
    public static final int YELLOWNS_REDEW = 1;
    public static final int REDNS_GREENEW = 2;
    public static final int REDNS_YELLOWEW = 3;
    /**
     * Length of cars, in meters
     */
    public static double carLength = 10;
    /**
     * Length of roads, in meters
     */
    public static double roadLength = 200;
    /**
     * Maximum car velocity, in meters/second
     */
    public static double maxVelocity = 5;
    public static double maxBrakeDis = 10;
    public static double maxStopDis = 5;
    public static double timeStep = 1;

    public static int greenLightTime = 60;
    public static int yellowLightTime = 20;

    private MP() {
    }

}

