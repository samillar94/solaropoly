/**
 * 
 */
package solaropoly;

/**
 * Abstract class for squares on the board, which shall have in common a name and an action
 * when a player lands on them
 * @author G17
 */
public abstract class Square {
	
	private String name;

	// setget
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	// constr
	
	public Square() {
	}
	
	/**
	 * @param name
	 */
	public Square(String name) {
		this.name = name;
	}
	
	// abstract methods
	
	/**
	 * This method should be implemented to all the extensions of square
	 * to allow the square to take actions when the plater lands on it
	 * @param player - the player who landed in this square
	 */
	public abstract void act(Player player);



}
