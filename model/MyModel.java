package myproject.model;

import myproject.model.swing.SwingAnimatorBuilder;
import myproject.util.Animator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MyModel extends Observable {
    public static final int SIMPLE_PATTERN = 0;
    public static final int ALTERNATE_PATTERN = 1;
    List<Road> carGeneratingPoint = new ArrayList<>();
    private List<Agent> agents;
    private Animator animator;
    private boolean disposed;
    private double time;
    private SwingAnimatorBuilder builder;
    private int simulationRuntime = 1000;
    private int horizontalRoadNum = 2;
    private int verticalRoadNum = 3;
    private int trafficPattern = ALTERNATE_PATTERN;
    private int carEntryRate = 1;//1 car per second

    /**
     * Creates a model to be visualized using the <code>builder</code>.
     * If the builder is null, no visualization is performed.
     * Each road has one {@link Car}.
     */
    public void resetParameters(){
        setSimulationTimeStep(1);
        setTrafficLightGreenTime(60);
        setTrafficLightYellowTime(20);
        setSimulationRunTime(1000);
        setGridSize(2,3);
        setRoadSegmentLength(200);
        setCarBrakeDistance(10);
        setCarEntryRate(1);
        setCarLength(10);
        setCarMaxVelocity(5);
        setCarStopDistance(5);
        setTrafficPattern(ALTERNATE_PATTERN);
    }
    public MyModel() {
        agents = new ArrayList<Agent>();
    }

    /**
     * Run the simulation for <code>duration</code> model seconds.
     */
    public void run() {
        builder = new SwingAnimatorBuilder();
        setup(builder, horizontalRoadNum, verticalRoadNum);
        animator = builder.getAnimator();
        super.addObserver(animator);
        if (disposed) throw new IllegalStateException();
        for (int i = 0; i < simulationRuntime; i++) {
            time += MP.timeStep;
            if (time % carEntryRate == 0) {
                for (Road road : carGeneratingPoint) {
                    Car car = new Car(road);
                    road.accept(car);
                    agents.add(car);
                }
            }
            Agent[] agents_copy = agents.toArray(new Agent[0]);
            for (Agent a : agents_copy) {
                a.setup(time);
            }
            for (Agent a : agents_copy) {
                a.commit(time);
            }
            super.setChanged();
            super.notifyObservers();
        }
    }

    private void setup(AnimatorBuilder builder, int rows, int columns) {
        List<Road> roads = new ArrayList<>();
        Light[][] intersections = new Light[rows][columns];

        // Add Lights
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                intersections[i][j] = new Light();
                builder.addLight(intersections[i][j], i, j);
                agents.add(intersections[i][j]);
            }
        }
        if (trafficPattern == SIMPLE_PATTERN) {
            boolean eastToWest = false;
            for (int i = 0; i < rows; i++) {
                Road previousRoad = null;
                for (int j = 0; j <= columns; j++) {
                    //build a connected horizontal road
                    Road l = new Road(false);
                    if (previousRoad != null) {
                        previousRoad.setNextRoad(l);
                    }
                    builder.addHorizontalRoad(l, i, j, eastToWest);
                    roads.add(l);
                    if (j != columns) {
                        l.setTrafficLight(intersections[i][j]);
                    }
                    if (j == 0) {
                        carGeneratingPoint.add(l);
                    }
                    previousRoad = l;
                }
            }

            boolean southToNorth = false;
            for (int j = 0; j < columns; j++) {
                Road previousRoad = null;
                for (int i = 0; i <= rows; i++) {
                    Road l = new Road(true);
                    if (previousRoad != null) {
                        previousRoad.setNextRoad(l);
                    }
                    builder.addVerticalRoad(l, i, j, southToNorth);
                    roads.add(l);
                    if (i != rows) {
                        l.setTrafficLight(intersections[i][j]);
                    }
                    if (i == 0) {
                        carGeneratingPoint.add(l);
                    }
                    previousRoad = l;
                }
            }
        } else {
            //ALTERNATE_PATTERN
            boolean eastToWest = false;
            for (int i = 0; i < rows; i++) {
                Road previousRoad = null;
                if (i % 2 == 0) {
                    //0 2 4 rows:
                    for (int j = 0; j <= columns; j++) {
                        //build a connected horizontal road
                        Road l = new Road(false);
                        if (previousRoad != null) {
                            previousRoad.setNextRoad(l);
                        }
                        builder.addHorizontalRoad(l, i, j, eastToWest);
                        roads.add(l);
                        if (j != columns) {
                            l.setTrafficLight(intersections[i][j]);
                        }
                        if (j == 0) {
                            carGeneratingPoint.add(l);
                        }
                        previousRoad = l;
                    }
                } else {
                    //1,3, 5 rows
                    for (int j = columns; j >= 0; j--) {
                        Road l = new Road(false);
                        if (previousRoad != null) {
                            previousRoad.setNextRoad(l);
                        }
                        builder.addHorizontalRoad(l, i, j, !eastToWest);
                        roads.add(l);
                        if (j != 0) {
                            l.setTrafficLight(intersections[i][j - 1]);
                        }
                        if (j == columns) {
                            carGeneratingPoint.add(l);
                        }
                        previousRoad = l;
                    }
                }
            }

            boolean southToNorth = false;
            for (int j = 0; j < columns; j++) {
                Road previousRoad = null;
                if (j % 2 == 0) {
                    //0,2,4 columns: just like simple traffic
                    for (int i = 0; i <= rows; i++) {
                        Road l = new Road(true);
                        if (previousRoad != null) {
                            previousRoad.setNextRoad(l);
                        }
                        builder.addVerticalRoad(l, i, j, southToNorth);
                        roads.add(l);
                        if (i != rows) {
                            l.setTrafficLight(intersections[i][j]);
                        }
                        if (i == 0) {
                            carGeneratingPoint.add(l);
                        }
                        previousRoad = l;
                    }
                } else {
                    //1,3,5 colums: alternating...
                    for (int i = rows; i >= 0; i--) {
                        Road l = new Road(true);
                        if (previousRoad != null) {
                            previousRoad.setNextRoad(l);
                        }
                        builder.addVerticalRoad(l, i, j, !southToNorth);
                        roads.add(l);
                        if (i != 0) {
                            l.setTrafficLight(intersections[i - 1][j]);
                        }
                        if (i == rows) {
                            carGeneratingPoint.add(l);
                        }
                        previousRoad = l;
                    }
                }
            }
        }
    }

    /**
     * Throw away this model.
     */
    public void dispose() {
        animator.dispose();
        disposed = true;
    }

    public void setSimulationTimeStep(double setValue) {
        MP.timeStep = setValue;
    }

    public void setSimulationRunTime(int setValue) {
        simulationRuntime = setValue;
    }

    public void setGridSize(int horizontal, int vertical) {
        horizontalRoadNum = horizontal;
        verticalRoadNum = vertical;
    }

    public void setTrafficPattern(int pattern) {
        trafficPattern = pattern;
    }

    public void setCarEntryRate(int setValue) {
        carEntryRate = setValue;
    }

    public void setRoadSegmentLength(double setValue) {
        MP.roadLength = setValue;
    }

    public void setCarMaxVelocity(double setValue) {
        MP.maxVelocity = setValue;
    }

    public void setCarBrakeDistance(double setValue) {
        MP.maxBrakeDis = setValue;
    }

    public void setCarStopDistance(double setValue) {
        MP.maxBrakeDis = setValue;
    }

    public void setTrafficLightGreenTime(int setValue) {
        MP.greenLightTime = setValue;
    }

    public void setCarLength(int setValue) {
        MP.carLength = setValue;
    }

    public void setTrafficLightYellowTime(int setValue) {
        MP.yellowLightTime = setValue;
    }
}
