package de.unistuttgart.vis.dsass2021.ex05.p2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.unistuttgart.vis.dsass2021.ex05.p1.Point;
import de.unistuttgart.vis.dsass2021.ex05.p1.Rectangle;

/**
 * This class represents a two-dimensional collision map. A collision map is a
 * data structure that stores a set of rectangles. Given another rectangle, this
 * data structure allows retrieving all rectangles that intersect this
 * rectangle.
 */
public class CollisionMap {

    // If the resolution of the grid is not specified by the user we use this
    // default resolution.
    private static final int GRID_RESOLUTION_X = 100;
    private static final int GRID_RESOLUTION_Y = 100;

    /**
     * Rectangle that encapsulates all rectangles in the collision map.
     */
    private final Rectangle gridRectangle;

    /**
     * A two-dimensional array of {@link java.util.List} serves as the data
     * structure for storing the rectangles. Each element of the array holds a list
     * of rectangles. At the same time, each element of the array is associated with
     * an area of the bounding rectangle {@link CollisionMap.gridRectangle} through
     * the transform methods ({@link CollisionMap.transformX} and
     * {@link CollisionMap.transformY}. These areas are called cells.
     */
    private List<Rectangle>[][] map;

    /**
     * Creates a {@link CollisionMap} from a set of rectangles.
     * 
     * @param rectangles that are placed in the collision map
     * @throws CollisionMapException
     */
    public CollisionMap(final Set<Rectangle> rectangles) throws IllegalArgumentException {
        this(rectangles, GRID_RESOLUTION_X, GRID_RESOLUTION_Y);
    }

    /**
     * Creates a {@link CollisionMap} from a set of rectangles and specified grid
     * resolutions.
     *
     * @param rectangles      that are placed in the collision map, must be != null
     * @param gridResolutionX the resolution in x direction, must be >= 1
     * @param gridResolutionY the grid resolution in y direction, must be >= 1
     * @throws IllegalArgumentException if any parameter has an invalid value
     */
    public CollisionMap(final Set<Rectangle> rectangles, final int gridResolutionX, final int gridResolutionY)
            throws IllegalArgumentException {
        if (rectangles == null || gridResolutionX < 1 || gridResolutionY < 1) {
            throw new IllegalArgumentException("rectangles is null or gridResolution is less than 1");
        }
        this.gridRectangle = Rectangle.getBoundingBox(rectangles);
        generateCollisionMap(gridResolutionX, gridResolutionY);
        try {
            fillCollisionMap(rectangles);
        } catch (final CollisionMapOutOfBoundsException exception) {
            exception.printStackTrace();
        }
    }

    
    /**
     * Fill this collision map with a set of rectangles. A rectangle is added to a
     * cell if it overlaps with it (that includes just touching it). Afterwards,
     * each cell of the collision map should contain all rectangles that cover the
     * cell.
     * 
     * @param rectangles is a set of rectangles to insert, it must be != null
     * @throws CollisionMapOutOfBoundsException if a rectangle is out of the bounds
     *                                          of this rectangle
     */
    private void fillCollisionMap(Set<Rectangle> rectangles) throws CollisionMapOutOfBoundsException {
        // TODO Insert code for assignment 5.2.a
    	Iterator<Rectangle> rectangle_iterator = rectangles.iterator();
    	while( rectangle_iterator.hasNext() ) {
    		Rectangle current_rec = rectangle_iterator.next();
    		float recX =current_rec.getX() ,recY =current_rec.getY();
    		float recWidth =current_rec.getWidth() ,recHeight =current_rec.getHeight();
    		int n_recX = (int) transformX(recX);
    		int n_recY = (int) transformY(recY);
    		int n_recXmax = (int) (transformX(recX)+recWidth);
    		int n_recYmax = (int) (transformY(recY)+recHeight);
    		for(int i=n_recX;i<=n_recXmax ;i++) {
    			for(int j=n_recY;j<=n_recYmax;j++) {
    		    map.add((j,i),current_rec);
    		    
    			
    		}
    		
    		
    		}
    	
    	
    }
    
    
    /**
     * Given a rectangle, this method returns a set of potential colliding
     * rectangles (rectangles in the same cells).
     *
     * @param rectangle  the rectangle to test overlap with must be != null
     * @return a set with all Rectangles that possibly overlap with rectangle
     * @throws CollisionMapOutOfBoundsException if the rectangle is out of the
     *                                          bounding box for this CollisionMap
     */
    private Set<Rectangle> getCollisionCandidates(final Rectangle rectangle) throws CollisionMapOutOfBoundsException {
        // TODO Insert code for assignment 5.2.b
    	
    }

   

    /**
     * Transform a x coordinate from rectangle space to the internal space of the
     * {@link CollisionMap}. For accessing specific cells of the grid the return
     * value must be rounded and cast appropriately.
     * 
     * @param x coordinate of a point
     * @return x coordinate of given point in the internal space
     * @throws CollisionMapOutOfBoundsException if x is too low or too high
     */
    private float transformX(final float x) throws CollisionMapOutOfBoundsException {
        if (x < this.gridRectangle.getX() || x > this.gridRectangle.getX() + this.gridRectangle.getWidth()) {
            throw new CollisionMapOutOfBoundsException("x coordinate is outside the defined range.");
        } else {
            return ((x - this.gridRectangle.getX()) / this.gridRectangle.getWidth()) * map[0].length;
        }
    }

    /**
     * Transform a y coordinate from rectangle space to the internal space of the
     * {@link CollisionMap}. For accessing specific cells of the grid the return
     * value must be rounded and cast appropriately.
     * 
     * @param y coordinate of a point
     * @return y coordinate of given point in the internal space
     * @throws CollisionMapOutOfBoundsException if y is too low or too high
     */
    private float transformY(float y) throws CollisionMapOutOfBoundsException {
        if (y < this.gridRectangle.getY() || y > this.gridRectangle.getY() + this.gridRectangle.getHeight()) {
            throw new CollisionMapOutOfBoundsException("y coordinate is outside the defined range.");
        } else {
            return ((y - this.gridRectangle.getY()) / this.gridRectangle.getHeight()) * map.length;
        }
    }

    /**
     * Check if the given rectangle collides with rectangles in the
     * {@link CollisionMap}.
     * 
     * @param rectangle the rectangle to check for collision
     * @return true if the given rectangle intersects one of the rectangles in the
     *         collision map.
     * @throws IllegalArgumentException if rectangle is null
     */
    public boolean collide(final Rectangle rectangle) {
        // TODO Insert code for assignment 5.2.c
    }

    /**
     * Allocate the collision map
     * 
     * @param gridResolutionX
     * @param gridResolutionY
     */
    @SuppressWarnings("unchecked")
    private void generateCollisionMap(int gridResolutionX, int gridResolutionY) {
        map = new ArrayList[gridResolutionY][gridResolutionX];
        for (int y = 0; y < gridResolutionY; ++y) {
            for (int x = 0; x < gridResolutionX; ++x) {
                map[y][x] = new ArrayList<Rectangle>();
            }
        }
    }
}
