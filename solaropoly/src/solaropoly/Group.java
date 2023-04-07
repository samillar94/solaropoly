/**
 * 
 */
package solaropoly;

import java.util.ArrayList;

/**
 * The Group class represents a "field" in the requirements, containing two or
 * three Areas.
 * 
 * @author G17
 */
public class Group {

	/**
	 * Maximum number of squares that a group can contain from the requirements of
	 * the game. It represent the maximum size of the group.
	 */
	private static final int MAX_AREAS = 3;

	/**
	 * Minimum number of squares that a group can contain from the requirements of
	 * the game. It represent the maximum size of the group.
	 */
	private static final int MIN_AREAS = 2;

	/// variables
	private String name;
	private ArrayList<Area> areas;
	private Player owner;
	private int minorDevCost, majorDevCost;
	private int monopolyOutputBuff, minorDevOutputBuff, majorDevOutputBuff;

	/// setget

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

	/**
	 * 
	 * @return
	 */
	public int getMinorDevCost() {
		return minorDevCost;
	}

	/**
	 * 
	 * @param minorDevCost
	 */
	public void setMinorDevCost(int minorDevCost) {
		this.minorDevCost = minorDevCost;
	}

	/**
	 * 
	 * @return
	 */
	public int getMajorDevCost() {
		return majorDevCost;
	}

	/**
	 * 
	 * @param majorDevCost
	 */
	public void setMajorDevCost(int majorDevCost) {
		this.majorDevCost = majorDevCost;
	}

	/**
	 * @return the areas
	 */
	public ArrayList<Area> getAreas() {
		return areas;
	}
	
	/**
	 * @param squares an ArrayList of squares - only those of this field will be added
	 */
	public void setSquares(ArrayList<Square> squares) {
		ArrayList<Area> areas = new ArrayList<Area>();
		
		for (Square square : squares) {
			if (square instanceof Area) {
				if (((Area) square).getGroup() == this) {
					areas.add((Area) square);
				}
			}
		}
		
		this.setAreas(areas);
	}
	
	/**
	 * @param areas an ArrayList of areas
	 */
	public void setAreas(ArrayList<Area> areas) {

		// System.out.println(this.name + " matches: " + thissAreas.toString());
		if (areas.size() < MIN_AREAS || areas.size() > MAX_AREAS) {
			throw new IllegalArgumentException("Fields must have " + MIN_AREAS + "–" + MAX_AREAS + " areas.");
		} else {
			this.areas = areas;
		}
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}


	/**
	 * @return the monopolyOutputBuff
	 */
	public int getMonopolyOutputBuff() {
		return monopolyOutputBuff;
	}

	/**
	 * @param monopolyOutputBuff the monopolyOutputBuff to set
	 */
	public void setMonopolyOutputBuff(int monopolyOutputBuff) {
		this.monopolyOutputBuff = monopolyOutputBuff;
	}

	/**
	 * @return the minorDevOutputBuff
	 */
	public int getMinorDevOutputBuff() {
		return minorDevOutputBuff;
	}

	/**
	 * @param minorDevOutputBuff the minorDevOutputBuff to set
	 */
	public void setMinorDevOutputBuff(int minorDevOutputBuff) {
		this.minorDevOutputBuff = minorDevOutputBuff;
	}

	/**
	 * @return the majorDevOutputBuff
	 */
	public int getMajorDevOutputBuff() {
		return majorDevOutputBuff;
	}

	/**
	 * @param majorDevOutputBuff the majorDevOutputBuff to set
	 */
	public void setMajorDevOutputBuff(int majorDevOutputBuff) {
		this.majorDevOutputBuff = majorDevOutputBuff;
	}

	
	
	/// constr
	
	public Group() {
	}

	/**
	 * A Group is set up with a name and general parameters and areas added afterward
	 * 
	 * @param name
	 * @param minorDevCost
	 * @param majorDevCost
	 * @param monopolyOutputBuff
	 * @param minorDevOutputBuff
	 * @param majorDevOutputBuff
	 */
	public Group(String name, int minorDevCost, int majorDevCost, int monopolyOutput, int minorDevOutput, int majorDevOutput) {
		this.name = name;
		this.minorDevCost = minorDevCost;
		this.majorDevCost = majorDevCost;
		this.monopolyOutputBuff = monopolyOutput;
		this.minorDevOutputBuff = minorDevOutput;
		this.majorDevOutputBuff = majorDevOutput;
	}
	
	

	/// methods

	@Override
	public String toString() {
		return GameSystem.COLOUR_LOCATION + this.name + GameSystem.RESET;
	}

	/**
	 * Checks if an area can be developed by checking the development level of the
	 * other areas within the group. ie all ensuring areas are developed equaly
	 * 
	 * @author andrewscott
	 * @param area
	 * @param group
	 * @return
	 */
	public boolean canAreaBeDeveloped(Area area) {
		int targetDevLevel = area.getDevelopmentLevel()+1;

	
		for (Area a : this.getAreas()) {
			if (a != area) {
				
				int devLevelDiff = Math.abs(targetDevLevel - a.getDevelopmentLevel());
				if (devLevelDiff > 1) {
					return false;
				}
			}
		}
		// Check that all areas in the group have development levels within 1 of each
		// other
		for (int i = 0; i < this.getAreas().size(); i++) {
			for (int j = i + 1; j < this.getAreas().size(); j++) {
				int devLevelDiff = Math.abs(this.getAreas().get(i).getDevelopmentLevel() - this.getAreas().get(j).getDevelopmentLevel());
				if (devLevelDiff > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
}
