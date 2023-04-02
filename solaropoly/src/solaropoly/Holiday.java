/**
 * 
 */
package solaropoly;

/**
 * Second square on the board with no effects - equivalent to Monopoly "Just Holiday"
 * @author G17
 */
public class Holiday extends Square {

	/**
	 * 
	 */
	public Holiday() {
	}

	/**
	 * @param name
	 */
	public Holiday(String name) {
		super(name);
	}



	@Override
	public void act(Player player) {

		System.out.println("You've landed in the "+GameSystem.COLOUR_LOCATION+this.getName()+GameSystem.RESET+"!\nRelax and bask in the sun like a solar panel 😎\n");

	}

}
