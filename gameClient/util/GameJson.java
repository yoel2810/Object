package gameClient.util;

/***
 * A class that helps to convert json to Game
 * @author yoel hartman
 *
 */

public class GameJson {
	
	private Game GameServer;
	
	public GameJson (Game GameServer)
	{
		this.GameServer = GameServer;
	}
	
	public Game getGameServer()
	{
		return this.GameServer;
	}

}
