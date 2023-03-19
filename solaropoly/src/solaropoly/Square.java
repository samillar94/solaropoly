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

	public abstract void act(Player player);

}
