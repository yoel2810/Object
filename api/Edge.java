package api;

import gameClient.util.EdgesJson;

public class Edge implements edge_data {
	
	private int src;
	private int dst;
	private double weight;
	private String info;
	private int tag;
	
	public Edge(EdgesJson ej)
	{
		this.src = ej.getSrc();
		this.dst = ej.getDest();
		this.weight = ej.getW();
		this.info = "";
		this.tag = Integer.MAX_VALUE;
	}
	
	public Edge(edge_data other)
	{
		this.src = other.getSrc();
		this.dst = other.getDest();
		this.weight = other.getWeight();
		this.info = new String(other.getInfo());
		this.tag = other.getTag();
	}
	
	public Edge (int src, int dst, double w)
	{
		this.src = src;
		this.dst = dst;
		this.weight = w;
		this.info = "";
		this.tag = Integer.MAX_VALUE;
	}
	
	public Edge ()
	{
		this.src = -1;
		this.dst = -1;
		this.weight = 0;
		this.info = null;
		this.tag = 0;
	}

	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dst;
	}

	@Override
	public double getWeight() {
		return this.weight;
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

}
