package gameClient.util;

/***
 * This class helps to convert from json to Poke
 * @author yoel hartman
 *
 */
public class PokemonJson {

	private Pokem[] Pokemons;

	public PokemonJson(Pokem[] Pokemons) {
		this.Pokemons = Pokemons;
	}
	
	public Pokem[] getPokemons ()
	{
		return this.Pokemons;
	}
}
