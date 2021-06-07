package de.unistuttgart.vis.dsass2021.ex05.p1;

import java.util.LinkedList;
import java.util.List;

public abstract class QuadTree<T extends QuadTreeElement> {



  /**
   * top left sub-node of the quad tree
   */
  QuadTree<T> topLeft = null;
  
  /**
   * top right sub-node of the quad tree
   */
  QuadTree<T> topRight = null;

  /**
   * bottom left sub-node of the quad tree
   */
  QuadTree<T> bottomLeft = null;

  /**
   * bottom right sub-node of the quad tree
   */
  QuadTree<T> bottomRight = null;

  /**
   * If non-null this node is a leaf node and this member contains the elements
   */
  List<T> leafElements = null;

  /**
   * Holds the area of this node
   */
  Rectangle boundingBox;

  /**
   * The maximum number of elements in a leaf node
   */
  int maxLeafElements = 4;
  
  /**
   * Performs a two-dimensional range query and adds all elements in the
   * selected area to the result list.
   * 
   * This method does not clear the given list before add new elements. 
   * This is for a recursive implementation.
   * 
   * @param resultList: List that is used to store the elements that are
   *        contained in the selected area
   * @param searchArea: the result list contains all element that are within
   *        the selected area
   */
  
  public abstract void rangeQuery(final List<T> resultList,
      final Rectangle searchArea);

  /**
   * This method recursively creates the quad tree.
   * 
   * @param list of elements to be stored in the quad tree
   * @throws QuadTreeException
   */
  abstract void createQuadTree(final List<T> list)
      throws IllegalArgumentException;

  /**
   * Return the rectangle that encloses the point set of this node.
   * 
   * @return the bounding box
   */
  Rectangle getBoundingBox() {
    return this.boundingBox;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof QuadTree)) {
      return false;
    } else {
      if (leafElements != null) {
        return leafElements.equals(((QuadTree<?>) object).leafElements);
      } else {
        return topRight.equals(((QuadTree<?>) object).topRight)
            && topLeft.equals(((QuadTree<?>) object).topLeft)
            && bottomLeft.equals(((QuadTree<?>) object).bottomLeft)
            && bottomRight.equals(((QuadTree<?>) object).bottomRight);
      }
    }
  }
}
