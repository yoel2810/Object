package gameClient.util;

/***
 * The pokemon class
 * @author yoel hartman
 *
 */
public class Poke implements Comparable<Poke> {

	private double value;
	private int type;
	private String pos;

	public Poke(double value, int type, String pos) {
		this.value = value;
		this.type = type;
		this.pos = pos;
	}

	public double getValue() {
		return value;
	}

	public int getType() {
		return type;
	}

	public String getPos() {
		return pos;
	}

	@Override
	public int compareTo(Poke arg0) {
		if (this.getValue() < arg0.getValue())
			return 1;
		else if (this.getValue() > arg0.getValue())
			return -1;
		else
			return 0;
	}

}
