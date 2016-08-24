package prediction;

import plotting.WindVector;

import java.util.List;

/**
 * Created by lapost48 on 8/23/2016.
 */
public class PriorityBuoyList {

    BuoyNode headX;
    BuoyNode tailX;

    BuoyNode headY;
    BuoyNode tailY;

    public PriorityBuoyList() {
        this.headX = null;
        this.tailX = null;
        this.headY = null;
        this.tailY = null;
    }



    public void add(WindVector windVector) {
        boolean firstX = true;
        BuoyNode tempX = headX;
        while(windVector.getLongComponent() < tempX.getX()) {
            firstX = false;
            tempX = tempX.nextX;
        }
        boolean firstY = true;
        BuoyNode tempY = headY;
        while(windVector.getLatComponent() < tempY.getY()) {
            firstY = false;
            tempY = tempY.nextY;
        }
        new BuoyNode(windVector, tempX.nextX, tempX, tempY.nextY, tempY);

    }

    private class BuoyNode {

        WindVector windVector;

        BuoyNode nextX;
        BuoyNode prevX;

        BuoyNode nextY;
        BuoyNode prevY;

        public BuoyNode(WindVector windVector) {
            this.windVector = windVector;
        }

        public BuoyNode(WindVector windVector, BuoyNode nextX, BuoyNode prevX, BuoyNode nextY, BuoyNode prevY) {
            this.windVector = windVector;
            this.nextX = nextX;
            this.prevX = prevX;
            this.nextY = nextY;
            this.prevY = prevY;
        }

        float getX() {
            return windVector.getLongComponent();
        }

        float getY() {
            return windVector.getLatComponent();
        }

    }

}

