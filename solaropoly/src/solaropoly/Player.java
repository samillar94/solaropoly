/**
 * 
 */
package solaropoly;

import java.util.ArrayList;

/**
 * This class holds properties about the player's status in game, including name, balance, position
 * and assets
 * @author G17
 */
public class Player {
	

	
	/**
	 * Force the business rule if required to stop the game if there's a limit of loops on the board
	 */
	private static final int LOOPS_LIMIT = 1;
	
	/**
	 * The result of a die should be minimum 1. Set the limit based on the number of dice the game should use
	 */
	private static final int DICE_NUMBER = 2;
	
	// instance vars
	private String name;
	private int balance;
	private int position;
	private ArrayList<Square> ownedSquares; // TODO make this TreeSet/ArrayList
	private ArrayList<Group> ownedGroups;
	private int turns;
	
	/**
	 * Default constructor
	 */
	public Player() {}
	
	/**
	 * Constructor with arguments
	 * @param name
	 * @param balance
	 * @param position
	 */
	public Player(String name, int balance, int position) throws IllegalArgumentException, IllegalStateException {
		this.setName(name);
		this.balance = balance;
		this.setPosition(position);
		this.ownedSquares = new ArrayList<Square>();
		this.ownedGroups = new ArrayList<Group>();
		this.turns = 0;
	}
	
	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * it gives if positive or stop if negative a certain amount of turns to the player.
	 * @param turns the turns to set
	 */
	public void setTurns(int turns) {
		this.turns = turns;
	}
	
	/**
	 * Increase turn number
	 */
	public void increaseTurns() {
		this.turns++;
	}
	
	/**
	 * Decrease turn number
	 */
	public void decreaseTurns() {
		this.turns--;
	}
	
	/**
	 * Decrease turn number
	 */
	public void sumTurns(int turns) {
		this.turns += turns;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) throws IllegalArgumentException, IllegalStateException {
		if (name.strip() == "" || name == null) {
			throw new IllegalArgumentException("Need at least one non-whitespace character");
		} else if (name.length() > 20) {
			this.name = name.substring(0, 19);
			System.out.println(GameSystem.RESET+"We did have to shorten that - sorry!");
		} else {
			this.name = name;
		}
	}
	
	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) throws IllegalArgumentException {
		if (position >= (GameSystem.board.getSize() * LOOPS_LIMIT) || position < 0) {
			throw new IllegalArgumentException("Invalid player position. Try to reduce the dice sides or the number of dice");
		} else {
			this.position = position;
		}
	}
	
	/**
	 * @return the ownedSquares
	 */
	public ArrayList<Square> getOwnedSquares() {
		return ownedSquares;
	}
	
	/**
	 * @param ownedSquares the ownedSquares to set
	 */
	public void setOwnedSquares(ArrayList<Square> ownedSquares) {
		this.ownedSquares = ownedSquares;
	}
	
	/**
	 * @return the ownedGroups
	 */
	public ArrayList<Group> getOwnedGroups() {
		return ownedGroups;
	}

	/**
	 * @param ownedGroups the ownedGroups to set
	 */
	public void setOwnedGroups(ArrayList<Group> ownedGroups) {
		this.ownedGroups = ownedGroups;
	}
	
	@Override
	public String toString() {
		return "Player [name=" + GameSystem.COLOUR_PLAYER + name + GameSystem.RESET + ", balance=" + balance + ", position=" + position + ", ownedSquares="
				+ ownedSquares + ", ownedGroups=" + ownedGroups + "]";
	}
	
	// Methods
	
	/**
	 * This method prints in console a long line that cover the width of the terminal automatically.
	 * It uses the current terminal width and prints the line separation to apply when the player
	 * attention should be caught because an action is required. Then it prints the name of the player
	 * asking to take an action.
	 */
	public void getAttention() {
		int consoleWidth = 80; // Default console width
        try {
            consoleWidth = Integer.parseInt(System.getenv("COLUMNS"));
        } catch (NumberFormatException e) {
            // Ignore the exception and use the default console width
        }
        System.out.println(GameSystem.RESET+String.valueOf(GameSystem.SEPARATOR_CHAR).repeat(consoleWidth));
        System.out.printf("%s%s%s, take action!%n%n", GameSystem.COLOUR_PLAYER, this.name, GameSystem.RESET);
	}
	
	/**
	 * This method sums the outputs of all areas that the player owns.
	 * @return output in MW
	 */
	public int getOutput() {
		
		int builder = 0;
		for (Square square : this.getOwnedSquares()) {
			if (square instanceof Area) builder += ((Area) square).getCurrentOutput();
		}
		return builder;
		
	}
	
	/**
	 * This method returns the square where the player actually is.
	 */
	public Square getLandedSquare() {
		return GameSystem.board.getSquare(this.position);
	}
	
	/**
	 * This method move the player by a given valid dice result value and start his turn
	 */
	public void move(int roll) throws IllegalArgumentException {
		
			BoardPosition boardPosition = GameSystem.board.getBoardPosition(this.position, roll);
			
			for (int i = 0; i < boardPosition.getStartPassed(); i++) {
				Sunrise sunrise = (Sunrise) GameSystem.board.getSquare(0);
				sunrise.passAct(this);
			}
			
			this.setPosition(boardPosition.getPosition());
			
			boardPosition.getSquare().act(this);
			
			// develop area and trade actions
			String input = "";
			System.out.printf("%sIf you would like to trade or develop an area type %sTrade%s or %sDevelop%s and press Enter.%n"
					+ "Otherwise, just press Enter to end your turn:%s%n",
					GameSystem.RESET,
					GameSystem.COLOUR_OPTION, GameSystem.RESET,
					GameSystem.COLOUR_OPTION, GameSystem.RESET,
					GameSystem.COLOUR_INPUT);
			
			while (true) {
				input = GameSystem.SCANNER.nextLine();
				
				if (input.equalsIgnoreCase("Trade") || input.equalsIgnoreCase("Develop") || input == null || input.equals("")) {
					break;
				} else {
					System.out.printf("%sWrong input - please choose between %sTrade%s, %sDevelop%s and continue (Enter):%n%s",
							GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_OPTION, GameSystem.RESET,
							GameSystem.COLOUR_INPUT);
				}
			}
			
			if (input.equalsIgnoreCase("Develop")) {
				System.out.println(GameSystem.RESET + "You chose to develop an area.");
				GameSystem.developArea(this);
			} else if (input.equalsIgnoreCase("Trade")) {
				System.out.println(GameSystem.RESET + "You chose to trade.");
				Area.dutchAuctionSystem(this);
			}
		
	}
	
	public void displayBalance() {
		System.out.printf("%s%s%s, your current balance is %s%s%,d%s%s and you own:%n"
				, GameSystem.COLOUR_PLAYER, this.name, GameSystem.RESET
				, GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.balance, GameSystem.RES_SUF, GameSystem.RESET);
		System.out.println("\n  Areas: "+this.getOwnedSquares());
		System.out.println("  Regions: "+this.getOwnedGroups()+"\n");
	}
	
	public void increaseBalance(int credit) {
		this.setBalance(this.balance + credit);
	}
	
	public void decreaseBalance(int debit) {
		this.setBalance(this.balance - debit);
	}
	
	/**
	 * Add a new owned square
	 * @param square
	 */
	public void gainProperty(Square square) {
		this.ownedSquares.add(square);
	}
	
	/**
	 * Add a new owned group
	 * @param group
	 */
	public void gainProperty(Group group) {
		this.ownedGroups.add(group);
	}
	
	/**
	 * Remove a square from the owned squares
	 * @param square
	 */
	public void removeProperty(Square square) {
		this.ownedSquares.remove(square);
	}
	
	/**
	 * Remove a group from the owned groups
	 * @param group
	 */
	public void removeProperty(Group group) {
		this.ownedGroups.remove(group);
	}
	
	/**
	 * This method transfer or assign the ownership of a given Area to this player.
	 * To do so the method modifies the ownership in the Group object if all the areas are owned
	 * by one player after the transfer of the ownership, sets the new owner of the area and updates
	 * the owned groups and squares on this object at once.
	 * @param area - the area to gain.
	 */
	public void gainOwnership(Area area) {
		area.changeOwnership(this);
	}
	
	/**
	 * This method transfer or assign the ownership of a given Group to this player.
	 * To do so the method modifies the ownership in the Group, sets the new owner
	 * of the areas of the group and updates the owned groups and squares on this object at once.
	 * It also resets the ownership to other players if they had one of the areas of the group.
	 * @param group - the whole group to gain.
	 */
	public void gainOwnership(Group group) {
		for (Area area : group.getAreas()) {
			area.changeOwnership(this);
		}
	}
	
	/**
	 * This method remove the ownership of a given Area to this player.
	 * To do so the Area should be owned by this player. It updates
	 * the owned groups and squares on this object, the Group
	 * owner of that area and the area owner at once.
	 * @param area - the area to remove
	 */
	public void removeOwnership(Area area) {
		area.removeOwnership(this);
	}
	
	/**
	 * This method remove the ownership of a given Group to this player.
	 * To do so the Area should be owned by this player. It updates
	 * the owned groups and squares on this object, the Group
	 * owner of that area and the area owner at once.
	 * @param group - the whole group to remove
	 */
	public void removeOwnership(Group group) {
		if (this.getOwnedSquares().containsAll(group.getAreas())) {
			for (Area area : group.getAreas()) {
				area.removeOwnership(this);
			}
		} else {
			System.err.println("This player doesn't own all the squares of this group");
		}
	}
	
	/**
	 * This method is used to transfer {@value GameSystem#RES_SUF} between players.
	 * @param player - the player that receive the transaction.
	 * @param amount - the amount of the transaction.
	 * @return boolean - if the transaction goes well returns true
	 */
	public boolean transaction(Player player, int amount) {
		if (this.balance >= amount) {
			try {
				player.increaseBalance(amount);
				this.decreaseBalance(amount);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Transaction aborted...");
			}
		} else {
			System.out.printf("Sorry %s%s%s, you don't have enough resource to transfer to %s%s%s, your balance is %s%s%,d%s%s.%n",
					GameSystem.RESET, 
					GameSystem.COLOUR_PLAYER, this.name, GameSystem.RESET, 
					GameSystem.COLOUR_OTHERPLAYER, player.getName(), GameSystem.RESET, 
					GameSystem.COLOUR_RESOURCE, GameSystem.RES_PRE, this.balance, GameSystem.RES_SUF, GameSystem.RESET);
		}
		return false;
	}
}
