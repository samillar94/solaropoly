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
	private int minorDevelopmentCost, majorDevelopmentCost;

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
	public int getMinorDevelopmentCost() {
		return minorDevelopmentCost;
	}

	/**
	 * 
	 * @param minorDevelopmentCost
	 */
	public void setMinorDevelopmentCost(int minorDevelopmentCost) {
		this.minorDevelopmentCost = minorDevelopmentCost;
	}

	/**
	 * 
	 * @return
	 */
	public int getMajorDevelopmentCost() {
		return majorDevelopmentCost;
	}

	/**
	 * 
	 * @param majorDevelopmentCost
	 */
	public void setMajorDevelopmentCost(int majorDevelopmentCost) {
		this.majorDevelopmentCost = majorDevelopmentCost;
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

	/// constr

	public Group() {
	}

	/**
	 * A Group is set up with a name and areas added afterward
	 * 
	 * @param name
	 */
	public Group(String name, int minorDevelopmentCost, int majorDevelopmentCost) {
		this.setName(name);
		this.setMinorDevelopmentCost(minorDevelopmentCost);
		this.setMajorDevelopmentCost(majorDevelopmentCost);
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
	public boolean canAreaBeDeveloped(Area area, Group group) {
		int targetDevLevel = area.getDevelopmentLevel()+1;

	
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
