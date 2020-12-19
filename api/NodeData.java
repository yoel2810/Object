package api;

import java.util.HashMap;
import gameClient.util.NodesJson;
import gameClient.util.Point3D;

public class NodeData implements node_data, Comparable<node_data>  {

	private int key;
	private String info;
	private int tag;
	private geo_location location;
	private double w;
	private HashMap<Integer, edge_data> inEdges;//Edges that go in
	private HashMap<Integer, edge_data> outEdges;//Edges that go out

	public NodeData(NodesJson nj, HashMap<Integer, edge_data> inEdges, HashMap<Integer, edge_data> outEdges) {
		this.key = nj.getId();
		this.location = new Point3D(nj.getPos());
		this.info = "";
		this.tag = 0;
		this.w = Integer.MAX_VALUE;
		this.inEdges = inEdges;
		this.outEdges = outEdges;
	}

	public NodeData(node_data other) {
		this.key = other.getKey();
		this.info = new String(other.getInfo());
		this.tag = other.getTag();
		this.w = other.getWeight();
		this.location = new Point3D(other.getLocation().x(), other.getLocation().y(), other.getLocation().z());
		this.inEdges = new HashMap<Integer, edge_data>();
		this.outEdges = new HashMap<Integer, edge_data>();
		for (edge_data e : ((NodeData) other).getInEdges().values()) {
			this.inEdges.put(e.getSrc(), new Edge(e));
		}
		for (edge_data e : ((NodeData) other).getOutEdges().values()) {
			this.outEdges.put(e.getDest(), new Edge(e));
		}
	}

	@Override
	public int compareTo(node_data arg0) {
		int ret = 0;
		if (this.w - arg0.getWeight() > 0)
			ret = 1;
		else if (this.w - arg0.getWeight() < 0)
			ret = -1;
		return ret;
	}

	public NodeData(int key) {
		this.key = key;
		this.info = "";
		this.tag = 0;
		this.location = new Point3D(0,0,0);
		this.w = Integer.MAX_VALUE;
		this.inEdges = new HashMap<Integer, edge_data>();
		this.outEdges = new HashMap<Integer, edge_data>();
	}

	public NodeData(int key, String info, int tag, double w) {
		this.key = key;
		this.info = info;
		this.tag = tag;
		this.location = new Point3D(0,0,0);
		this.w = w;
		this.inEdges = new HashMap<Integer, edge_data>();
		this.outEdges = new HashMap<Integer, edge_data>();
	}

	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public geo_location getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(geo_location p) {
		this.location = p;

	}

	@Override
	public double getWeight() {
		return this.w;
	}

	@Override
	public void setWeight(double w) {
		this.w = w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;

	}

	public HashMap<Integer, edge_data> getInEdges() {
		return this.inEdges;
	}

	public void setInEdges(HashMap<Integer, edge_data> inEdges) {
		this.inEdges = inEdges;
	}

	public HashMap<Integer, edge_data> getOutEdges() {
		return this.outEdges;
	}

	public void setOutEdges(HashMap<Integer, edge_data> outEdges) {
		this.outEdges = outEdges;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.key + "";
	}

}
