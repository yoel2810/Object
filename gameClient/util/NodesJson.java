package gameClient.util;

/***
 * this class represents the nodes
 * @author yoel hartman
 *
 */
public class NodesJson {
	
	private String pos;
	private int id;
	
	public NodesJson (String pos, int id)
	{
		this.pos = pos;
		this.id = id;
	}
	
	public String getPos()
	{
		return this.pos;
	}
	
	public int getId()
	{
		return this.id;
	}

}
