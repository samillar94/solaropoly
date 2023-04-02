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
	public int passGoCredit;

	/**
	 * Default constructor.
	 */
	public Go() {}
	
	/**
	 * Constructor with arguments.
	 * @param name
	 */
	public Go(String name, int passGoCredit) {
		super.setName(name);
		this.passGoCredit = passGoCredit;
	}
	
	/**
	 * Do nothing actually, just print a message
	 */
	@Override
	public void act(Player player) {
		player.increaseBalance(passGoCredit);
		System.out.printf("Welcome back to %s%s%s! You've received %s%s%,d%s%s.%n"
				, GameSystem.COLOUR_LOCATION, this.getName(), GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.PRE, passGoCredit, GameSystem.SUF, GameSystem.RESET
				);
		System.out.println("Why not tweet inspirationally about the solar energy industry?\n");

	}
	
	/**
	 * Static method called when the player passes, but does not land on, Go
	 * @param player
	 */
	public void passAct(Player player) {
		player.increaseBalance(this.passGoCredit);
		System.out.printf("You passed Go and received %s%s%,d%s%s. ", GameSystem.COLOUR_RESOURCE, GameSystem.PRE, this.passGoCredit, GameSystem.SUF, GameSystem.RESET);
	}
}
