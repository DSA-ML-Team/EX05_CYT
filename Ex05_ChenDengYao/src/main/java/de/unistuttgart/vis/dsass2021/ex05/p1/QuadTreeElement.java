package de.unistuttgart.vis.dsass2021.ex05.p1;

public interface QuadTreeElement {
	Point data = null;
	
  /**
   * Returns the position of a quad tree element.
   * 
   * @return anchor point
   */
  public abstract Point getPosition() ;
}
