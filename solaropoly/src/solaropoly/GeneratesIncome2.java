/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author samil
 *
 */
public interface GeneratesIncome2 {
	
	public void setCost(int cost);
	public int getCost();
	
	/**
	 * Implementations of this class shall have an instance variable of the 
	 * type HashMap<String, ArrayList<Integer>. The String key shall 
	 * name a dimension in which gameplay changes rent - e.g., 
	 * "Monopoly" to allow rent to increase on monopolised fields and "Developments"
	 * to allow rent to increase on developed areas. The ArrayList value shall
	 * contain the 0-indexed array of Integer rent values in game currency.
	 * @param rentProfile
	 */
	public void setRentProfile(HashMap<String, ArrayList<Integer>> rentProfile);
	public HashMap<String, ArrayList<Integer>> getRentProfile();
	
	public int getBaseRent();
	public int getCurrentRent();
	

}
