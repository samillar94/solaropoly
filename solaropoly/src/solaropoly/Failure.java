/**
 * Solaropoly game
 */
package solaropoly;

/**
 * This class stop the player for a given number of turns
 * 
 * @author Roberto Lo Duca 40386172
 *
 */
public class Failure extends Square {
	private int turns;

	/**
	 * Default constructor
	 */
	public Failure() {}

	/**
	 * Constructor with arguments
	 * @param name - the name of the square
	 * @param turns - the turns to stop the player
	 */
	public Failure(String name, int turns) throws IllegalArgumentException {
		super(name);
		this.setTurns(turns);
	}
	
	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * @param turns the turns to set
	 */
	public void setTurns(int turns) throws IllegalArgumentException {
		if (turns < 0) {
			this.turns = turns;
		} else {
			throw new IllegalArgumentException("The number of turns should be negative and lower than 0");
		}
		
	}
	
	/**
	 * This method stop the player for a number of turns stored in the object
	 */
	@Override
	public void act(Player player) {
		System.out.println("You got a Failure in your power plant. You will loose some turns in order to repair it...");
		player.sumTurns(this.turns);
		System.out.println("Turns subtracted: " + this.turns);
	}
}
