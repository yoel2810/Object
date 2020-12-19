package gameClient.util;

/***
 * This class helps to convert from json to Poke
 * @author yoel hartman
 *
 */

public class Pokem implements Comparable<Pokem> {
	private Poke Pokemon;
	
	public Pokem (Poke Pokemon)
	{
		this.Pokemon = Pokemon;
	}
	
	public Poke getPokemon()
	{
		return this.Pokemon;
	}

	@Override
	public int compareTo(Pokem arg0) {
		return this.Pokemon.compareTo(arg0.getPokemon());
	}
}