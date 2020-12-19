package gameClient.util;

import java.util.Arrays;
import java.util.List;
import api.DS_DWGraph;
import api.DWGraph_Algo;
import api.game_service;
import api.node_data;
import gameClient.Ex2;

/***
 * This class helps to manage the game.
 * Has many functions to help get information from the game.
 * @author yoel hartman
 *
 */

public class GameHelper {

	private game_service game;// the game
	private PokemonJson pj; //the pokemons
	private GraphJson graphJ;// the graph (as written in json)
	private AgentsJson agents;// the agents
	private GameJson gameJ;// the game
	private DS_DWGraph dsGraph;// the graph (as DS_DWGraph)
	private DWGraph_Algo algo;// the graph algorithem

	public GameHelper(game_service game, PokemonJson pj, GraphJson graphJ, AgentsJson agents, GameJson gameJ) {
		this.game = game;
		this.pj = pj;
		this.graphJ = graphJ;
		this.agents = agents;
		this.gameJ = gameJ;
		this.dsGraph = new DS_DWGraph(graphJ);
		this.algo = new DWGraph_Algo(dsGraph);
		Arrays.sort(this.pj.getPokemons());
	}

	/**
	 * this function sets the target pokemons to the agents
	 * O(|agents|)
	 */
	public void setTargets() {
		for (AgentJ a : this.agents.getAgents()) {
			if (a.getAgent().getDest() != -1)
				continue;

			Poke closestP = getClosestPokemonToAgent(a.getAgent());
			EdgesJson e = getClosestEdgeToPokemon(closestP);
			if(e==null)
				continue;
			int src = e.getSrc();
			int dest = e.getDest();
			

			// int dest = distancePointToLine(closestP).getDest();
			List<node_data> path = this.algo.shortestPath(a.getAgent().getSrc(), dest);
			if(a.getAgent().getSrc()==dest)
				path = this.algo.shortestPath(a.getAgent().getSrc(), src);
			// System.out.println(Arrays.toString(path.toArray()) + a.getAgent().getSrc());
			if (path.size() >= 2)
				game.chooseNextEdge(a.getAgent().getId(), path.get(1).getKey());
			if (path.size() == 1) {
				game.chooseNextEdge(a.getAgent().getId(), path.get(0).getKey());
			}

		}
	}

	/**
	 * Gets the closest pokemon to a specific agent
	 * O(|pokemons|)
	 * @param a - the agent
	 * @return - the pokemon
	 */
	private Poke getClosestPokemonToAgent(Agen a) {
		double minDist = Double.MAX_VALUE;
		Poke minP = null;
		for (Pokem p : this.pj.getPokemons()) {
			if (new Point3D(p.getPokemon().getPos()).distance(new Point3D(a.getPos())) < minDist) {
				minDist = new Point3D(p.getPokemon().getPos()).distance(new Point3D(a.getPos()));
				minP = p.getPokemon();
			}
		}
		return minP;
	}

	/**
	 * Gets the closest edge to a specific pokemon
	 * O(|E|)
	 * @param pokemon - the pokemon
	 * @return the closest edge
	 */
	public EdgesJson getClosestEdgeToPokemon(Poke pokemon) {
		
		
		Point3D pokemonLoc = new Point3D(pokemon.getPos());
		Point3D temp = Ex2.func2(pokemonLoc);
		pokemonLoc = temp;
		for (EdgesJson ej : this.graphJ.getEdges()) {
			Point3D src = new Point3D(getNodeById(ej.getSrc()).getPos());
			temp = Ex2.func2(src);
			src = temp;
			Point3D dst = new Point3D(getNodeById(ej.getDest()).getPos());
			temp = Ex2.func2(dst);
			dst = temp;
			double top, bot, left, right;
			top = Math.min(src.y(), dst.y());
			bot = Math.max(src.y(), dst.y());
			left = Math.min(src.x(), dst.x());
			right = Math.max(src.x(), dst.x());
			if (pokemonLoc.x() < right && pokemonLoc.x() > left && pokemonLoc.y() > top && pokemonLoc.y() < bot) {
				if (pokemon.getType() == 1) {
					if (ej.getSrc() < ej.getDest()) {
						return ej;
					}
				} else {
					if (ej.getSrc() > ej.getDest()) {
						return ej;
					}
				}
			}
		}
		return null;
	}

/*	public EdgesJson distancePointToLine(Poke pokemon) {

		Point3D pokemonLoc = new Point3D(pokemon.getPos());
		double minDist = Double.MAX_VALUE;
		EdgesJson minEdge = null;

		int n = getClosestNodeToPokemon(pokemon, pokemon.getType());

		for (EdgesJson ej : this.graphJ.getEdges()) {
			LineGraph2D lg = new LineGraph2D(new Point3D(getNodeById(ej.getDest()).getPos()),
					new Point3D(getNodeById(ej.getSrc()).getPos()));

			if (lg.distance(pokemonLoc) < minDist) {
				if (pokemon.getType() == 1) {
					if (ej.getSrc() < ej.getDest()) {

						minDist = lg.distance(pokemonLoc);
						minEdge = ej;
					}
				} else {
					if (ej.getSrc() > ej.getDest()) {
						minDist = lg.distance(pokemonLoc);
						minEdge = ej;
					}
				}
			}
		}
		return minEdge;
	}*/

	/**
	 * Add the agents to the game
	 * O(|agents|)
	 */
	public void AddAgents() {

		int n = Math.min(this.pj.getPokemons().length, this.gameJ.getGameServer().getAgents());
		for (int i = 0; i < n; i++) {
			Poke p = this.pj.getPokemons()[i].getPokemon();
			// int nodeid = getClosestEdgeToPokemon(p).getSrc();
			int nodeid = getClosestNodeToPokemon(p);
			game.addAgent(nodeid);

		}
	}

	/**
	 * Get agent(Agen object) by the node id
	 * O(|agents|)
	 * @param id - the node id
	 * @return the Agen object
	 */
	public Agen getAgentById(int id) {
		for (AgentJ a : this.agents.getAgents()) {
			if (a.getAgent().getId() == id)
				return a.getAgent();
		}
		return null;
	}
	
	
/**
 * Gets the closest pokemon to a specific node
 * @param node - the node location
 * @return the pokemon id
 */
	public String getClosestPokemonToNode(Point3D node) {
		double min = Double.MIN_VALUE;
		String minid = "0,0,0";
		for (Pokem p : this.pj.getPokemons()) {
			if (node.distance(new Point3D(p.getPokemon().getPos())) < min) {
				min = node.distance(new Point3D(p.getPokemon().getPos()));
				minid = p.getPokemon().getPos();
			}
		}
		return minid;
	}

	/**
	 * Gets the closest node to a specific pokemon
	 * O(|V|)
	 * @param pokemon - the pokemon
	 * @return the node id
	 */
	public int getClosestNodeToPokemon(Poke pokemon) {
		double min = Double.MAX_VALUE;
		int minNode = -1;
		for (NodesJson nj : this.graphJ.getNodes()) {
			if (new Point3D(pokemon.getPos()).distance(new Point3D(nj.getPos())) < min) {
				min = new Point3D(pokemon.getPos()).distance(new Point3D(nj.getPos()));
				minNode = nj.getId();
			}
		}
		return minNode;
	}

	/**
	 * Gets a node (NodeJson object) by his id
	 * O(|V|)
	 * @param id - node id
	 * @return the node
	 */
	public NodesJson getNodeById(int id) {
		for (NodesJson nj : this.graphJ.getNodes()) {
			if (nj.getId() == id)
				return nj;
		}
		return null;
	}

	public game_service getGame() {
		return game;
	}

	public void setGame(game_service game) {
		this.game = game;
	}

	public PokemonJson getPj() {
		return pj;
	}

	public void setPj(PokemonJson pj) {
		this.pj = pj;
		Arrays.sort(this.pj.getPokemons());
	}

	public GraphJson getGraphJ() {
		return graphJ;
	}

	public void setGraphJ(GraphJson graphJ) {
		this.graphJ = graphJ;
	}

	public AgentsJson getAgents() {
		return agents;
	}

	public void setAgents(AgentsJson agents) {
		this.agents = agents;
	}

	public GameJson getGameJ() {
		return gameJ;
	}

	public void setGameJ(GameJson gameJ) {
		this.gameJ = gameJ;
	}

}
