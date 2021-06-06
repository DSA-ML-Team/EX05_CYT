package de.unistuttgart.vis.dsass2021.ex05.p1;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class represents a quadrilateral, where the angles are 90 degrees.
 */
public class Rectangle {

    // Upper left corner of the rectangle
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    /**
     * Generate a new rectangle specified with the upper left corner, width and
     * height. Note that y values the coordinate systems increase from top to down
     * as usual in computer graphics.
     * 
     * @param leftXValue  left position of the rectangle
     * @param upperYValue upper position of the rectangle
     * @param width       the width of the rectangle. The width must be greater than
     *                    0.
     * @param heigth      the height of the rectangle The height must be greater
     *                    than 0.
     * @throws IllegalArgumentException if width or height are 0 or less.
     */
    public Rectangle(final float leftXValue, final float upperYValue, final float width, final float height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width or height are 0 or less");
        }
        this.x = leftXValue;
        this.width = width;
        this.y = upperYValue;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    /**
     * Checks whether a given point is inside this rectangle.
     * 
     * The point is inside the rectangle if all it is on the corners, edges or
     * inside the rectangle.
     * 
     * 
     * @param point the point to check
     * @return true if the point is inside the rectangle false if not
     * @throws IllegalArgumentException if the point is null
     */
    public boolean containsPoint(final Point point) {
        if (point == null) {
            throw new IllegalArgumentException("point is null");
        }
        final float rightXCoordinate = this.x + this.width;
        final float lowerYCoordinate = this.y + this.height;
        if (point.getXValue() >= this.x && point.getXValue() <= rightXCoordinate && point.getYValue() >= this.y
                && point.getYValue() <= lowerYCoordinate) {
            return true;
        }
        return false;      
    }

    /**
     * Checks whether a given rectangle intersects this rectangle.
     * 
     * The given rectangle intersects this rectangle if it is (1) inside this
     * rectangle, (2) both rectangles have an overlapping part inside, or (3) both
     * rectangles overlap on the edge.
     * 
     * @param rectangle the rectangle to check
     * @return true if the rectangle intersects this rectangle else false
     * @throws IllegalArgumentException if the rectangle is null
     */
    public boolean intersects(final Rectangle rectangle) {
        if (rectangle == null) {
            throw new IllegalArgumentException("rectangle is null");
        }

        final boolean intersectionOnXAxis = intervalIntersection(this.x, this.x + this.width, rectangle.x,
                rectangle.x + rectangle.width);
        final boolean intersectionOnYAxis = intervalIntersection(this.y, this.y + this.height, rectangle.y,
                rectangle.y + rectangle.height);
        if (intersectionOnXAxis && intersectionOnYAxis) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param intervalLeftThisRectanlge   First interval left
     * @param intervalRightThisRectangle  First interval right
     * @param intervalLeftOtherRectangle  Second interval left
     * @param intervalRightOtherRectangle Second interval right
     * @return true if the two intervals overlap
     */
    private boolean intervalIntersection(float intervalLeftThisRectanlge, float intervalRightThisRectangle,
            float intervalLeftOtherRectangle, float intervalRightOtherRectangle) {
        if ((intervalLeftOtherRectangle >= intervalLeftThisRectanlge)
                && (intervalLeftOtherRectangle <= intervalRightThisRectangle))
            return true;
        if ((intervalLeftThisRectanlge >= intervalLeftOtherRectangle)
                && (intervalLeftThisRectanlge <= intervalRightOtherRectangle))
            return true;
        return false;
    }

    /**
     * Compute the bounding rectangle for a collection of rectangles.
     * 
     * @param rectangles the collection of rectangles which should be included in
     *                   the bounding rectangle
     * @return the bounding rectangle
     * @throws IllegalArgumentException if rectangles or one of its elements is null
     *                                  or if rectangles is empty
     */
    public static Rectangle getBoundingBox(final Collection<Rectangle> rectangles) {
        if (rectangles == null || rectangles.contains(null) || rectangles.isEmpty()) {
            throw new IllegalArgumentException("rectangles or one of its elements is null or rectangles is empty");
        }
        float minX = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        for (Rectangle rectangle : rectangles) {
            if (rectangle.x < minX) {
                minX = rectangle.x;
            }
            if (rectangle.x + rectangle.width > maxX) {
                maxX = rectangle.x + rectangle.width;
            }
            if (rectangle.y < minY) {
                minY = rectangle.y;
            }
            if (rectangle.y + rectangle.height > maxY) {
                maxY = rectangle.y + rectangle.height;
            }
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Computes a new rectangle based on this rectangle where the size is decreased
     * so none of the given points are included in the new rectangle. The upper left
     * corner of this rectangle has to stay the same.
     * 
     * @param points - A <code>Collection</code> of Points for example an ArrayList
     * @return the new rectangle with decreased size
     * @throws IllegalArgumentException if the collection or one the points in
     *                                  contains are null
     * 
     */
    public Rectangle excludePoints(final Collection<Point> points) {
        // TODO Insert code for assignment 5.1.a
    	// firstly, we gonna find the point, which has Xmin and Ymin, and get its value.
    	// we compose an Iterator through points object
    	Iterator<Point> point_iterator = points.iterator();
    	float X_min = this.x + this.width , Y_min = this.y + this.height;
    	while( point_iterator.hasNext() ) {
    		Point current_point = point_iterator.next();
    		if( current_point.getXValue() < X_min | current_point.getYValue() < Y_min ) {
    		X_min = current_point.getXValue();
    		Y_min = current_point.getYValue();
    		}
    	}
    	//so far we stack the most left and up point X,Y value as X_min, Y_min
    	//now we create a new bounding box according to it, but with distance 1 from edges.
    	Rectangle new_Rectangle = new Rectangle(this.x, this.y, X_min-this.x-1, Y_min-this.y-1   );
    	return new_Rectangle;
    }

}
