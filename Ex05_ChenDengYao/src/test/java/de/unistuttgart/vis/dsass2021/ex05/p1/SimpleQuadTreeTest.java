package de.unistuttgart.vis.dsass2021.ex05.p1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unistuttgart.vis.dsass2021.ex05.p1.Point;
import de.unistuttgart.vis.dsass2021.ex05.p1.QuadTree;
import de.unistuttgart.vis.dsass2021.ex05.p1.QuadTreeElement;
import de.unistuttgart.vis.dsass2021.ex05.p1.Rectangle;
import de.unistuttgart.vis.dsass2021.ex05.p1.SimpleQuadTree;


public class SimpleQuadTreeTest {
	
	public class ElementObj implements QuadTreeElement{
		float x;
		float y;
		
		public ElementObj(float x, float y) {
			this.x=x;
			this.y=y;
		}
		@Override
		public Point getPosition() {
			// TODO Auto-generated method stub
			return (new Point( this.x, this.y));
		}
		
	}
	
	
	@Test
	public void testQuadTree() {
		ElementObj p0 = new ElementObj( 3, 3 );
		ElementObj p1 = new ElementObj( 3, 6 );
		ElementObj p2 = new ElementObj( 1, 8 );
		ElementObj p3 = new ElementObj( 4, 6 );
		ElementObj p4 = new ElementObj((float)0.1, (float)0.1);
		ElementObj p5 = new ElementObj(10,10);
		List<ElementObj> elements = new ArrayList<ElementObj>();
		elements.add(p0);
		elements.add(p1);
		elements.add(p2);
		elements.add(p3);
		elements.add(p4);
		elements.add(p5);
		SimpleQuadTree<ElementObj> myTree = new SimpleQuadTree<ElementObj>( elements,4);
		Rectangle searchArea = new Rectangle( (float)2.5 ,1, 10, 10  );
		List<ElementObj> results = new ArrayList<ElementObj>();
		myTree.rangeQuery(results,  searchArea);
		System.out.println("we found :");
		System.out.println( results.size());
		System.out.println("targets");	
		
	}
	
}
