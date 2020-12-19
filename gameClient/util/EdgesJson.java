package gameClient.util;

/***
 * class the represents the edges
 * @author yoel hartman
 *
 */

public class EdgesJson {
	
	private int src;
	private double w;
	private int dest;
	
	public EdgesJson(int src, double w, int dest)
	{
		this.src = src;
		this.w = w;
		this.dest = dest;
	}
	
	public EdgesJson(EdgesJson o)
	{
		this.src = o.getSrc();
		this.w = o.getW();
		this.dest = o.getDest();
	}

	public int getSrc() {
		return src;
	}

	public double getW() {
		return w;
	}

	public int getDest() {
		return dest;
	}

}
