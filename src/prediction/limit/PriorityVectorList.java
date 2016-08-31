package prediction.limit;

import vectors.WeatherVector;

/**
 * Created by lapost48 on 8/23/2016.
 */
public class PriorityVectorList {

    public enum Direction {
        NORTH, SOUTH, EAST, WEST;

        private float coordinate;

        Direction() {}

        void setCoordinate(float coordinate) {
            this.coordinate = coordinate;
        }
        float getCoordinate() {
            return coordinate;
        }
    }

    WeatherVector[] vectors;

    public PriorityVectorList() {
        this.vectors = new WeatherVector[4];
    }

    public WeatherVector get(Direction direction) {
        switch(direction) {
            case NORTH: return vectors[0];
            case SOUTH: return vectors[1];
            case EAST : return vectors[2];
            case WEST : return vectors[3];
            default   : return null;
        }
    }

    public void add(WeatherVector weatherVector) {
        for(int i = 0; i < vectors.length; i++) {
            if(vectors[i] == null) {
                vectors[i] = weatherVector;
            } else {
                Direction direction = Direction.values()[i];
                float vectorCoordinate = vectorCoordinate(weatherVector, direction);
                if(direction.getCoordinate() < vectorCoordinate) {
                    direction.setCoordinate(vectorCoordinate);
                    vectors[i] = weatherVector;
                }
            }
        }
    }

    private float vectorCoordinate(WeatherVector vector, Direction direction) {
        float coordinate = 1;
        switch(direction) {
            case SOUTH: coordinate *= -1;
            case NORTH: return vector.getLatComponent() * coordinate;
            case WEST : coordinate *= -1;
            case EAST : return vector.getLongComponent() * coordinate;
            default   : System.err.println("No direction given");
                        System.exit(1);
        }
        return 0;
    }

}

