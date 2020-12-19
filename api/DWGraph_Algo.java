package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.gson.Gson;


public class DWGraph_Algo implements dw_graph_algorithms {
	private directed_weighted_graph graph;

	public DWGraph_Algo() {
		this.graph = new DS_DWGraph();
	}

	public DWGraph_Algo(directed_weighted_graph g) {
		this.graph = g;
	}

	/**
	 * returns a HashMap of the previous nodes (the node's parent)
	 * O(|V|*degree(v))
	 * @param s - an ID of a node
	 * @return HashMap<Integer, node_data> - the keys are the nodes' ID and the values are the parent of the node.
	 */
	public HashMap<Integer, node_data> getParents(int s) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s);
		HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();// to check if nodes is visited
		for (node_data nd : this.graph.getV()) {//O(n)
			visited.put(nd.getKey(), false);
		}
		visited.replace(s, true);// the first node is visited
		HashMap<Integer, node_data> prev = new HashMap<Integer, node_data>();
		for (node_data nd : this.graph.getV()) {//O(n)
			prev.put(nd.getKey(), null);
		} // creating a hash map with all the keys of the nodes
		while (!q.isEmpty()) {//O(n)
			int node = q.remove();
			Collection<edge_data> neighbours = this.graph.getE(node);

			for (edge_data e : neighbours) {//O(k)
				if (visited.get(e.getDest()) == false) {
					q.add(e.getDest());
					visited.replace(e.getDest(), true);
					prev.replace(e.getDest(), this.graph.getNode(node));
				}
			}

		}
		return prev;
	}

	/**
	 * get the path from a start node to an end node
	 * O(path size)
	 * @param s - start node id
	 * @param e - end node id
	 * @param prev -  parents of the node
	 * @return ArrayList<node_data> path of nodes from start to end.
	 */
	public ArrayList<node_data> getPathFromStartToEnd(int s, int e, HashMap<Integer, node_data> prev) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		// node_data[] path = new node_data[prev.length];
		int i = e;
		
		//O(m)
		while (prev.get(i) != null) {// if == null it means that the node has no parent (the first node)
			path.add(i);
			i = prev.get(i).getKey();

		}
		path.add(i);

		ArrayList<node_data> reverse = new ArrayList<node_data>();
		for (i = 0; i < path.size(); i++)
			reverse.add(this.graph.getNode(path.get(path.size() - 1 - i)));// reverse the arr

		if (reverse.get(0).getKey() == s)
			return reverse;
		return new ArrayList<node_data>();
	}

	@Override
	public void init(directed_weighted_graph g) {
		this.graph = g;

	}

	@Override
	public directed_weighted_graph getGraph() {
		return this.graph;
	}

	@Override
	public directed_weighted_graph copy() {
		return (directed_weighted_graph) new DS_DWGraph(this.graph);
	}

	@Override
	public boolean isConnected() {
		Search s = new Search();
		return s.scc(this.getGraph()).size() == 1;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// if (this.prevModeCount == this.graph.getMC())
		directed_weighted_graph initg = copy();// o(n^2)
		Dijkstra(this.graph.getV().toArray(), this.graph.getNode(src));
		double ret = this.graph.getNode(dest).getWeight();
		this.init(initg);
		if (ret < Integer.MAX_VALUE)
			return ret;
		else
			return -1;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if (src == dest) {
			ArrayList<node_data> a = new ArrayList<node_data>();
			a.add(this.graph.getNode(src));
			return a;
		}
		HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
		directed_weighted_graph initg = copy();// o(n^2)
		Dijkstra(this.graph.getV().toArray(), this.graph.getNode(src));
		ArrayList<node_data> path = new ArrayList<node_data>();
		path.add(this.graph.getNode(dest));
		while (dest != src) {
			NodeData node = ((NodeData) this.graph.getNode(dest));
			if (node == null)
				return null;
			if (visited.containsKey(node.getKey()) == false) {
				visited.put(node.getKey(), false);
			} else {
				if (visited.get(node.getKey()))
					return null;// circle
			}
			visited.replace(node.getKey(), true);
			dest = node.getTag();
			path.add(this.graph.getNode(dest));
		}
		ArrayList<node_data> rev = new ArrayList<node_data>();
		for (int i = 0; i < path.size(); i++) {
			rev.add(path.get(path.size() - 1 - i));
		}
		this.init(initg);
		return rev;
	}

	/**
	 * Dijkstra algorithem
	 * O(|V|^2)
	 * @param nodeArr - nodes of the graph
	 * @param s - the start node
	 */
	public void Dijkstra(Object[] nodeArr, node_data s) {
		if(s==null || nodeArr==null)
			return;
		
		s.setWeight(0);

		PriorityQueue<node_data> que = new PriorityQueue<node_data>();
		for (Object n : nodeArr) {//O(|V|)
			que.add((node_data) n);

		}

		while (que.isEmpty() == false) {// O(|V|)
			node_data u = que.poll();
			for (Integer neiKey : ((NodeData) u).getOutEdges().keySet()) {// O(degree)
				node_data v = this.graph.getNode(neiKey);
				if (v.getInfo() != "visited") {
					double t = u.getWeight() + ((NodeData) u).getOutEdges().get(neiKey).getWeight();
					if (v.getWeight() > t) {
						que.remove(v);// O(|V|)
						v.setWeight(t);
						((NodeData) v).setTag(u.getKey());
						que.add((NodeData) v);// O(|V|*log|V|)

					}

				}

			}
			u.setInfo("visited");
		}

	}

	@Override
	public boolean save(String file) {
		Gson gson = new Gson();
		String json = gson.toJson(this.graph);

		try {
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(json);
			pw.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean load(String file) {
		Gson gson = new Gson();
		try {
			FileReader reader = new FileReader(file);
			this.graph = gson.fromJson(reader, this.graph.getClass());
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

}
