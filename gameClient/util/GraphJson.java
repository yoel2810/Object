package gameClient.util;

/***
 * A class that represents the game
 * @author yoel hartman
 *
 */

public class GraphJson {
	
	
	private EdgesJson[] Edges;
	private NodesJson[] Nodes;
	
	public GraphJson(EdgesJson[] edges, NodesJson[] nodes) {
		Edges = edges;
		Nodes = nodes;
	}
	
	public EdgesJson[] getEdges() {
		return Edges;
	}
	public NodesJson[] getNodes() {
		return Nodes;
	}
	
	

}
