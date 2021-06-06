package de.unistuttgart.vis.dsass2021.ex05.p1;

import java.util.LinkedList;
import java.util.List;

/**
 * The class SimpleQuad
 */

public class SimpleQuadTree<T extends QuadTreeElement> extends QuadTree<T> {

    /**
     * a constructor of a SimpleQuadTree with 2 parameters. A bounding box will be
     * computed by the algorithm.
     *
     * @param elements,          a list of elements to be saved in the quadtree.
     * @param maxElementsInLeaf, the maximum limit of elements, in which a leaf node
     *                           can save.
     * @throws IllegalArgumentException, if parameter is missing, or element ==
     *                                   null, or the maxElementsInLeaf < 1.
     */
    public SimpleQuadTree(final List<T> elements, final int maxElementsInLeaf) throws IllegalArgumentException {
        if (elements == null || maxElementsInLeaf < 1) {
            throw new IllegalArgumentException();
        }
        this.boundingBox = computeBoundingBox(elements);
        this.maxLeafElements = maxElementsInLeaf;
        createQuadTree(elements);

    }

    /**
     * a constructor to create a SimpleQuadTree with 3 parameters. The bounding box
     * is predefined.
     *
     * @param elements,          a list of elements to be saved in the quadtree.
     * @param maxElementsInLeaf, the maximum limit of elements, in which a leaf node
     *                           can save.
     * @param boundingBox,       the smallest rectangle, in which all elements can
     *                           be included.
     * @throws IllegalArgumentException, if parameter is missing, or element ==
     *                                   null, or the maxElementsInLeaf < 1.
     */
    private SimpleQuadTree(final List<T> elements, final int maxElementsInLeaf, final Rectangle boundingBox)
            throws IllegalArgumentException {
        if (elements == null || maxElementsInLeaf < 1) {
            throw new IllegalArgumentException();
        }
        this.boundingBox = boundingBox;
        this.maxLeafElements = maxElementsInLeaf;
        createQuadTree(elements);
    }

    /**
     * This returns a bounding box, which is smallest of all possible bounding boxes
     * that include all elements of the list.
     *
     * @param elements, a list of elements to be saved in the quadtree, must not be
     *                  null, must not contain null
     * @return a bounding box in type Rectangle
     */
    private Rectangle computeBoundingBox(final List<T> elements) {
        // TODO Insert code for assignment 5.1.b
    	// we have to find the the most right down point of all elements.
    	float X_max = 0 , Y_max = 0;
    	float X_min = elements.get(0).getPosition().getXValue();
    	float Y_min = elements.get(0).getPosition().getYValue();
    	for( int i =0 ; i < elements.size(); i++ ) {
    		Point current_point = elements.get(i).getPosition();
    		float current_X = current_point.getXValue();
    		float current_Y = current_point.getYValue();
    		if (current_X > X_max) {X_max = current_X;}
    		if (current_Y > Y_max) {Y_max = current_Y;}
    		if (current_X > X_max) {X_max = current_X;}
    		if (current_Y > Y_max) {Y_max = current_Y;}
    	}
    	//construct a new Rectangle, which is the bounding box
    	Rectangle BoundingBox  = new Rectangle(X_min, Y_min, X_max-X_min, Y_max-Y_min);
    	return BoundingBox;
    }

    /**
     * This creates a quadtree using a recursive algorithm. If the size of the list is
     * smaller than the maximum limit of leaf elements, a split is not necessary.
     * All elements will be saved directly in the current node. Otherwise, a splitting
     * into four child nodes is necessary. A linked list for each subnode will be
     * initialized. The size of the bounding box of each subnode is the half of its
     * width and height. This calls the Rectangle() function. Elements contained
     * in the bounding box will be added into the linked list. With the list of
     * elements and the bounding box as well as the maxLeafElements, it creates a
     * SimpleQuadTree. This again includes the createQuadTree() function, which will
     * be called recursively.
     *
     * @param list, a list of elements in type T
     * @throws IllegalArgumentException, when parameter is missing.
     */
    void createQuadTree(final List<T> list) throws IllegalArgumentException {
        // TODO Insert code for assignment 5.1.c
    	// First of all, have to check if the list cotains only one element,
    	// which means: this node is a leaf node
    	if ( list.size()== 1 ) { this.leafElements.add(list.get(0));}
    	//in else case, the current list contains always more then one element
    	else {
	    	Rectangle current_box = computeBoundingBox( list );
	    	float box_X = current_box.getX(), current_box_Y = current_box.getY();
	    	float box_Width = current_box.getWidth(), box_Height = current_box.getHeight();
	    	// these will store splitted element
	    	List<T> TL_Kind = new LinkedList<T>();
	    	List<T> TR_Kind = new LinkedList<T>();
	    	List<T> BL_Kind = new LinkedList<T>();
	    	List<T> BR_Kind = new LinkedList<T>();
	    	for ( int i = 0 ; i < list.size(); i++) {
	    		float point_X = list.get(i).getPosition().getXValue();
	    		float point_Y = list.get(i).getPosition().getYValue();
	    		if( point_X <= box_X + box_Width/2  &&  point_Y <= current_box_Y + box_Height/2) {
	    			TL_Kind.add(list.get(i));
	    		}
	    		else if( point_X > box_X + box_Width/2  &&  point_Y <= current_box_Y + box_Height/2) {
	    			TR_Kind.add(list.get(i));
	    		}
	    		else if( point_X <= box_X + box_Width/2  &&  point_Y > current_box_Y + box_Height/2) {
	    			BL_Kind.add(list.get(i));
	    		}
	    		else if( point_X > box_X + box_Width/2  &&  point_Y > current_box_Y + box_Height/2) {
	    			BR_Kind.add(list.get(i));
	    		}
	    	}
	    	// now we assign the splitted list into TL,TR,BL,BR
	    	//if for example the TL_Kind list contains only one element,
	    	// then it will automatically generate the TL child node as a leaf node
	    	//which all four node is null and leafElement stores the point element
	    	if ( !TL_Kind.isEmpty()) {  		
	    		this.topLeft = createSubTree(TL_Kind, computeBoundingBox(TL_Kind));
	    		createQuadTree( TL_Kind );
	    	}
	    	else if ( TL_Kind.isEmpty()) {this.topLeft = null;}
	    	
	    	
	    	if ( !TR_Kind.isEmpty()) {
	    		this.topRight = createSubTree(TR_Kind, computeBoundingBox(TR_Kind));
	    		createQuadTree( TR_Kind );
	    	}
	    	else if( TR_Kind.isEmpty()) {this.topRight = null;}
	    	
	    	
	    	if ( !BL_Kind.isEmpty()) { 
	    		this.bottomLeft = createSubTree(BL_Kind, computeBoundingBox(BL_Kind));
	    		createQuadTree( BL_Kind);
	    	}
	    	else if( BL_Kind.isEmpty()) {this.bottomLeft = null;}
	    	
	    	
	    	if ( !BR_Kind.isEmpty()) { 
	    		this.bottomRight = createSubTree(BR_Kind, computeBoundingBox(BR_Kind));
	    		createQuadTree( BR_Kind);
	    	}
	    	else if(BR_Kind.isEmpty()) {this.bottomRight = null ;}
    	}
    }

    /**
     * Creates a sub QuadTree with the elements contained in the bounding box.
     * The QuadTree is empty if no elements are contained in the bounding box.
     *
     * @param allElements   all elements of the parent node
     *                      (must be != null, must not contain null)
     * @param boundingBox   the boundingBox to check if an element should be
     *                      inserted into the sub QuadTree (must be != null)
     * @return the SimpleQuadTree that contains all elements within the bounding box
     */
    private SimpleQuadTree<T> createSubTree(final List<T> allElements, final Rectangle boundingBox) {
        assert allElements != null : "allElements is null";
        assert !allElements.contains(null) : "allElements contains null";
        assert boundingBox != null : "boundingBox is null";

        final List<T> elements = new LinkedList<>();
        for (final T item : allElements) {
            if (boundingBox.containsPoint(item.getPosition())) {
                elements.add(item);
            }
        }
        return new SimpleQuadTree<>(elements, this.maxLeafElements, boundingBox);
    }

    /**
     * This inserts all elements located in the requested range in resultList. It
     * first checks whether the query intersects the whole quadtree. After that, the
     * elements will be directly saved in resultList if this is a leaf node. Else it
     * needs to go down to the subnodes recursively until the leaf node and then get
     * the elements. The resultList only save the element, when it locates inside
     * the requested range and is not already part of the list.
     *
     * @param resultList: List that is used to store the elements that are contained
     *                    in the searching area, must be != null it is allowed that
     *                    this list already contains elements, they are not removed,
     *                    however this also does not make any sense
     * @param query:      the searching area, must be != null
     * @throws IllegalArgumentException if any parameter is invalid
     */
    @Override
    public void rangeQuery(final List<T> resultList, final Rectangle query) {
        // TODO Insert code for assignment 5.1.d
    	//check if intersects the whole tree
    	//firstly, have to compute the bounding box of the whole tree
    	//before that, we have to check if the search area has totally no overlap with tree
    	//if it is, then do nothing and the resultList remain empty
    	if( this.boundingBox.intersects(query) ) {
    		//now check if the query area contain the whole tree box
    		if( query.getX()<= this.getBoundingBox().getX() && 
    			query.getY() <= this.getBoundingBox().getY() &&
    			query.getX()+query.getWidth() >= this.getBoundingBox().getX()+this.getBoundingBox().getWidth() &&
    			query.getY()+query.getHeight() >= this.getBoundingBox().getY()+this.getBoundingBox().getHeight()){
    			//cause target area contains the whole tree area,
    			//we only need to store all leaf of tree into it
    			//for sure, have to check if the elements duplicates
    			if( this.leafElements != null ) { 
    				if( !resultList.contains(this.leafElements.get(0)) ) {
    					resultList.add( this.leafElements.get(0));
    				}
    			}
    			else if( this.topLeft.leafElements == null) { this.topLeft.rangeQuery( resultList, query); }
    			else if( this.topRight.leafElements == null) { this.topRight.rangeQuery( resultList, query); }
    			else if( this.bottomLeft.leafElements == null) { this.bottomLeft.rangeQuery( resultList, query); }
    			else if( this.bottomRight.leafElements == null) { this.bottomRight.rangeQuery( resultList, query); }
    		}
    		//so far, we have considered the relationship of containing whole tree
    		//now we work on the case: overlapping only part of the tree 
    		else {
    		// we only care the subnodes, which intersect with query
    			List<QuadTree<T>> subNodes = new LinkedList<QuadTree<T>>();
    			// check if current recursive sub tree is not a leaf node and it has children
    			if( this.leafElements == null && this.topLeft != null) {
    				subNodes.add( this.topLeft );
    				subNodes.add( this.topRight );
    				subNodes.add( this.bottomLeft );
    				subNodes.add( this.bottomRight );
    				for( int i =0; i < 4; i++) {
    					if (  subNodes.get(i)!= null && query.intersects( subNodes.get(i).getBoundingBox() ) ) {
    						subNodes.get(i).rangeQuery( resultList, query);
    					}
    				}
    			}
    			else if( this.leafElements != null) {
    				if( query.containsPoint( this.leafElements.get(0).getPosition()) && 
    						!resultList.contains( this.leafElements.get(0)) ){
    					resultList.add( this.leafElements.get(0) );
    				}
    			}
    		}
    	}
    }
    	
 
}












