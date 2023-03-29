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
	 * Increase the player's resource when he lands on this square
	 */
	@Override
	public void act(Player player) {
		player.increaseBalance(PASS_GO_RESOURCE);
		System.out.printf("Welcome back to Go! You've received %s%,d%s.%n", GameSystem.PRE, PASS_GO_RESOURCE, GameSystem.SUF);

	}
	
	/**
	 * Static method called when the player passes, but does not land on, Go
	 * @param player
	 */
	public static void passAct(Player player) {
		player.increaseBalance(PASS_GO_RESOURCE);
		System.out.printf("You passed Go and received %s%,d%s. ", GameSystem.PRE, PASS_GO_RESOURCE, GameSystem.SUF);
	}
}
