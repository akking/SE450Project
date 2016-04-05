package myproject.model;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
    private double position = 0;
    private double velocity;
    //parameters of the car
    private double maxVelocity = MP.maxVelocity * RandomGenerator.generateNextRandomDouble();
    private double brakeDistance = MP.maxBrakeDis * RandomGenerator.generateNextRandomDouble();
    private double stopDistance = computeStopDistance(brakeDistance);
    private java.awt.Color color = new java.awt.Color((int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255));
    private Road currentRoad = null;

    Car(Road inThisRoad) {
        this.currentRoad = inThisRoad;
    } // Created only by this package

    double getBrakeDistance() {
        return brakeDistance;
    }

    private double computeStopDistance(double brakeDistance) {
        //brake distance of a car must be longer than the stop distance
        double stopDis = MP.maxStopDis * RandomGenerator.generateNextRandomDouble();
        while (brakeDistance < stopDis) {
            stopDis = MP.maxStopDis * RandomGenerator.generateNextRandomDouble();
        }
        return stopDis;
    }

    public double getPosition() {
        return position;
    }


    public java.awt.Color getColor() {
        return color;
    }


    @Override
    public void setup(double time) {
        double nextObstacleDistance = currentRoad.nextObstacleDistance(this);
        velocity = (maxVelocity / (brakeDistance - stopDistance)) * (nextObstacleDistance - stopDistance);
        velocity = Math.max(0.0, velocity);
        velocity = Math.min(maxVelocity, velocity);
        velocity = velocity * (MP.roadLength / currentRoad.getRoadLength());
        if ((position + MP.carLength + velocity) > MP.roadLength) {
            //ready to drive to next road but you need to consider the nextObstacleDistance
            if (MP.carLength > nextObstacleDistance) {
                velocity = 0;
            } else {
                driveToNextRoad();
            }
        }
    }


    private void driveToNextRoad() {
        if (currentRoad.hasNextRoad()) {
            currentRoad.nextRoad().accept(this);
            currentRoad.removeCar(this);
            currentRoad = currentRoad.nextRoad();
            resetPosition();
        } else {
            currentRoad.removeCar(this);
        }
    }

    private void resetPosition() {
        position = 0;
    }


    @Override
    public void commit(double time) {
        position += velocity * MP.timeStep;

    }
}
