/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
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
	private int minorDevelopmentCost, majorDevelopmentCost;
	private static final int MAX_LEVEL = 3;
	private static final int MIN_LEVEL = 0;
	
	
	

	/// constr

	/**
	 * Default constructor
	 */
	public Area() {
	}

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
	 * @return the minorDevelopmentCost
	 */
	public int getMinorDevelopmentCost() {
		return minorDevelopmentCost;
	}

	/**
	 * @param minorDevelopmentCost the minorDevelopmentCost to set
	 */
	public void setMinorDevelopmentCost(int minorDevelopmentCost) {
		this.minorDevelopmentCost = minorDevelopmentCost;
	}

	/**
	 * @return the majorDevelopmentCost
	 */
	public int getMajorDevelopmentCost() {
		return majorDevelopmentCost;
	}

	/**
	 * @param majorDevelopmentCost the majorDevelopmentCost to set
	 */
	public void setMajorDevelopmentCost(int majorDevelopmentCost) {
		this.majorDevelopmentCost = majorDevelopmentCost;
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
	 * 
	 * @return the rentProfile
	 */
	public HashMap<String, ArrayList<Integer>> getRentProfile() {
		return rentProfile;
	}

	/**
	 * The rent profile is set by passing two integer arrays, one for how rent
	 * changes with monopoly level in its group, and one for how rent changes with
	 * its development level. Both levels are set to zero on instantiation.
	 * 
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
		if(monopolyLevel<MIN_LEVEL ) {
			throw new IllegalArgumentException("Monopoly level must be greater or equal than " + MIN_LEVEL);
		} else {
		this.monopolyLevel = monopolyLevel;
		}
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
	public int setDevelopmentLevel() {
		int developmentLevel =0;
		developmentLevel++;
		if(developmentLevel<MIN_LEVEL || developmentLevel > MAX_LEVEL) {
			throw new IllegalArgumentException("Development level level must be greater or equal than " + MIN_LEVEL +  " or less than or equal to "+ MAX_LEVEL);
					
		} 
		return developmentLevel;
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
				sb.append(rent + ", ");
			}
			sb.setLength(sb.length() - 2); // Remove the last ", "
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * It prints all the informations of the Area
	 * @return
	 */
	public String detailsArea() {
		// return String.format("%s (%s, value £%,d)", this.getName(), this.group, this.cost);
		return ""
			+ "Name: " + this.getName() + "\n"
			+ "Group name: " + this.group.getName() + "\n"
			+ "Base rent: " + this.getBaseRent() + "\n"
			+ "Rent profile:\n" + this.getRentProfileString() + "\n"
			+ "Development level: " + this.developmentLevel + "\n"
			+ "Monopoly level: " + this.monopolyLevel + "\n"
			+ "Owner: " + ((this.owner != null) ? this.owner.getName() : null) + "\n"
			+ "Base rent: " + this.getBaseRent() + "\n"
			+ "Current rent: " + this.getCurrentRent() + "\n"
			+ "Cost area: " + this.cost + "\n";
	}
	
	@Override
	public String toString() {
		return String.format("%n%s (%s, value £%,d),%n", this.getName(), this.group, this.cost);
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
	 * This method allow actions when the plater lands on an area
	 * @param player - the player who landed in this area
	 * TODO if other types of property are made, make Property an interface and move a lot of this code there
	 */
	@Override
	public void act(Player player) {
		System.out
				.println("You landed in: " + this.getName() + "\n" + "This square information:\n\n" + this.detailsArea());

		if (this.owner == null) {
			purchaseArea(player);
		} else if (this.owner == player) {
			System.out.println("Opulence! You own this.");
		} else {
			rentPay(player, this);
		}
	}

	/**
	 * This method is used to fulfill the requirement for an unknown square, which
	 * specifies that the square should be bought or sold to a different player at
	 * the same price. In this case, the requirement is to sell the square, but
	 * since the price is fixed for all players, this method first checks the active
	 * player's decision. If the active player wants to sell, or can only sell
	 * because they don't have enough money, the method will let them choose whether
	 * to sell or do nothing. If the sell option is chosen, the method will check
	 * all other players' balances and ask only those who have enough money if they
	 * want to accept the offer. If more than one player accepts the offer, the
	 * active player can choose between them.
	 * 
	 * @param player - The active player that needs to take actions.
	 */
	private void purchaseArea(Player player) {
		try {
			System.out.printf(
						"%sWould you like to buy this square for %s%s%,d%s%s?%n" 
						+ "Type %sBuy%s and Enter to buy%n"
						+ "Type %sSell%s and Enter to sell%n" 
						+ "Type %sNone%s to end the turn%s%n",
						GameSystem.RESET,
						GameSystem.COLOUR_RESOURCE, GameSystem.PRE, this.cost, GameSystem.SUF, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_INPUT);
			String input = "";

			while (true) {
				input = GameSystem.SCANNER.nextLine();

				if (input.equalsIgnoreCase("Buy") || input.equalsIgnoreCase("Sell") || input.equalsIgnoreCase("None")) {
					break;
				} else {
					System.out.printf("%sWrong input - please choose between %sBuy%s, %sSell%s and %sNone%s:%n%s",
							GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_INPUT);
				}
			}

			if (input.equalsIgnoreCase("Buy") && player.getBalance() >= this.cost) {
				player.decreaseBalance(this.cost);
				changeOwnership(player);
				System.out.printf("%sOk, you've bought %s! %n", GameSystem.RESET,this.getName());
				player.displayBalance();
			} else if (input.equalsIgnoreCase("Buy") && player.getBalance() < this.cost) {
				System.out.printf("%sNot enough money, sell or nothing. Do you want to sell the square to other players?%n"
								+ "Type %sSell%s and Enter to sell%n" 
								+ "Type %sNone%s to end the turn: %n%s",
								GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_INPUT);

				while (true) {
					input = GameSystem.SCANNER.nextLine();

					if (input.equalsIgnoreCase("Sell") || input.equalsIgnoreCase("None")) {
						break;
					} else {
						System.out.printf("%sWrong input - please choose between %sSell%s and %sNone%s: %s",
								GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_INPUT);
					}
				}
			}

			if (input.equalsIgnoreCase("Sell")) {
				ArrayList<Player> accepters = new ArrayList<Player>();
				ArrayList<Player> competitors = new ArrayList<Player>();
				competitors.addAll(GameSystem.players);
				competitors.remove(player);
				
				for (Player competitor : competitors) {
					competitor.getAttention();
					System.out.printf("%s%s%s, do you want to accept the square that %s%s%s refused to buy?%n"
							+ "Type %sAccept%s and Enter to accept%n"
							+ "Type %sRefuse%s and Enter to refuse:%n %s",
							GameSystem.COLOUR_PLAYER, competitor.getName(), GameSystem.RESET, 
							GameSystem.COLOUR_OTHERPLAYER, player.getName(), GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_INPUT);
					
					while (true) {
						input = GameSystem.SCANNER.nextLine();

						if (input.equalsIgnoreCase("Accept") || input.equalsIgnoreCase("Refuse")) {
							break;
						} else {
							System.out.printf(
									"%sWrong input - please choose between %sAccept%s and %sRefuse%s: %s",
									GameSystem.RESET, 
									GameSystem.COLOUR_OPTION, GameSystem.RESET,
									GameSystem.COLOUR_OPTION, GameSystem.RESET,
									GameSystem.COLOUR_INPUT);
						}
					}

					if (input.equalsIgnoreCase("Accept")) {
						accepters.add(competitor);
					}
				}
				
				player.getAttention();
				if (accepters.isEmpty()) {
					System.out.println(GameSystem.COLOUR_PLAYER+player.getName()+GameSystem.RESET + ", no one wants your square, so it will be offered to the next person who lands on it.");
				} else if (accepters.size() == 1) {
					System.out.println(GameSystem.COLOUR_PLAYER+player.getName()+GameSystem.RESET + " congratulations, your square was sold to "
							+ GameSystem.COLOUR_OTHERPLAYER+accepters.get(0).getName()+GameSystem.RESET);
					
					changeOwnership(accepters.get(0));
				} else {
					System.out.printf("%s%s%s, there are %s players that are willing to buy your square.%n"
							+ "Please enter the name of the one you prefer to sell the square.%n"
							+ "Choose between these players:%n", 
							GameSystem.COLOUR_PLAYER, player.getName(), GameSystem.RESET,
							accepters.size());

					for (Player accepter : accepters) {
						System.out.println(GameSystem.COLOUR_OTHERPLAYER+accepter.getName()+GameSystem.RESET);
					}
					
					System.out.println(GameSystem.COLOUR_INPUT);

					boolean flag = false;
					while (true) {
						input = GameSystem.SCANNER.nextLine();
						for (Player accepter : accepters) {
							if (input.equalsIgnoreCase(accepter.getName())) {
								accepter.decreaseBalance(this.cost);
								changeOwnership(accepter);
								System.out.printf("%sOk, %s%s%s bought %s! %n%n", 
										GameSystem.RESET,
										GameSystem.COLOUR_OTHERPLAYER, accepter.getName(), GameSystem.RESET,
										this.getName());
								accepter.displayBalance();
								flag = true;
							}
						}

						if (flag) {
							break;
						} else {
							System.out.println(GameSystem.RESET+ "Wrong input - please choose between one of the valid names: "+GameSystem.COLOUR_INPUT);
						}
					}

					System.out.println(GameSystem.RESET+ "You have sold the square successfully.");
				}
			}
		} catch (Exception e) {
			System.err.println("We had a problem, skip turn.");
			e.printStackTrace();
			GameSystem.SCANNER.nextLine(); // clean the scanner to avoid other errors in GameSystem
		}
	}
	
	/**
	 * This method transfer or assign the ownership of this area to a Player.
	 * To do so the method modifies the ownership in the Group object if all the areas are owned
	 * by one player after the transfer of the ownership, sets the new owner of this area and updates
	 * the owned groups and squares on the Player object at once.
	 * @param player - the player who gets the ownership of this area
	 */
	public void changeOwnership(Player player) {
		// retrieve the group of this square
		Group group = GameSystem.board.getGroup(this);
		
		// check if the square is in a group owned by someone. if yes:
		if (!Objects.equals(group.getOwner(), null)) {
			// remove ownership in group
			group.setOwner(null);
			// remove ownership in player
			group.getOwner().removeOwnership(group);
		// if the player has all the squares of the group:
		} else if (player.getOwnedSquares().containsAll(group.getAreas())) {
			// add ownership in group
			group.setOwner(player);
			// add ownership in player
			player.gainOwnership(group);
		}
		
		// check if the square is owned. if yes:
		if (!Objects.equals(this.owner, null)) {
			// remove ownership in player
			this.owner.removeOwnership(this);
		}
		
		// change ownership in Area
		this.owner = player;
		// change ownership in player
		player.gainOwnership(this);
	}
	
	/**
	 * This method remove the ownership of this area to a player.
	 * To do so the area should be owned by the player. It updates
	 * the owned groups and squares on the Player object, the Group
	 * owner of that area and the area owner at once.
	 * @param player - the player who gets this area removed
	 */
	public void removeOwnership(Player player) {
		// check if the square is owned by the player. if yes:
		if (Objects.equals(this.owner, player)) {
			// retrieve the group of this square
			Group group = GameSystem.board.getGroup(this);
			
			// the group
				// remove ownership in group
			group.setOwner(null);
				// remove ownership in player
			player.removeOwnership(group);
			
			// the square
				// remove ownership in player
			player.removeOwnership(this);
				// remove ownership in Area
			this.owner = null;
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

			System.out.println(
					GameSystem.RESET+ "Sorry, you don't have enough money to pay rent. You are forced to enter the AUCTION SYSTEM");
			do {
				if (p1.getOwnedSquares().isEmpty()) {
					System.out.println("You have no more properties for sell. You bankrupted!");
					GameSystem.players.remove(p1);
				} else {
					dutchAuctionSystem(p1);
				}
			} while (p1.getBalance() < a1.getCurrentRent());
			if (GameSystem.players.contains(p1)) {
				p1.decreaseBalance(a1.getCurrentRent());
				a1.getOwner().increaseBalance(a1.getCurrentRent());
				System.out.printf("Rent fee %s%s%,d%s%s paid successfully to %s%s%s.%n", 
						GameSystem.COLOUR_RESOURCE, GameSystem.PRE, a1.getCurrentRent(), GameSystem.SUF, GameSystem.RESET,
						GameSystem.COLOUR_OTHERPLAYER, a1.getOwner().getName(), GameSystem.RESET);
			}
		} else {
			p1.decreaseBalance(a1.getCurrentRent());
			a1.getOwner().increaseBalance(a1.getCurrentRent());
			System.out.printf("Rent fee %s%s%,d%s%s paid successfully to %s%s%s.%n", 
					GameSystem.COLOUR_RESOURCE, GameSystem.PRE, a1.getCurrentRent(), GameSystem.SUF, GameSystem.RESET,
					GameSystem.COLOUR_OTHERPLAYER, a1.getOwner().getName(), GameSystem.RESET);
			// TODO eliminate code repetition by renaming this method and putting the repeated coade in payRent();
		}

	}

	/**
	 * @author Li
	 * @param auctioneer Trade System to help player sell their property in
	 *                   DutchAuctionSystem
	 * @throws Exception
	 */
	public static void dutchAuctionSystem(Player auctioneer) {
		if (auctioneer.getOwnedSquares().isEmpty()) {
			System.out.println(GameSystem.RESET+ "You don't have any property, auction system closed");
			return;
		}
		try {
			boolean getResult = false;
			int gamerInput;
			boolean legalInput = false;
			ArrayList<Square> auctionnerSet = new ArrayList<>();
			auctionnerSet = auctioneer.getOwnedSquares();
			Area tradeArea = new Area();
			
			//TODO givess plz enter legal input, looping error?
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

			while (true) {
				
				try {
					gamerInput = GameSystem.SCANNER.nextInt();
					if(gamerInput<0) {
						System.out.println("Please type into a number greater than zero");
					}else {
						break;
					}
				}catch(Exception e) {
					System.out.println("Please enter in positive integer");
				}
			}
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
							ArrayList<Square> demoSquare = tradeArea.getOwner().getOwnedSquares();
							demoSquare.remove(tradeArea);
							tradeArea.getOwner().setOwnedSquares(demoSquare);
							ArrayList<Square> Demo = p.getOwnedSquares();
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
