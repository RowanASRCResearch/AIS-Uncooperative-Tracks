package prediction.limit;

import vectors.GeoVector;

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

    GeoVector[] vectors;

    public PriorityVectorList() {
        this.vectors = new GeoVector[4];
    }

    public GeoVector get(Direction direction) {
        switch(direction) {
            case NORTH: return vectors[0];
            case SOUTH: return vectors[1];
            case EAST : return vectors[2];
            case WEST : return vectors[3];
            default   : return null;
        }
    }

    public void add(GeoVector geoVector) {
        for(int i = 0; i < vectors.length; i++) {
            if(vectors[i] == null) {
                vectors[i] = geoVector;
            } else {
                Direction direction = Direction.values()[i];
                float vectorCoordinate = vectorCoordinate(geoVector, direction);
                if(direction.getCoordinate() < vectorCoordinate) {
                    direction.setCoordinate(vectorCoordinate);
                    vectors[i] = geoVector;
                }
            }
        }
    }

    private float vectorCoordinate(GeoVector vector, Direction direction) {
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

