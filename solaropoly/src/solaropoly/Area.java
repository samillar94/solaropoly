/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * This is the square that actually does stuff! Areas can be owned and developed by players.
 * @author G17
 */
public class Area extends Square {
	
	private Group group;
	private Player owner;
	private int cost;
	private ArrayList<Integer> rentProfile=new ArrayList<>();
	private int monopolyLevel = 0;
	private int developmentLevel = 0;
private String name;
	
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
 * @param rentProfile the rentProfile to set
 */
public void setRentProfile(ArrayList<Integer> rentProfile) {
	this.rentProfile = rentProfile;
}

	/**
	 * @return the field
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the field to set
	 */
	public void setGroup(Group group) {
		this.group = group;
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
	 * @return the cost
	 */
	

	/**
	 * @return the monopolyLevel
	 */
	public int getMonopolyLevel() {
		return monopolyLevel;
	}

	/**
	 * @param monopolyLevel the monopolyLevel to set
	 */
	public void setMonopolyLevel(int monopolyLevel) {
		// TODO validate 0+
		this.monopolyLevel = monopolyLevel;
	}

	/**
	 * @return the developmentLevel
	 */
	public int getDevelopmentLevel() {
		return developmentLevel;
	}

	/**
	 * @param developmentLevel the developmentLevel to set
	 */
	public void setDevelopmentLevel(int developmentLevel) {
		// TODO validate 0+
		this.developmentLevel = developmentLevel;
	}

	
	/// constr

	/**
	 * 
	 */
	public Area() {
	}

	/**
	 * This constructor sets up an area with a name, Group, cost and 
	 * rent profile.
	 * The rent profile is set by passing two integer arrays, one for how rent
	 * changes with monopoly level in its group, and one for how rent changes 
	 * with its development level. Both levels are set to zero on instantiation.
	 * @param name
	 */
	public Area(String name, Group group, int cost, int[] monopolyProfile, int[] developmentProfile) {
		super(name);
		this.group = group;
		this.setCost(cost);
		this.rentProfile.put("Monopoly", (ArrayList<Integer>) Arrays.stream( monopolyProfile ).boxed().collect( Collectors.toList() ));
		this.rentProfile.put("Development", (ArrayList<Integer>) Arrays.stream( developmentProfile ).boxed().collect( Collectors.toList() ));
		
	}
	
	
	/// methods

	/**
	 * @return the baseRent
	 */
	@Override
	public int getBaseRent() {
		return rentProfile.get("Development").get(0);
	}

	/**
	 * @return the baseRent
	 */
	@Override
	public int getCurrentRent() {
		if (rentProfile.get("Development").get(this.developmentLevel) == 0) {
			return rentProfile.get("Monopoly").get(this.monopolyLevel);
		} else {
			return rentProfile.get("Development").get(this.developmentLevel);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s, value £%,d)", this.getName(), this.group, this.cost);
	}
	
	@Override
	public void act(Player player) {
		
		/// TODO offer the property for sale to the player and allow them to pass on it if they don't want it
		

	}
	/**
	 * this method will transferred money automatically when players landed on competitors' land
	 * @author Li
	 * 
	 */
public void rentPay(Player p2, Area a2) {
	// TODO When players landed on others land
	
	p2.setBalance(p2.getBalance()-rentProfile.get(a2.developmentLevel+a2.monopolyLevel));
	a2.getOwner().setBalance(p2.getBalance()-rentProfile.get(a2.developmentLevel+a2.monopolyLevel));
	//change test
	
}
	

}
