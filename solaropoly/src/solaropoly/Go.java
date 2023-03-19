/**
 * 
 */
package solaropoly;

/**
 * Start square for the board. Although you get credits for passing here,
 * because you don't have to land here to get that, there are no real effects 
 * of landing here, similar to Parking.
 * @author samil
 *
 */
public class Go extends Square {

	/**
	 * 
	 */
	public Go() {
		
	}
	
	public Go(String name) {
		super.setName(name);
	}

	@Override
	public void act(Player player) {

		System.out.println("Welcome back to Go :)");

	}

}
