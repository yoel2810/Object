package gameClient.util;

/***
 * class that helps to convert from json to Agen
 * @author yoel hartman
 *
 */

public class AgentsJson {
	private AgentJ[] Agents;
	
	public AgentsJson(AgentJ[] Agents)
	{
		this.Agents = Agents;
	}
	
	public AgentJ[] getAgents()
	{
		return this.Agents;
	}

}
