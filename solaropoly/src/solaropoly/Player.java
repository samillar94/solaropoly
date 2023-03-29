/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class holds properties about the player's status in game, including name, balance, position
 * and assets
 * @author G17
 */
public class Player {
	
	/**
	 * Separator to use in the getPlayerAttention to separate the action for each player
	 */
	private static final char SEPARATOR_CHAR = '_';
	
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
	private HashSet<Square> ownedSquares; // TODO make this TreeSet/ArrayList
	private ArrayList<Group> ownedGroups;
	
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
		this.ownedSquares = new HashSet<Square>();
		this.ownedGroups = new ArrayList<Group>();
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
			System.out.println("We did have to shorten that - sorry!");
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
	public HashSet<Square> getOwnedSquares() {
		return ownedSquares;
	}
	
	/**
	 * @param ownedSquares the ownedSquares to set
	 */
	public void setOwnedSquares(HashSet<Square> ownedSquares) {
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
		return "Player [name=" + GameSystem.RED_BRIGHT + name + GameSystem.RESET + ", balance=" + balance + ", position=" + position + ", ownedSquares="
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
        System.out.println(String.valueOf(SEPARATOR_CHAR).repeat(consoleWidth));
        System.out.printf("Player %s, take action!%n%n", this.name);
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
		if (roll >= DICE_NUMBER) {
			BoardPosition boardPosition = GameSystem.board.getBoardPosition(this.position, roll);
			
			for (int i = 0; i < boardPosition.getStartPassed(); i++) {
				Go.passAct(this);
			}
			
			this.setPosition(boardPosition.getPosition());
			boardPosition.getSquare().act(this);
			
			// develop area and trade actions
			String input = "";
			System.out.println("If you would like to Trade or Develop an area type Trade or Develop and press Enter.\n"
					+ "Otherwise, just press Enter to skip the turn.");
			
			while (true) {
				input = GameSystem.SCANNER.nextLine();
				
				if (input.equalsIgnoreCase("Trade") || input.equalsIgnoreCase("Develop") || input == null || input.equals("")) {
					break;
				} else {
					System.out.println("Wrong input. please choose between Trade, Develop and Skip (Enter).");
				}
			}
			
			if (input.equalsIgnoreCase("Develop")) {
				System.out.println("You choosed to develop area");
				//GameSystem.developArea();
			} else if (input.equalsIgnoreCase("Trade")) {
				System.out.println("You choosed to trade");
				//Area.dutchAuctionSystem(this);
			}
			
			System.out.println("Turn ended. Next player...");
		} else {
			throw new IllegalArgumentException("Invalid dice roll. Try to change the number of dice to roll");
		}
	}
	
	public void displayBalance() {
		System.out.printf("%s%s%s, your current balance is %s%,d%s.%n", GameSystem.RED_BRIGHT, this.name, GameSystem.RESET, GameSystem.PRE, this.balance, GameSystem.SUF);
		// TODO another version showing properties too
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
	public void gainOwnership(Square square) {
		this.ownedSquares.add(square);
	}
	/**
	 * Add a new owned group
	 * @param group
	 */
	public void gainOwnership(Group group) {
		this.ownedGroups.add(group);
	}
	
	/**
	 * Remove a square from the owned squares
	 * @param square
	 */
	public void removeOwnership(Square square) {
		this.ownedSquares.remove(square);
	}
	
	/**
	 * Remove a group from the owned groups
	 * @param group
	 */
	public void removeOwnership(Group group) {
		this.ownedGroups.remove(group);
	}
	
	/**
	 * This method is used to transfer resources between players.
	 * @param player - the player that receive the transaction.
	 * @param amount - the amount of the transaction.
	 */
	public void transaction(Player player, int amount) {
		if (this.balance >= amount) {
			player.increaseBalance(amount);
			this.decreaseBalance(amount);
		} else {
			System.out.printf("Sorry %s, you don't have enough resource to transfer to %s, your balance is %d%n"
					, this.name, player.getName(), this.balance);
		}
	}
}
