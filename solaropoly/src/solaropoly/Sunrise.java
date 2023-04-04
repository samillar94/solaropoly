/**
 * Solaropoly game
 */
package solaropoly;

/**
 * Start square for the board. Although you get credits for passing here,
 * because you don't have to land here to get that, there are no real effects 
 * of landing here, similar to Holiday.
 * @author G17
 */
public class Sunrise extends Square {
	
	/**
	 * resource to take after one loop of the board
	 */
	public int sunriseCredit;

	/**
	 * Default constructor.
	 */
	public Sunrise() {}
	
	/**
	 * Constructor with arguments.
	 * @param name
	 * @param sunriseCredit when square passed
	 */
	public Sunrise(String name, int sunriseCredit) {
		super.setName(name);
		this.sunriseCredit = sunriseCredit;
	}
	
	/**
	 * Do nothing actually, just print a message
	 */
	@Override
	public void act(Player player) {
		player.increaseBalance(sunriseCredit);
		System.out.printf("Welcome back to %s%s%s! You've received %s%s%,d%s%s.%n"
				, GameSystem.COLOUR_LOCATION, this.getName(), GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, sunriseCredit, GameSystem.RES_SUF, GameSystem.RESET
				);
		System.out.println("Why not tweet inspirationally about the solar energy industry?\n");

	}
	
	/**
	 * Static method called when the player passes, but does not land on, Sunrise
	 * @param player
	 */
	public void passAct(Player player) {
		player.increaseBalance(this.sunriseCredit);
		System.out.printf("You passed Sunrise and received %s%s%,d%s%s. ", GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.sunriseCredit, GameSystem.RES_SUF, GameSystem.RESET);
	}
}
