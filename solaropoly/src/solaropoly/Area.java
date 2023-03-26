/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is the square that actually does stuff! Areas can be owned and developed
 * by players.
 * 
 * @author G17
 */
public class Area extends Square implements GeneratesIncome {

	private Group group;
	private Player owner;
	private int cost;
	private HashMap<String, ArrayList<Integer>> rentProfile = new HashMap<>(2);
	private int monopolyLevel = 0;
	private int developmentLevel = 0;
	
	/// constr
	
	/**
	 * Default constructor
	 */
	public Area() {}

	/**
	 * This constructor sets up an area with a name, Group, cost and rent profile.
	 * The rent profile is set by passing two integer arrays, one for how rent
	 * changes with monopoly level in its group, and one for how rent changes with
	 * its development level. Both levels are set to zero on instantiation.
	 * 
	 * @param name
	 */
	public Area(String name, Group group, int cost, int[] monopolyProfile, int[] developmentProfile) {
		super(name);
		this.group = group;
		this.setCost(cost);
		this.rentProfile.put("Monopoly",
				(ArrayList<Integer>) Arrays.stream(monopolyProfile).boxed().collect(Collectors.toList()));
		this.rentProfile.put("Development",
				(ArrayList<Integer>) Arrays.stream(developmentProfile).boxed().collect(Collectors.toList()));

	}
	
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
	 * The rent profile is set by passing two integer arrays, one for how rent
	 * changes with monopoly level in its group, and one for how rent changes with
	 * its development level. Both levels are set to zero on instantiation.
	 * @return the rentProfile
	 */
	public HashMap<String, ArrayList<Integer>> getRentProfile() {
		return rentProfile;
	}

	/**
	 * The rent profile is set by passing two integer arrays, one for how rent
	 * changes with monopoly level in its group, and one for how rent changes with
	 * its development level. Both levels are set to zero on instantiation.
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
	
	/// show details
	
	/**
	 * @return the rentProfile but in String format
	 */
	public String getRentProfileString() {
        StringBuilder sb = new StringBuilder();
        
        for (HashMap.Entry<String, ArrayList<Integer>> entry : this.rentProfile.entrySet()) {
            String name = entry.getKey();
            ArrayList<Integer> rents = entry.getValue();
            
            sb.append(name + ": ");
            for (Integer rent : rents) {
                sb.append(rent +", " );
            }
            sb.setLength(sb.length() - 2); // Remove the last ", "
            sb.append("\n");
        }
		return sb.toString();
	}
	
	@Override
	public String toString() {
		// return String.format("%s (%s, value £%,d)", this.getName(), this.group, this.cost);
		return ""
			+ "Name: " + this.getName() + "\n"
			+ "Group name: " + this.group.getName() + "\n"
			+ "Base rent: " + this.getBaseRent() + "\n"
			+ "Rent profile:\n" + this.getRentProfileString() + "\n"
			+ "Development level: " + this.developmentLevel + "\n"
			+ "Monopoly level: " + this.monopolyLevel + "\n"
			+ "Owner: " + this.owner.getName() + "\n"
			+ "Base rent: " + this.getBaseRent() + "\n"
			+ "Current rent: " + this.getCurrentRent() + "\n"
			+ "Cost area: " + this.cost;
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
	
	/**
	 *  TODO if other types of property are made, make Property an interface and move a lot of this code there
	 */
	@Override
	public void act(Player player) {
		System.out.println("You landed in: " + this.getName() + "\n"
				+ "This square information:\n\n"
				+ this.toString());
		
		if (this.owner == null && player.getBalance() >= this.cost) {
			System.out.printf("Would you like to buy this area for %,d%s?%n"
					+ "Press Enter to buy, else press any other character and press Enter to decline.%n", GameSystem.SUF, this.cost);
			String input = GameSystem.SCANNER.nextLine();
			
			if (input == null || input == "") {
				player.decreaseBalance(this.cost);
				// two-way link
				this.setOwner(player);
				player.gainOwnership(this);
				System.out.printf("Ok, you've bought %s! %n",this.getName());
				player.displayBalance();
			} else {
				// requirement for the unknown square:
				System.out.println("");
				
			}
		} else if (this.owner == null && player.getBalance()<this.cost){
			System.out.println("You can't buy this, sorry.");
		} else if (this.owner == player) {
			System.out.println("Opulence! You own this.");
		} else {
			rentPay(player, this);
		}
	}

	/**
	 * this method will transfer money automatically when players landed on
	 * competitors' land
	 * 
	 * @author Li
	 * 
	 */
	public void rentPay(Player p1, Area a1) {
		if (p1.getBalance() < a1.getCurrentRent()) {
			a1.getOwner().setBalance(+p1.getBalance());
			GameSystem.players.remove(p1);
		} else {
			p1.setBalance(-a1.getCurrentRent());
			a1.getOwner().setBalance(+a1.getCurrentRent());
		}

	}

	/**
	 * @author Li
	 * @param auctioneer Trade System to help player sell their property in
	 *                   DutchAuctionSystem
	 * @throws Exception
	 */
	public void dutchAuctionSystem(Player auctioneer) {
		if (auctioneer.getOwnedSquares().isEmpty()) {
			System.out.println("You don't have any property, auction system closed");
			return;
		}
		try {
			boolean getResult = false;
			boolean legalInput = false;
			HashSet<Square> auctionnerSet = new HashSet<>();
			auctionnerSet = auctioneer.getOwnedSquares();
			Area tradeArea = new Area();
			System.out.println(
					"Welcome to Dutch auction system,which item would you like to aucion? please enter the name");
			// show the list of owners' estates
			for (Square s : auctionnerSet) {
				Area area = new Area();
				area = (Area) s;
				System.out.println(area.toString());
			}
			String Userinput = GameSystem.SCANNER.nextLine();
			// Let player choose the land he or she wants to sell
			do {
				for (Square s : auctionnerSet) {
					if (Userinput.equalsIgnoreCase(s.getName())) {
						System.out.println("Land is picked up successfully");
						tradeArea = (Area) s;
						getResult = true;
					}
				}
				if (!getResult) {
					System.out.println("Illegal Input, please try again");
				}
			} while (!getResult);
			System.out.println("Now please type in the price you want to sell");
			int gamerInput = GameSystem.SCANNER.nextInt();
			System.out.println("Does anyone want receive it offer? The offer price is " + gamerInput);
			do {
				System.out.println("If someone want to take it, please enter your name."
						+ "\n If no one wants to take it, please type N and enter and we will go to next round");
				String userName = GameSystem.SCANNER.nextLine();
				for (Player p : GameSystem.players) {
					if (p.getName().equalsIgnoreCase(userName)) {
						if (p.getBalance() > gamerInput) {
							p.decreaseBalance(gamerInput);
							tradeArea.getOwner().increaseBalance(gamerInput);
							HashSet<Square> demoSquare = tradeArea.getOwner().getOwnedSquares();
							demoSquare.remove(tradeArea);
							tradeArea.getOwner().setOwnedSquares(demoSquare);
							HashSet<Square> Demo = p.getOwnedSquares();
							Demo.add(tradeArea);
							p.setOwnedSquares(Demo);
							System.out.println("Deal! Do you want to auction another property? Y/N");

						} else {
							System.out.println("Sorry for that you do not have enough balance to buy");
							dutchAuctionSystem(auctioneer);
						}
						legalInput = true;

					}
					if (!legalInput) {
						System.out.println("Plz enter legal input");
						System.out.println();
					}
				}
			} while (!legalInput);
			String UserAnswer = GameSystem.SCANNER.nextLine();
			do {
				if (UserAnswer.equalsIgnoreCase("N")) {
					System.out.println("System closed");
					return;
				} else if (UserAnswer.equalsIgnoreCase("Y")) {
					dutchAuctionSystem(auctioneer);
				}
			} while (!UserAnswer.equalsIgnoreCase("Y") | !UserAnswer.equalsIgnoreCase("N"));
		} catch (Exception e) {
			System.out.println("Exception happened, trade system restart");
			dutchAuctionSystem(auctioneer);
		}
	}

}
