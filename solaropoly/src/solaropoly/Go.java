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
	 * Do nothing actually, just print a message
	 */
	@Override
	public void act(Player player) {
		System.out.println("Nothing to do here...");
	}
	
	/**
	 * Increase the player's resource when he lands on this square
	 */
	public void actPass(Player player) {
		player.increaseBalance(PASS_GO_RESOURCE);
		if (player.getLandedSquare().equals(this)) {
			System.out.printf("Welcome back to Go, increase %s by: %d%n", GameSystem.SUF, PASS_GO_RESOURCE);
		} else {
			System.out.printf("You passed from Go, increase %s by: %d%n", GameSystem.SUF, PASS_GO_RESOURCE);
		}
	}
}
