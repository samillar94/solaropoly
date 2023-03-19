/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author samil
 * 
 */
public class Area extends Square implements GeneratesIncome {
	
	private Group group;
	private Player owner;
	private int cost;
	private HashMap<String, ArrayList<Integer>> rentProfile = new HashMap<>(2);
	private int monopolyLevel = 0;
	private int developmentLevel = 0;

	
	/// setget
	
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
	@Override
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	@Override
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * @return the rentProfile
	 */
	public HashMap<String, ArrayList<Integer>> getRentProfile() {
		return rentProfile;
	}

	/**
	 * @param rentProfile the rentProfile to set
	 */
	public void setRentProfile(HashMap<String, ArrayList<Integer>> rentProfile) {
		// TODO validate HashMap and ArrayList not empty, Integer 0+
		this.rentProfile = rentProfile;
	}

	
	

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

	

}
