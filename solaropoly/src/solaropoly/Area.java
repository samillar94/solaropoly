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
	private int baseOutput;

	private int monopolyLevel = 0;
	private int developmentLevel = 0;
	public static final int MAX_LEVEL = 4;
	private static final int MIN_LEVEL = 0;
	private int dutchPrice=2147483647;
	
	
	

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
	public Area(String name, Group group, int cost, int[] monopolyProfile, int[] developmentProfile, int baseOutput) {
		super(name);
		this.group = group;
		this.setCost(cost);
		this.rentProfile.put("Monopoly",
				(ArrayList<Integer>) Arrays.stream(monopolyProfile).boxed().collect(Collectors.toList()));
		this.rentProfile.put("Development",
				(ArrayList<Integer>) Arrays.stream(developmentProfile).boxed().collect(Collectors.toList()));
		this.baseOutput = baseOutput;
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
	 * @return the baseOutput
	 */
	public int getBaseOutput() {
		return baseOutput;
	}

	/**
	 * @param baseOutput the baseOutput to set
	 */
	public void setBaseOutput(int baseOutput) {
		this.baseOutput = baseOutput;
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
	 * @param incrementLevel the developmentLevel to set
	 */
	public int incrementDevelopmentLevel() {
		
		developmentLevel++;
		if(developmentLevel<MIN_LEVEL || developmentLevel > MAX_LEVEL) {
			throw new IllegalArgumentException("Development level level must be greater or equal to " + MIN_LEVEL +  " or less than or equal to "+ MAX_LEVEL);
					
		} 
		return developmentLevel;
	}

	
	/// methods
	
	/**
	 * This method returns 0 if a square is unowned, but if owned it sums the baseOutput and any applicable buffs
	 * @return
	 */
	public int getCurrentOutput() {
		int builder = 0;
		if (this.getOwner() != null) {
			builder += this.baseOutput;
			if (this.getMonopolyLevel() == 1) builder += this.getGroup().getMonopolyOutputBuff();
			if (this.getDevelopmentLevel() < 4) builder += this.getGroup().getMinorDevOutputBuff() * this.getDevelopmentLevel();
			if (this.getDevelopmentLevel() == 4) builder += this.getGroup().getMajorDevOutputBuff();
		}
		return builder;
	}

	public int getMaxOutput() {
		return this.baseOutput + this.getGroup().getMonopolyOutputBuff() + (this.getGroup().getMinorDevOutputBuff() * 3) + this.getGroup().getMajorDevOutputBuff();
	}
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
		
		String details = String.format(
				 "  Region: %s%s%s • Owner: %s%s%s %n"
				+"  Investments attracted: base: %s%s%,d%s%s • current: %s%s%,d%s%s • max: %s%s%,d%s%s %n"
				+"  Output: base: %s%s%,d%s%s • current: %s%s%,d%s%s • max: %s%s%,d%s%s %n"
//				+"  Sale price: %s%s%,d%s%s"
				, GameSystem.COLOUR_LOCATION, this.group.getName(), GameSystem.RESET
				, GameSystem.COLOUR_OTHERPLAYER, ((this.owner != null) ? this.owner.getName() : "-"), GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.getBaseRent(), GameSystem.RES_SUF, GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.getCurrentRent(), GameSystem.RES_SUF, GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.getRentProfile().get("Development").get(MAX_LEVEL), GameSystem.RES_SUF, GameSystem.RESET
				, GameSystem.COLOUR_OUTPUT, GameSystem.OUT_PRE, this.getBaseOutput(), GameSystem.OUT_SUF, GameSystem.RESET
				, GameSystem.COLOUR_OUTPUT, GameSystem.OUT_PRE, this.getCurrentOutput(), GameSystem.OUT_SUF, GameSystem.RESET
				, GameSystem.COLOUR_OUTPUT, GameSystem.OUT_PRE, this.getMaxOutput(), GameSystem.OUT_SUF, GameSystem.RESET
//				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.getCost(), GameSystem.RES_SUF, GameSystem.RESET
				);
		return details;

	}
	
	@Override
	public String toString() {
		return String.format("%n    %s%s%s (%s, cost %s%s%,d%s%s, output %s%s%,d%s%s)"
				, GameSystem.COLOUR_LOCATION, this.getName(), GameSystem.RESET
				, this.group 
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.getCost(), GameSystem.RES_SUF, GameSystem.RESET
				, GameSystem.COLOUR_OUTPUT, GameSystem.OUT_PRE, this.getCurrentOutput(), GameSystem.OUT_SUF, GameSystem.RESET
				);
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
				.println("You landed in: "  + GameSystem.COLOUR_LOCATION + this.getName() + GameSystem.RESET + " (position "  + GameSystem.COLOUR_LOCATION + player.getPosition() + GameSystem.RESET + ")\n" + "Area details:\n\n" + this.detailsArea());

		if (this.owner == null) {
			purchaseArea(player);
		} else if (this.owner == player) {
			System.out.println("Opulence! You own this.\n");
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
						"%s%s%s, would you like to buy or offer this area for %s%s%,d%s%s?%n" 
						+ "Type %sBuy%s and Enter to buy%n"
						+ "Type %sOffer%s and Enter to offer it to the other players%n" 
						+ "Type %sNo%s to end the turn%s%n",
						GameSystem.COLOUR_PLAYER, player.getName(), GameSystem.RESET,
						GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.cost, GameSystem.RES_SUF, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_OPTION, GameSystem.RESET,
						GameSystem.COLOUR_INPUT);
			String input = "";

			while (true) {
				input = GameSystem.SCANNER.nextLine();

				if (input.equalsIgnoreCase("Buy") || input.equalsIgnoreCase("Offer") || input.equalsIgnoreCase("No")) {
					break;
				} else {
					System.out.printf("%sWrong input - please choose between %sBuy%s, %sOffer%s and %sNo%s:%n%s",
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
				System.out.printf("%n%sOk, you've bought %s%s%s! %n%n", GameSystem.RESET
						, GameSystem.COLOUR_LOCATION, this.getName(), GameSystem.RESET);
				Thread.sleep(1000);
				player.displayBalance();
			} else if (input.equalsIgnoreCase("Buy") && player.getBalance() < this.cost) {
				System.out.printf("%sInsufficient funds. Do you want to offer the area to other players?%n"
								+ "Type %sOffer%s and Enter to offer%n" 
								+ "Type %sNo%s to end the turn: %n%s",
								GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_INPUT);

				while (true) {
					input = GameSystem.SCANNER.nextLine();

					if (input.equalsIgnoreCase("Offer") || input.equalsIgnoreCase("No")) {
						break;
					} else {
						System.out.printf("%sWrong input - please choose between %sOffer%s and %sNo%s:%n%s",
								GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_INPUT);
					}
				}
			}

			if (input.equalsIgnoreCase("Offer")) {
				ArrayList<Player> accepters = new ArrayList<Player>();
				ArrayList<Player> competitors = new ArrayList<Player>();
				competitors.addAll(GameSystem.players);
				competitors.remove(player);
				
				for (Player competitor : competitors) {
					competitor.getAttention();
					System.out.printf("%s%s%s, do you want to bid for the area %s%s%s that %s%s%s declined to buy?%n"
							+ "Type %sBid%s and Enter to bid%n"
							+ "Type %sNo%s and Enter to refuse:%n%s",
							GameSystem.COLOUR_PLAYER, competitor.getName(), GameSystem.RESET,
							GameSystem.COLOUR_LOCATION, this.getName(), GameSystem.RESET,
							GameSystem.COLOUR_OTHERPLAYER, player.getName(), GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_INPUT);
					
					while (true) {
						
						input = GameSystem.SCANNER.nextLine();

						if (!input.equalsIgnoreCase("Bid") && !input.equalsIgnoreCase("No")) {
						System.out.printf(
								"%sWrong input - please choose between %sBid%s and %sNo%s:%n%s",
								GameSystem.RESET, 
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_OPTION, GameSystem.RESET,
								GameSystem.COLOUR_INPUT);
						
						} else {
							break;
						}
						
					}

					if (input.equalsIgnoreCase("Bid")) {
						accepters.add(competitor);
					}
				}
				
				if (accepters.isEmpty()) {
					System.out.println(GameSystem.RESET+"No-one wants the area, so it will be left to the next person who lands on it.");
				} else if (accepters.size() == 1) {
					Player accepter = accepters.get(0);
					System.out.println(GameSystem.RESET);
					
					acceptedOffer(accepter, player);
				} else {
					player.getAttention();
					System.out.printf("%s%s%s, %s players have bid for the area:%s%n",
							GameSystem.COLOUR_PLAYER, player.getName(), GameSystem.RESET,
							accepters.size(), GameSystem.COLOUR_OTHERPLAYER
							);

					for (Player accepter : accepters) {
						System.out.println("  "+accepter.getName());
					}
					
					System.out.println(GameSystem.RESET+"Please choose who you prefer to buy the area:\n"+GameSystem.COLOUR_INPUT);

					boolean flag = false;
					while (true) {
						input = GameSystem.SCANNER.nextLine();
						for (Player accepter : accepters) {
							if (input.equalsIgnoreCase(accepter.getName())) {
								accepter.getAttention();
								acceptedOffer(accepter, player);
								flag = true;
							}
						}

						if (flag) {
							break;
						} else {
							System.out.println(GameSystem.RESET+ "Wrong input - please choose between one of the names: "+GameSystem.COLOUR_INPUT);
						}
					}

					System.out.printf("%s%s%s, you have passed on the square successfully.%n%n"
							, GameSystem.COLOUR_PLAYER, player.getName(), GameSystem.RESET);
				}
			}
		} catch (Exception e) {
			System.err.println("We had a problem, skip turn.");
			e.printStackTrace();
			GameSystem.SCANNER.nextLine(); // clean the scanner to avoid other errors in GameSystem
		}
	}
	
	/**
	 * This method transfer the ownership of a sold area to the accepter and must be used only by purchaseArea.
	 * It updates the balances, print it to screen to the accepter and execute a transaction to the player.
	 * Then it prints to the player that he sold successfully and his updated balance.
	 * @param accepter - the accepter of the offer
	 * @param player - the player who is selling the area
	 */
	private void acceptedOffer(Player accepter, Player player) {
		accepter.transaction(player, this.cost);
		changeOwnership(accepter);
		accepter.displayBalance();
		
		player.getAttention();
		System.out.println(GameSystem.RESET+"The square you landed on was sold to "
				+ GameSystem.COLOUR_OTHERPLAYER + accepter.getName() + GameSystem.RESET + ".");
		player.displayBalance();
	}
	
	
	/**
	 * This method transfer or assign the ownership of this area to a Player.
	 * To do so the method modifies the ownership in the Group object if all the areas are owned
	 * by one player after the transfer of the ownership, sets the new owner of this area and updates
	 * the owned groups and squares on the Player object at once.
	 * @param player - the player who gets the ownership of this area
	 */
	public void changeOwnership(Player player) {
		// check if the square is owned. if yes:
		if (!Objects.equals(this.owner, null)) {
			// remove ownership in player
			this.owner.removeProperty(this);
		}
		
		// change ownership in Area
		this.owner = player;
		// change ownership in player
		player.gainProperty(this);
		
		// check if the square is in a group owned by someone. if yes:
		if (!Objects.equals(this.group.getOwner(), null)) {
			// remove ownership in player
			this.group.getOwner().removeProperty(this.group);
			// remove ownership in group
			this.group.setOwner(null);
		// if the player has all the squares of the group:
		} else if (player.getOwnedSquares().containsAll(this.group.getAreas())) {
			// add ownership in group
			this.group.setOwner(player);
			// add ownership in player
			player.gainProperty(this.group);
		}
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
			// the group
				// remove ownership in group
			this.group.setOwner(null);
				// remove ownership in player
			player.removeProperty(this.group);
			
			// the square
				// remove ownership in player
			player.removeProperty(this);
				// remove ownership in Area
			this.owner = null;
		} else {
			System.err.println("This player doesn't own this square");
		}
	}

	/**
	 * this method will transfer money automatically when players landed on
	 * competitors' land
	 * 
	 * @author Li
	 * 
	 */
	public void rentPay(Player player, Area area) {
		if (player.getBalance() < area.getCurrentRent()) {

			System.out.println(
					GameSystem.RESET+ "Sorry, you don't have enough money to pay rent. You are forced to enter the AUCTION SYSTEM");
			do {
				if (player.getOwnedSquares().isEmpty()) {
					System.out.println("You have no more properties for sell. You bankrupted!");
					GameSystem.playersInGame.remove(player);
				} else {
					dutchAuctionSystem(player);
				}
			} while (player.getBalance() < area.getCurrentRent());
			if (GameSystem.players.contains(player)) {
				player.decreaseBalance(area.getCurrentRent());
				area.getOwner().increaseBalance(area.getCurrentRent());
				System.out.printf("You've made an investment of %s%s%,d%s%s in %s%s%s's company.%n%n", 
						GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, area.getCurrentRent(), GameSystem.RES_SUF, GameSystem.RESET,
						GameSystem.COLOUR_OTHERPLAYER, area.getOwner().getName(), GameSystem.RESET);
			}
		} else {
			player.decreaseBalance(area.getCurrentRent());
			area.getOwner().increaseBalance(area.getCurrentRent());
			System.out.printf("You've made an investment of %s%s%,d%s%s in %s%s%s's company.%n%n", 
					GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, area.getCurrentRent(), GameSystem.RES_SUF, GameSystem.RESET,
					GameSystem.COLOUR_OTHERPLAYER, area.getOwner().getName(), GameSystem.RESET);
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
			System.out.println(GameSystem.RESET + "You don't have any property, auction system closed");
			return;
		}
		try {
			String userName;
			boolean getResult = false;
			int gamerInput;
			boolean legalInput = false;
			ArrayList<Square> auctionnerSet = new ArrayList<>();
			auctionnerSet = auctioneer.getOwnedSquares();
			Area tradeArea = new Area();
			System.out.println("Welcome to Dutch auction system. Here is the basic rules of Duction auction system\n"
			+ColourLibrary.GREEN+"A Dutch auction (also called a descending price auction) refers to a type of auction in which"
			+ " an auctioneer starts with a very high price\n incrementally lowering the price until someone places a bid."+ColourLibrary.RESET);
			System.out.println(
					"Welcome to Dutch auction system,which item would you like to aucion? please enter the name");
			// show the list of owners' estates
			for (Square square : auctionnerSet) {
				Area area = new Area();
				area = (Area) square;
				System.out.println(area.toString());
			}
			String Userinput = GameSystem.SCANNER.nextLine();
			// Let player choose the land he or she wants to sell
			do {
				for (Square square : auctionnerSet) {
					if (Userinput.equalsIgnoreCase(square.getName())) {
						System.out.println("Land is picked up successfully");
						tradeArea = (Area) square;
						getResult = true;
					}
				}
				if (!getResult) {
					System.out.println("Illegal Input, please try again");
					System.out.println();
					Userinput = GameSystem.SCANNER.nextLine();
				}
			} while (!getResult);

			
			do {
				System.out.println("Now please type in the price you want to sell\n"+ColourLibrary.RED+"Hint:Remember that it's dutch auction, your offer should be less than previous round!"+ColourLibrary.RESET);
				while (true) {

					if (GameSystem.SCANNER.hasNextInt()) {
						gamerInput = GameSystem.SCANNER.nextInt();
						
						
						if (gamerInput >= 0 & gamerInput<tradeArea.getDutchPrice()) {
							tradeArea.setDutchPrice(gamerInput);
							break;
						} else {
							System.out.println("Please type in a positive number and it should greater than the price you last offer");
						}
					} else {
						System.out.println("Please type in a positive number");
						GameSystem.SCANNER.next();
					}
				}
				
				System.out.println("Does anyone want receive it offer? The offer price is " + ColourLibrary.CYAN_BRIGHT+gamerInput+ColourLibrary.RESET);
				System.out.println("If someone want to take it, please enter your name."
						+ "\nIf no one wants to take it, please type N and enter and we will go to next round");
				GameSystem.SCANNER.nextLine();
				userName = GameSystem.SCANNER.nextLine().trim();
				if (userName.equalsIgnoreCase("N")) {
continue;
				}
				else {
					break;
				}
			} while (true);
			
			do {
				for (Player player : GameSystem.players) {
					if (player.getName().equalsIgnoreCase(userName)) {
						if (player.getBalance() > gamerInput) {
							player.decreaseBalance(gamerInput);
							tradeArea.getOwner().increaseBalance(gamerInput);
							tradeArea.changeOwnership(player);
							System.out.println("Deal! Do you want to auction another property? Y/N");
							legalInput = true;
						} else {
							System.out.println("Sorry for that you do not have enough balance to buy");
							dutchAuctionSystem(auctioneer);
							break;
						}
					}
				}
				if (!legalInput) {
					System.out.println("Please enter an legal name");
					userName = GameSystem.SCANNER.nextLine().trim();
				}

			} while (!legalInput);
			System.out.println();
			String UserAnswer = GameSystem.SCANNER.nextLine();
			do {
				if (UserAnswer.equalsIgnoreCase("N")) {
					System.out.println("System closed");
					return;
				} else if (UserAnswer.equalsIgnoreCase("Y")) {
					dutchAuctionSystem(auctioneer);
					return;
				}
			} while (!UserAnswer.equalsIgnoreCase("Y") | !UserAnswer.equalsIgnoreCase("N"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception happened, trade system restart");
			dutchAuctionSystem(auctioneer);
		}
	}

	/**
	 * @return the dutchPrice
	 */
	public int getDutchPrice() {
		return dutchPrice;
	}

	/**
	 * @param dutchPrice the dutchPrice to set
	 */
	public void setDutchPrice(int dutchPrice) {
		if(dutchPrice>0) {
		this.dutchPrice = dutchPrice;}
		
	}

}
