package api;

import java.util.Collection;
import java.util.HashMap;

import gameClient.util.EdgesJson;
import gameClient.util.GraphJson;
import gameClient.util.NodesJson;

public class DS_DWGraph implements directed_weighted_graph {

	private HashMap<Integer, node_data> nodes;
	// private HashMap<Integer, HashMap<Integer, edge_data>> edges;
	private int modeCount;
	private int edgeSize;

	public DS_DWGraph(GraphJson gj) {

		this.nodes = new HashMap<Integer, node_data>();
		for (NodesJson nj : gj.getNodes()) {
			HashMap<Integer, edge_data> inEdges = new HashMap<Integer, edge_data>();
			HashMap<Integer, edge_data> outEdges = new HashMap<Integer, edge_data>();
			for (EdgesJson ej : gj.getEdges()) {
				if(ej.getSrc()==nj.getId())
					outEdges.put(ej.getDest(), new Edge(ej));
				if (ej.getDest()==nj.getId())
					inEdges.put(ej.getSrc(), new Edge(ej));
			}
			this.nodes.put(nj.getId(), new NodeData(nj, inEdges, outEdges));
		}
		this.edgeSize = gj.getEdges().length;
		this.modeCount = 0;
	}

	public DS_DWGraph(directed_weighted_graph graph) {
		this.modeCount = graph.getMC();
		this.nodes = new HashMap<Integer, node_data>();
		this.edgeSize = graph.edgeSize();
		for (node_data node : graph.getV()) {
			this.nodes.put(node.getKey(), new NodeData(node));
		}
	}

	public DS_DWGraph() {
		this.modeCount = 0;
		this.nodes = new HashMap<Integer, node_data>();
		this.edgeSize = 0;
	}

	@Override
	public node_data getNode(int key) {
		return this.nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
			if (((NodeData) this.nodes.get(src)).getOutEdges().containsKey(dest))
				return ((NodeData) this.nodes.get(src)).getOutEdges().get(dest);
		}
		return null;
	}

	@Override
	public void addNode(node_data n) {
		if (!this.nodes.containsKey(n.getKey())) {
			nodes.put(n.getKey(), n);
			this.modeCount++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) {
		if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
			if (!((NodeData) this.nodes.get(src)).getOutEdges().containsKey(dest)) {
				((NodeData) this.nodes.get(src)).getOutEdges().put(dest, new Edge(src, dest, w));
				this.modeCount++;
				this.edgeSize++;
			}
			if (!((NodeData) this.nodes.get(dest)).getInEdges().containsKey(src)) {
				((NodeData) this.nodes.get(dest)).getInEdges().put(src, new Edge(src, dest, w));
			}
		}

	}

	@Override
	public Collection<node_data> getV() {
		return (Collection<node_data>) this.nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if (this.nodes.containsKey(node_id))
			return ((NodeData) this.nodes.get(node_id)).getOutEdges().values();
		return null;
	}

	@Override
	public node_data removeNode(int key) {
		NodeData rm = (NodeData) this.nodes.remove(key);
		for (Integer n : rm.getInEdges().keySet()) {
			removeEdge(n, key);
		}
		for (Integer n : rm.getOutEdges().keySet()) {
			removeEdge(n, key);
		}
		rm.setInEdges(null);
		rm.setOutEdges(null);
		this.modeCount++;
		return rm;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
			NodeData srcNode = ((NodeData) this.nodes.get(src));
			NodeData dstNode = ((NodeData) this.nodes.get(dest));
			if (dstNode.getInEdges().containsKey(src))
				dstNode.getInEdges().remove(src);
			if (srcNode.getOutEdges().containsKey(dest)) {
				this.modeCount++;
				this.edgeSize++;
				return srcNode.getOutEdges().remove(dest);
			}
		}
		return null;
	}

	@Override
	public int nodeSize() {
		return this.nodes.size();
	}

	@Override
	public int edgeSize() {
		return this.edgeSize;

	}

	@Override
	public int getMC() {
		return this.modeCount;
	}

}
