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
	private int monopolyOutput, minorDevOutput, majorDevOutput;

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
	 * @param areas an ArrayList of areas - only those of this field will be added
	 */
	public void setAreas(ArrayList<Square> squares) {
		
		ArrayList<Area> thissAreas = new ArrayList<Area>();
		
		for (Square square : squares) {
			if (square instanceof Area) {
				if (((Area) square).getGroup() == this) {
					thissAreas.add((Area) square);
				}
			}
		}

		// System.out.println(this.name + " matches: " + thissAreas.toString());
		if (thissAreas.size() < MIN_AREAS || thissAreas.size() > MAX_AREAS) {
			throw new IllegalArgumentException("Fields must have " + MIN_AREAS + "–" + MAX_AREAS + " areas.");
		} else {
			this.areas = thissAreas;
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
	 * @return the monopolyOutput
	 */
	public int getMonopolyOutput() {
		return monopolyOutput;
	}

	/**
	 * @param monopolyOutput the monopolyOutput to set
	 */
	public void setMonopolyOutput(int monopolyOutput) {
		this.monopolyOutput = monopolyOutput;
	}

	/**
	 * @return the minorDevOutput
	 */
	public int getMinorDevOutput() {
		return minorDevOutput;
	}

	/**
	 * @param minorDevOutput the minorDevOutput to set
	 */
	public void setMinorDevOutput(int minorDevOutput) {
		this.minorDevOutput = minorDevOutput;
	}

	/**
	 * @return the majorDevOutput
	 */
	public int getMajorDevOutput() {
		return majorDevOutput;
	}

	/**
	 * @param majorDevOutput the majorDevOutput to set
	 */
	public void setMajorDevOutput(int majorDevOutput) {
		this.majorDevOutput = majorDevOutput;
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
	 * @param monopolyOutput
	 * @param minorDevOutput
	 * @param majorDevOutput
	 */
	public Group(String name, int minorDevCost, int majorDevCost, int monopolyOutput, int minorDevOutput,
			int majorDevOutput) {
		this.name = name;
		this.minorDevCost = minorDevCost;
		this.majorDevCost = majorDevCost;
		this.monopolyOutput = monopolyOutput;
		this.minorDevOutput = minorDevOutput;
		this.majorDevOutput = majorDevOutput;
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
	 * @param area
	 * @param group
	 * @return
	 */
	public boolean canAreaBeDeveloped(Area area, Group group) {
		int targetDevLevel = area.getDevelopmentLevel();

	
		for (Area a : group.getAreas()) {
			if (a != area) {
				int devLevelDiff = Math.abs(targetDevLevel - a.getDevelopmentLevel());
				if (devLevelDiff > 1) {
					return false;
				}
			}
		}
		// Check that all areas in the group have development levels within 1 of each
		// other
		for (int i = 0; i < group.getAreas().size(); i++) {
			for (int j = i + 1; j < group.getAreas().size(); j++) {
				int devLevelDiff = Math.abs(group.getAreas().get(i).getDevelopmentLevel() - group.getAreas().get(j).getDevelopmentLevel());
				if (devLevelDiff > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
}
