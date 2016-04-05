package myproject.model;


public class Light implements Agent {
    int greenDurationNS = (int) (MP.greenLightTime * RandomGenerator.generateNextRandomDouble());
    int yellowDurationNS = computeYellowDuration(greenDurationNS); // Duration of the North/South
    int greenDurationEW = (int) (MP.greenLightTime * RandomGenerator.generateNextRandomDouble()); // Duration of the East/West green
    int yellowDurationEW = computeYellowDuration(greenDurationEW); // Duration of the East/West
    private int lightState;
    private int state;
    private int lightDuration;

    Light() {
    } // Created only by this package

    public int getState() {
        return lightState;
    }
    // yellow/
    // phase (in seconds)

    private int computeYellowDuration(int greenDuration) {
        //ensure yellow time is shorter than green
        int yellowDuration;
        do {
            yellowDuration = (int) (MP.yellowLightTime * RandomGenerator.generateNextRandomDouble());
        } while (yellowDuration >= greenDuration);
        return yellowDuration;
    }

    @Override
    public void setup(double time) {
        lightDuration = greenDurationNS + yellowDurationNS + greenDurationEW + yellowDurationEW;
        double timeCycle = time % lightDuration;
        if (timeCycle <= greenDurationNS) {
            state = MP.GREENNS_REDEW;
        } else if (timeCycle <= (greenDurationNS + yellowDurationNS)) {
            state = MP.YELLOWNS_REDEW;
        } else if (timeCycle <= (greenDurationNS + yellowDurationNS + greenDurationEW)) {
            state = MP.REDNS_GREENEW;
        } else {
            state = MP.REDNS_YELLOWEW;
        }
    }

    @Override
    public void commit(double time) {
        lightState = state;
    }
}

