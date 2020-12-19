package gameClient.util;


/**
 * class of the agent
 * @author yoel hartman
 *
 */

public class Agen {
	
	private int id;
	private double value;
	private int src;
	private int dest;
	private double speed;
	private String pos;
	
	public Agen(int id, int value, int src, int dest, int speed, String pos) {
		this.id = id;
		this.value = value;
		this.src = src;
		this.dest = dest;
		this.speed = speed;
		this.pos = pos;
	}

	public int getId() {
		return id;
	}

	public double getValue() {
		return value;
	}

	public int getSrc() {
		return src;
	}

	public int getDest() {
		return dest;
	}

	public double getSpeed() {
		return speed;
	}

	public String getPos() {
		return pos;
	}
	
	

}
