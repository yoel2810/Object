package ex2.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import api.DS_DWGraph;
import api.DWGraph_Algo;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;

class Tests {

	private static directed_weighted_graph g;
	

	@BeforeAll
	public static void init() {
		g = new DS_DWGraph();
		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
	}

	@Test
	void test1() {
		assertTrue(g.nodeSize() == 3);
		g.connect(0, 1, 1);
		g.connect(1, 0, 100);
		g.connect(2, 1, 5);
		g.connect(2, 0, 1);
		assertTrue(g.edgeSize() == 4);
		
		System.out.println("test1 s");
	}
	
	@Test
	void test2() {
		dw_graph_algorithms ga = new DWGraph_Algo(g);
		assertFalse(ga.isConnected());
		System.out.println();
		System.out.println(Arrays.toString(ga.shortestPath(0, 1).toArray()));
		System.out.println(ga.shortestPathDist(0, 1));
		//to check save and load
		//System.out.println("test2 s");
		
	}
	
	@Test
	void test3()
	{
		g= new DS_DWGraph();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		assertTrue(g.nodeSize() == 5);
		g.connect(1, 2, 1);
		g.connect(2, 3, 100);
		g.connect(3, 1, 5);
		g.connect(3, 4, 1);
		g.connect(4, 5, 1);
		assertTrue(g.edgeSize() == 5);
		
		System.out.println("test3 s");
	}
	
	@Test
	void test4()
	{
		dw_graph_algorithms ga = new DWGraph_Algo(g);
		assertFalse(ga.isConnected());
		System.out.println();
		//System.out.println(Arrays.toString(ga.shortestPath(5, 4).toArray()));
		System.out.println(ga.shortestPathDist(5, 4));
		ga.save("graph.txt");
		assertTrue(ga.load("graph.txt"));
	}

}
