package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/***
 * The class helps us to do a dfs algorithem and to get the 
 * components of the graph
 * @author yoel hartman
 *
 */


public class Search {

	private directed_weighted_graph graph;
	private HashMap<Integer, Boolean> visited;//visited nodes
	private Stack<Integer> stack;
	private int time;
	private HashMap<Integer, Integer> lowlink;
	private List<List<Integer>> components;

	/**
	 * the function finds the components of the graph
	 * O(|V|+|E|)
	 * @param graph - the graph
	 * @return List<List<Integer>> (list of components)
	 */
	public List<List<Integer>> scc(directed_weighted_graph graph) {//O(|E|+|V|)
		this.graph = graph;
		visited = new HashMap<Integer, Boolean>();
		stack = new Stack<>();
		time = 0;
		lowlink = new HashMap<Integer, Integer>();
		for (node_data node : graph.getV()) {
			visited.put(node.getKey(), false);
			lowlink.put(node.getKey(), 0);
		}
		components = new ArrayList<>();

		for (node_data node : graph.getV()) 
			if (!visited.get(node.getKey()))
				dfs(node.getKey());
		return components;
	}
	
	/**
	 * dfs algorithem
	 * O(|E|)
	 * @param key - the starting node
	 */
	void dfs(int key) {
		//System.out.println("u = "+u);
		lowlink.replace(key, time++);
		visited.replace(key, true);
		stack.add(key);
		boolean uIsComponentRoot = true;


		
		for (int v : ((NodeData)graph.getNode(key)).getOutEdges().keySet()) {
			if (!visited.get(v))
				dfs(v);
			if (lowlink.get(key) > lowlink.get(v)) {
				//System.out.println("u = "+u+", v = "+v);
				lowlink.replace(key, lowlink.get(v));
				uIsComponentRoot = false;
				//System.out.println("uIsComponentRoot = "+uIsComponentRoot);
			}
		}

		if (uIsComponentRoot) {
			List<Integer> component = new ArrayList<>();
			while (true) {
				int x = stack.pop();
				component.add(x);
				lowlink.replace(x,Integer.MAX_VALUE);
				if (x == key)
					break;
			}
			components.add(component);
			//System.out.println("components: "+components);
		}
	}
}