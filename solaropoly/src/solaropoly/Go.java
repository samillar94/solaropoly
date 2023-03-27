/**
 * Solaropoly game
 */
package solaropoly;

/**
 * Start square for the board. Although you get credits for passing here,
 * because you don't have to land here to get that, there are no real effects 
 * of landing here, similar to Parking.
 * @author G17
 */
public class Go extends Square {
	
	/**
	 * resource to take after one loop of the board
	 */
	public static final int PASS_GO_RESOURCE = 1000000;

	/**
	 * Default constructor.
	 */
	public Go() {}
	
	/**
	 * Constructor with arguments.
	 * @param name
	 */
	public Go(String name) {
		super.setName(name);
	}
	
	/**
	 * This method is activated when the player pass this square even if he does not land on it.
	 */
	public void actPass(Player player) {
		player.increaseBalance(PASS_GO_RESOURCE);
		System.out.println("You passed the go");
	}

	@Override
	public void act(Player player) {
		player.increaseBalance(PASS_GO_RESOURCE);
		if 
			System.out.println("Welcome back to Go :)");
	}
}
