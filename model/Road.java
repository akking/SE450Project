package myproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A road holds cars.
 */
public class Road {
    private double roadLength = RandomGenerator.generateRandomRoadLength();
    private List<Car> cars = new ArrayList<Car>();
    private Road nextRoad = null;
    //A road holds a traffic light that it contains
    private Light trafficLight = null;
    private boolean isVerticalRoad;

    Road(boolean isVerticalRoad) {
        this.isVerticalRoad = isVerticalRoad;
    } // Created only by this package

    double getRoadLength() {
        return roadLength;
    }

    void setTrafficLight(Light light) {
        trafficLight = light;
    }

    boolean hasNextRoad() {
        return nextRoad != null;
    }

    void removeCar(Car carToRemove) {
        cars.remove(carToRemove);
    }

    Road nextRoad() {
        return nextRoad;
    }

    void setNextRoad(Road nextRoad) {
        this.nextRoad = nextRoad;
    }

    public void accept(Car d) {
        if (d == null) {
            throw new IllegalArgumentException();
        }
        cars.add(d);
    }

    public List<Car> getCars() {
        return cars;
    }

    double nextObstacleDistance(Car car) {
        double distance = 0;
        Car nextCar = null;
        try {//obstacle in the same road:
            int currentCarPosition = cars.indexOf(car);
            int nextCarPosition = currentCarPosition - 1;
            nextCar = cars.get(nextCarPosition);
            distance = nextCar.getPosition() - car.getPosition() - MP.carLength;
        } catch (IndexOutOfBoundsException e) {
            //traffic light may be considered as obstacle
            if (trafficLight != null) {
                int state = trafficLight.getState();
                if (isVerticalRoad) {
                    if (state == MP.REDNS_GREENEW || state == MP.REDNS_YELLOWEW) {
                        distance = MP.roadLength - car.getPosition() - MP.carLength;
                        return distance;
                    } else if (state == MP.YELLOWNS_REDEW && (MP.roadLength - car.getPosition()) < car.getBrakeDistance()) {
                        //if it is yellow and within brake distance, considered as obstacle
                        distance = MP.roadLength - car.getPosition() - MP.carLength;
                        return distance;
                    }
                } else {
                    if (state == MP.GREENNS_REDEW || state == MP.YELLOWNS_REDEW) {
                        distance = MP.roadLength - car.getPosition() - MP.carLength;
                        return distance;
                    } else if (state == MP.REDNS_YELLOWEW && (MP.roadLength - car.getPosition()) < car.getBrakeDistance()) {
                        distance = MP.roadLength - car.getPosition() - MP.carLength;
                        return distance;
                    }
                }
            }

            {//find next obstacle in nextRoad
                int betweenNumOfRoad = 0;
                Road nextRoadToFind = nextRoad;
                while (nextRoadToFind != null) {
                    betweenNumOfRoad++;
                    if (nextRoadToFind.cars.size() > 0) {
                        nextCar = nextRoadToFind.cars.get(nextRoadToFind.cars.size() - 1);
                        break;
                    }
                    nextRoadToFind = nextRoadToFind.nextRoad;
                }
                if (nextCar == null) {
                    distance = Double.MAX_VALUE;
                } else {
                    if (betweenNumOfRoad == 0) {
                        distance = nextCar.getPosition() - car.getPosition() - MP.carLength;
                    } else {
                        distance = (betweenNumOfRoad - 1) * MP.roadLength + nextCar.getPosition() + (MP.roadLength - car.getPosition() - MP.carLength);
                    }
                }
            }
        }
        return distance;
    }
}
