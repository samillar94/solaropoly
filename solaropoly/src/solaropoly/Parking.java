/**
 * 
 */
package solaropoly;

/**
 * Second square on the board with no effects - equivalent to Monopoly "Just Parking"
 * @author G17
 */
public class Parking extends Square {

	/**
	 * 
	 */
	public Parking() {
	}

	/**
	 * @param name
	 */
	public Parking(String name) {
		super(name);
	}



	@Override
	public void act(Player player) {

		System.out.println("Just Parking :)");

	}

}
