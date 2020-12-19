package gameClient;

import com.google.gson.Gson;
import Server.Game_Server_Ex2;
import api.game_service;
import gameClient.util.AgentsJson;
import gameClient.util.GameHelper;
import gameClient.util.GameJson;
import gameClient.util.GraphJson;
import gameClient.util.GraphPainter;
import gameClient.util.Input;
import gameClient.util.Point3D;
import gameClient.util.PokemonJson;
import gameClient.util.Score;

public class Ex2 {
	private static Long id = null;
	private static Integer level = null;

	public static void main(String[] args) {
		// for (int i = 0; i < 24; i++) {
		if (args.length == 2) {// getting the input
			id = Long.parseLong(args[0], 10);
			level = Integer.parseInt(args[1]);
		} else {
			Input in = new Input();
			while (id == null || level == null) {
				id = in.getId();
				level = in.getLevel();
			}
			in.setVisible(false);
			in = null;
		}
		 //System.out.println(id + " " + level);

		Gson gson = new Gson();
		int level_number = level;
		game_service game = Game_Server_Ex2.getServer(level_number);
		//System.out.println(game);
		Score sc = new Score();

		 game.login(id);

		GraphJson graphJ = gson.fromJson(game.getGraph(), GraphJson.class);
		Point3D[] nodes = getNodes(graphJ);

		PokemonJson pj = gson.fromJson(game.getPokemons(), PokemonJson.class);
		Point3D[] poke = getPokemons(pj);
		GameJson gameJ = gson.fromJson(game.toString(), GameJson.class);
		GameHelper gh = new GameHelper(game, pj, graphJ, null, gameJ);
		AgentsJson aj;
		// game.addAgent(gh.getClosestNodeToPokemon(pj.getPokemons()[0].getPokemon(),
		// pj.getPokemons()[0].getPokemon().getType()));
		gh.AddAgents();
		aj = gson.fromJson(game.getAgents(), AgentsJson.class);

		Point3D[] agents = getAgents(aj);
		// game.addAgent(gh.getClosestNodeToPokemon(pj.getPokemons()[0].getPokemon()));
		Point3D[] locEdges = getEdges(graphJ, gh);
		GraphPainter gp = new GraphPainter(locEdges, nodes, poke, agents);

		gp.setVisible(true);
		sc.setVisible(true);
		// gp.createGraph();
		
		game.startGame();
		while (game.isRunning()) {
			graphJ = gson.fromJson(game.getGraph(), GraphJson.class);
			nodes = getNodes(graphJ);

			pj = gson.fromJson(game.getPokemons(), PokemonJson.class);
			poke = getPokemons(pj);

			aj = gson.fromJson(game.getAgents(), AgentsJson.class);
			agents = getAgents(aj);
			gameJ = gson.fromJson(game.toString(), GameJson.class);
			// gh = new GameHelper(game, pj, graphJ, aj, gameJ);
			gh.setGame(game);
			gh.setPj(pj);
			gh.setAgents(aj);
			gh.setGameJ(gameJ);

			gh.setTargets();
			game.move();
			//System.out.println("score: " + gameJ.getGameServer().getGrade());

			// EdgesJson bc = gh.distancePointToLine(pj.getPokemons()[0].getPokemon());
			gp.updateGraph(agents, poke);
			sc.updateScores(game.timeToEnd(), gameJ.getGameServer().getGrade());
			// System.out.println(aj.getAgents()[0].getAgent().getPos());
		}
		gp.setVisible(false);
		sc.setVisible(false);
		gp = null;
		sc = null;
		System.out.println(gameJ.getGameServer().getGrade());
		System.out.println("finished");
		// }

	}

	/**
	 * Converting the agents into a Point3D array (their location) O(|agents|)
	 * 
	 * @param aj - the agents
	 * @return Point3D[]
	 */
	public static Point3D[] getAgents(AgentsJson aj) {
		Point3D[] agents = new Point3D[aj.getAgents().length];
		for (int i = 0; i < agents.length; i++) {
			agents[i] = new Point3D(aj.getAgents()[i].getAgent().getPos());
			Point3D temp = new Point3D(func(agents[i].x(), 'x'), func(agents[i].y(), 'y'), agents[i].z());
			agents[i] = temp;
		}
		return agents;
	}

	/**
	 * Converting the edges of the graph to a Point3D[] array (their location)
	 * 
	 * @param graphJ - the graph
	 * @param gh     - GameHelper object
	 * @return Point3D[]
	 */
	public static Point3D[] getEdges(GraphJson graphJ, GameHelper gh) {
		Point3D[] locEdges = new Point3D[graphJ.getEdges().length * 2];
		for (int i = 0, j = 0; i < graphJ.getEdges().length; i++) {
			locEdges[j] = new Point3D(gh.getNodeById(graphJ.getEdges()[i].getSrc()).getPos());
			Point3D temp = new Point3D(func(locEdges[j].x(), 'x'), func(locEdges[j].y(), 'y'), locEdges[j].z());
			locEdges[j] = temp;
			locEdges[j + 1] = new Point3D(gh.getNodeById(graphJ.getEdges()[i].getDest()).getPos());
			temp = new Point3D(func(locEdges[j + 1].x(), 'x'), func(locEdges[j + 1].y(), 'y'), locEdges[j + 1].z());
			locEdges[j + 1] = temp;
			j = j + 2;
		}
		return locEdges;
	}

	/**
	 * Converting nodes to Point3D[] array(their location)
	 * 
	 * @param graphJ - the graph
	 * @return Point3D[] array
	 */
	public static Point3D[] getNodes(GraphJson graphJ) {
		Point3D[] nodes = new Point3D[graphJ.getNodes().length];

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Point3D(graphJ.getNodes()[i].getPos());
			Point3D temp = new Point3D(func(nodes[i].x(), 'x'), func(nodes[i].y(), 'y'), nodes[i].z());
			// System.out.println("(" + func(nodes[i].x(),'x') + ", " +
			// func(nodes[i].y(),'y') + ", " + nodes[i].z() + ")");
			nodes[i] = temp;
		}
		return nodes;
	}

	/**
	 * Converting pokemons to Point3D[] array(their location)
	 * 
	 * @param pj - the pokemons
	 * @returnPoint3D[] array
	 */
	public static Point3D[] getPokemons(PokemonJson pj) {

		Point3D[] poke = new Point3D[pj.getPokemons().length];

		for (int i = 0; i < poke.length; i++) {
			poke[i] = new Point3D(pj.getPokemons()[i].getPokemon().getPos());
			Point3D temp = new Point3D(func(poke[i].x(), 'x'), func(poke[i].y(), 'y'), poke[i].z());
			poke[i] = temp;
		}
		return poke;
	}

	/**
	 * The function helps resize the graph and adjust it to the screen
	 * 
	 * @param p - gets a location (Point3D object)
	 * @return the point resized
	 */
	public static Point3D func2(Point3D p) {
		return new Point3D(func(p.x(), 'x'), func(p.y(), 'y'), 0);
	}

	/**
	 * The function helps resize the graph and adjust it to the screen
	 * 
	 * @param num - what number to resize
	 * @param c   - if it's x or y
	 * @return the resized number
	 */
	public static double func(double num, char c) {
		// System.out.println((int)((num - Math.floor(num))*10000)-1000);
		if (c == 'x')
			return ((int) ((num - Math.floor(num)) * 50000) - 9000);
		if (c == 'y')
			return ((int) ((num - Math.floor(num)) * 50000) - 4750);
		else
			return 0;
	}

}
