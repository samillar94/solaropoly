/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The executable class of the system, starting the Solaropoly command-line
 * game.
 * 
 * @author G17
 */
public class GameSystem {

	/// game parameter constants

	public static final int STARTING_BALANCE = 15000000;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final String PRE = ""; /// resource prefix
	public static final String SUF = " kWh"; /// resource suffix

	/// console colour keys
	// Regular
	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	// Bold
	public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
	public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
	public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
	public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

	// Underline
	public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
	public static final String RED_UNDERLINED = "\033[4;31m"; // RED
	public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
	public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
	public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
	public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
	public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
	public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

	// Background
	public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
	public static final String RED_BACKGROUND = "\033[41m"; // RED
	public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
	public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
	public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
	public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
	public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
	public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

	// High Intensity
	public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
	public static final String RED_BRIGHT = "\033[0;91m"; // RED
	public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
	public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
	public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
	public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
	public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
	public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

	// Bold High Intensity
	public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
	public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
	public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

	// High Intensity backgrounds
	public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
	public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
	public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
	public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
	public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
	public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
	public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
	public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

	/// essential components

	public static final Scanner SCANNER = new Scanner(System.in);

	public static boolean gameEndTrigger = false;

	public static ArrayList<Player> players = new ArrayList<Player>();

	/// executable code

	/**
	 * Main method - execution starts here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			/// set number of players
			int numPlayers = setNumPlayers();

			/// register players
			players = registerPlayers(numPlayers);

			/// game starts - do while (and try-catch?), cycling through players until game
			/// end triggered
			while (!gameEndTrigger) {

				for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {

					Thread.sleep(2000);

					turn(players.get(playerIndex));

					if (gameEndTrigger)
						break;

				}

			}

			/// game ending
			System.out.println("\nGAME OVER!\nAssets accumulated:");

			/// TODO put this into a method so it can also be called when players leave
			/// early
			for (Player player : players) {

				int propertyValue = 0;

				for (Square area : player.getOwnedSquares()) {
					propertyValue += ((Area) area).getCost();
				}

				System.out.printf("%s%s%s has £%,d cash and owns £%,d of assets %s for a total of £%,d.%n", RED_BRIGHT,
						player.getName(), RESET, player.getBalance(), propertyValue,
						player.getOwnedSquares().toString(), player.getBalance() + propertyValue);

			}

			System.out.println("Thank you for playing SOLAROPOLY");

		} catch (InterruptedException e) {

			System.out.println("Sorry, the sleep was interrupted and the softwear engine crashed.");
			e.printStackTrace();

		} catch (Exception e) {

			System.out.println("Sorry, something went wrong and the softwear engine crashed.");
			e.printStackTrace();

		}

	}

	/**
	 * Takes user input at the start of the game to set the number of players whose
	 * names will be registered.
	 * 
	 * @return the number of players
	 */
	private static int setNumPlayers() {

		int num = 0;

		while (num == 0) {
			System.out.println("How many are playing? Type a number between " + MIN_PLAYERS + " and " + MAX_PLAYERS
					+ " and press Enter.");
			try {
				num = SCANNER.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Sorry, need a whole number.");
			} catch (Exception e) {
				System.out.println("Sorry, try again.");
			}

		}

		players = new ArrayList<Player>(num);
		return num;

	}

	/**
	 * Registers players by name, including validation
	 * 
	 * @return an ArrayList of Players
	 */
	private static ArrayList<Player> registerPlayers(int numPlayers) {

		ArrayList<Player> playersBuilder = new ArrayList<Player>();
		ArrayList<String> names = new ArrayList<>();
		boolean resolved;
		SCANNER.nextLine(); /// not sure why I need this

		for (int playerNum = 1; playerNum <= numPlayers; playerNum++) {

			resolved = false;

			while (!resolved) {

				try {

					System.out.printf("%nPlayer %d, please enter your name: ", playerNum);

					String name = SCANNER.nextLine();

					if (names.contains(name)) {

						System.out.printf("Sorry, %s, players need unique names - maybe call yourself 'Funky %s'?%n",
								name, name);

					} else {

						playersBuilder.add(new Player(name, STARTING_BALANCE, 0));
						names.add(name);
						resolved = true;
						System.out.printf("Welcome %s%s%s! You start the game with a balance of %s%,d%s.%n", RED_BRIGHT,
								playersBuilder.get(playerNum - 1).getName(), RESET, PRE, STARTING_BALANCE, SUF);

					}

				} catch (Exception e) {

					System.out.println("Sorry, that wasn't a valid name.");

				}

			}

		}

		System.out.println("Welcome, all, to SOLAROPOLY");

		return playersBuilder;
	}

	/**
	 * This method is called for each player in turn from the main method, and
	 * organises the inputs and results that will be a part of their turn, including
	 * (1) deciding whether to abandon the game or roll and move; (2) developing any
	 * fields they have monopolised; and (3) trading assets with other players
	 * 
	 * @param player
	 */
	private static void turn(Player player) {

		boolean consent = consent(player);

		if (consent) {

			move(player);
			/// TODO
//			develop(player);
//			trade(player);

		} else {

			players.remove(player);
			/// TODO print player assets
			/// TODO remove their ownerships from assets

		}

		if (players.size() == 1)
			gameEndTrigger = true;

	}

	/**
	 * This method is called before each turn to ask the player if they want to
	 * continue or leave the game.
	 * 
	 * @param player
	 * @return
	 */
	private static boolean consent(Player player) {

		System.out.println();
		player.displayBalance();

		System.out.printf(
				"If you would like to take your turn, press Enter.%nOtherwise, enter any character and press Enter to leave the game. ",
				player.getName());

		String input = SCANNER.nextLine();

		if (input == null || input == "") {
			return true;
		} else {
			return false;
		}

	}

	private static void move(Player player) {
		/// TODO
//		int roll = rollDice();

		/// TODO movement on board

	}

	/**
	 * develop method
	 * 
	 * @param player
	 */
	private static void developArea(Player player) {
		boolean groupStatus = true;
		boolean areaStatus = true;
		ArrayList<Area> fullyDevelopedAreaStatus = new ArrayList<Area>();
		if (player.getOwnedGroups().size() > 0) {

			do {
				displayMenu(player);
				System.out.println();
				System.out.println(
						"If you wish to develop within a group please enter the group name, else, to skip just press Enter");
				String inputGroup = SCANNER.nextLine();

				if (inputGroup == null || inputGroup == "") {
					System.out.println("System recognises you do not wish to develop");
					groupStatus = false;
				} else {

					for (Group group : player.getOwnedGroups()) {

						if (group.getName().equalsIgnoreCase(inputGroup)) {
							do {

								System.out.println(
										"Please enter which area you would like to develop, else, to stop developing press Enter ");
								String inputArea = SCANNER.nextLine();

								if (inputArea == null || inputArea == "") {
									System.out.println("System recognises you no longer wish to develop");
									areaStatus = false;

								} else {
									// developing areas loop
									for (Square square : player.getOwnedSquares()) {

										if (square.getName().equalsIgnoreCase(inputArea)) {
											Area area;

											if (square instanceof Area) {
												area = (Area) square;

												if (area.getDevelopmentLevel() < 3) {

													if (player.getBalance() >= area.getMinorDevelopmentCost()) {
														area.setDevelopmentLevel();
														System.out.println(area.getName() + " level: "
																+ area.getDevelopmentLevel());
													} else {
														throw new IllegalArgumentException("Insufficient funds");

													}

												} else if (area.getDevelopmentLevel() == 3) {
													if (player.getBalance() >= area.getMajorDevelopmentCost()) {
														area.setDevelopmentLevel();
														System.out.println("Major development achieved."
																+ area.getName() + " developed "
																+ area.getDevelopmentLevel() + " times");
													} else {
														throw new IllegalArgumentException("Insufficient funds");
													}

												} else {
													throw new IllegalArgumentException(
															"Error: not able to develop area.");

												}

											} else {
												throw new IllegalArgumentException(
														"Please enter a square that is an area");
											}

										}
									}
									// condition for all areas fully developed
									do {
										for (Square square : player.getOwnedSquares()) {
											Area area;

											if (square instanceof Area) {
												area = (Area) square;
												if (area.getDevelopmentLevel() == 4) {
													fullyDevelopedAreaStatus.add(area);
												}
											}
										}
									} while (fullyDevelopedAreaStatus.size() != player.getOwnedSquares().size());

									if (fullyDevelopedAreaStatus.size() == player.getOwnedSquares().size()) {
										areaStatus = false;
									}

								}

							} while (areaStatus != false);

						} else {
							throw new IllegalArgumentException(
									"Please enter a valid group name or a group that you own");

						}
					}
				}

			} while (groupStatus != false);
		}
	}

	/**
	 * generates the menu using the getmenuitems method
	 * @param player
	 */
	private static void displayMenu(Player player) {
		
		System.out.println("Player balance: $" + player.getBalance());
		System.out.format("%-15s%-15s%-15s%s%n", "Field", "Square", "Level", "Upgrade Cost");
		
		List<MenuItem> menuItems = getMenuItems(player);
		
		for (MenuItem menuItem : menuItems) {
			System.out.format("%-15s%-15s%-15s$%d%n", menuItem.getField(), menuItem.getSquare(), menuItem.getLevel(),
					menuItem.getUpgradeCost());
		}

	}
	
	/**
	 * generates a each row for each property owned by each player during development to see their 
	 * owned properties and associated costs with development 
	 * 
	 * @param player
	 * @return
	 */
	public static List<MenuItem> getMenuItems(Player player) {
		
	    List<MenuItem> menuItems = new ArrayList<>();
	    
	    int playerBalance = player.getBalance();
	    
	    for (Group group : player.getOwnedGroups()) {
	    	
	    	// is each square mapped to a group somewhere?
	        for (Square square : player.getOwnedSquares()) {
	        	
	            if (square instanceof Area) {
	                Area area = (Area) square;
	                String groupName = group.getName();
	                String squareName = square.getName();
	                String developmentLevel = "";
	                int developmentCost = 0; // if costs are in an array, can iterate through them for each cost
	                
	                if (player.getOwnedSquares().contains(area)) {
	                    developmentLevel = Integer.toString(area.getDevelopmentLevel());
	                    developmentCost = area.getMinorDevelopmentCost();
	                }
	                
	                menuItems.add(new MenuItem(groupName, squareName, developmentLevel, developmentCost));
	                
	            } else {
	            	// so this is basically if someone owns a square thats like a train station
	            	// so not sure it has a group or a development level etc?
	                String groupName = group.getName();
	                String squareName = square.getName();
	                String developmentLevel = "";
	                
	               
	                menuItems.add(new MenuItem("", squareName, "", 0));
	            }
	        }
	    }
	    System.out.println("Player balance: " + playerBalance);
	    
	    return menuItems;
	}



	/**
	 * 
	 * @author andrewscott
	 *class to store the menu item objects
	 */
	private static class MenuItem {
		private String field;
		private String square;
		private String level;
		private int upgradeCost;

		// contructor with args
		public MenuItem(String field, String square, String level, int upgradeCost) {
			this.field = field;
			this.square = square;
			this.level = level;
			this.upgradeCost = upgradeCost;
		}

		// getters and setters
		public String getField() {
			return field;
		}

		public String getSquare() {
			return square;
		}

		public String getLevel() {
			return level;
		}

		public int getUpgradeCost() {
			return upgradeCost;
		}
	}
	


}
