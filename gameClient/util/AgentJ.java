package gameClient.util;

/***
 * class that helps to convert from json to Agen
 * @author yoel hartman
 *
 */

public class AgentJ {

	
	private Agen Agent;
	
	public AgentJ(Agen Agent)
	{
		this.Agent = Agent;
	}
	
	public Agen getAgent()
	{
		return this.Agent;
	}
}
