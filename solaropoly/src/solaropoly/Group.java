/**
 * 
 */
package solaropoly;

import java.util.ArrayList;

/**
 * The Group class represents a "field" in the requirements, containing two or three Areas.
 * @author G17
 */
public class Group {
	
	/// constants
	public static final int MIN_AREAS = 2;
	public static final int MAX_AREAS = 3;
	
	/// variables
	private String name;
	private ArrayList<Area> areas;
	private Player owner;
	
	
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
	 * @return the areas
	 */
	public ArrayList<Area> getAreas() {
		return areas;
	}
	
	/**
	 * @param areas an ArrayList of areas - only those of this field 
	 * will be added
	 */
	public void setAreas(ArrayList<Area> areas) {
		
		ArrayList<Area> thissAreas = new ArrayList<Area>();
		
		for (Area area : areas) {
			if (area.getGroup().getName() == this.getName()) {
				thissAreas.add(area);
			}
		}
		
		//System.out.println(this.name + " matches: " + thissAreas.toString());
		if (thissAreas.size()<MIN_AREAS || thissAreas.size()>MAX_AREAS) {
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
	
	public Group() {}
	
	/**
	 * A Group is set up with a name and areas added afterward
	 * @param name
	 */
	public Group(String name) {
		this.setName(name);
	}
	
	
	/// methods	
	
	@Override
	public String toString() {
		return this.name;
	}
	

}
