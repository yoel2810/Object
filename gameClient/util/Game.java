package gameClient.util;

/***
 * A class that represents the game
 * @author yoel hartman
 *
 */
public class Game {
	
	private int pokemons;
	private boolean is_logged_in;
	private int moves;
	private int grade;
	private int game_level;
	private int max_user_level;
	private int id;
	private String graph;
	private int agents;
	
	public Game(int pokemons, boolean is_logged_in, int moves, int grade, int game_level, int max_user_level, int id,
			String graph, int agents) {
		
		this.pokemons = pokemons;
		this.is_logged_in = is_logged_in;
		this.moves = moves;
		this.grade = grade;
		this.game_level = game_level;
		this.max_user_level = max_user_level;
		this.id = id;
		this.graph = graph;
		this.agents = agents;
	}

	public int getPokemons() {
		return pokemons;
	}

	public boolean isIs_logged_in() {
		return is_logged_in;
	}

	public int getMoves() {
		return moves;
	}

	public int getGrade() {
		return grade;
	}

	public int getGame_level() {
		return game_level;
	}

	public int getMax_user_level() {
		return max_user_level;
	}

	public int getId() {
		return id;
	}

	public String getGraph() {
		return graph;
	}

	public int getAgents() {
		return agents;
	}
	
	

}
