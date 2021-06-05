package de.unistuttgart.vis.dsass2021.ex05.p1;

/**
 * This class represents an two-dimensional point
 */
public class Point {

    private static int counter = 0;

    private final int id;

    private final float x;
    private final float y;

    public Point(final float x, final float y) {
        this.x = x;
        this.y = y;
        this.id = counter++;
    }

    public float getXValue() {
        return this.x;
    }

    public float getYValue() {
        return this.y;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Point)) {
            return false;
        } else {
            return this.id == ((Point) object).id;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
