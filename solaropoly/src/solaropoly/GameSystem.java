/**
 * Solaropoly Game
 */
package solaropoly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The executable class of the system, starting the Solaropoly command-line
 * game.
 * 
 * @author G17
 */
public class GameSystem {

	/// game parameter constants (rules)
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

	/// design language colours
	public static final String COLOUR_PLAYER = RED_BOLD_BRIGHT;
	public static final String COLOUR_OTHERPLAYER = RED_BOLD;
	public static final String COLOUR_INPUT = YELLOW;
	public static final String COLOUR_OPTION = YELLOW_BRIGHT;
	public static final String COLOUR_RESOURCE = GREEN_BRIGHT;
	public static final String COLOUR_LOCATION = CYAN_BOLD;
	
	/// essential components
    
    public static Board board = new Board();

	public static final Scanner SCANNER = new Scanner(System.in);

	public static boolean gameEndTrigger = false;

	public static ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * Stores only players that are still in game
	 */
	public static ArrayList<Player> playersInGame = new ArrayList<Player>();;

	/// executable code

	/**
	 * Main method - execution starts here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			setupBoard();
			
			/// set number of players
			int numPlayers = setNumPlayers();
			
			/// register players
			players = registerPlayers(numPlayers);
			playersInGame.addAll(players);
			Board.visualMap();
			/// game starts - do while (and try-catch?), cycling through players until game
			/// end triggered
			while (!gameEndTrigger) {

				for (Player player : players) {
					if (playersInGame.contains(player)) {
						
						player.getAttention();
						Thread.sleep(2000);
						
						if (player.getTurns() < 0) {
							System.out.println("Number of turns to skip: " + Math.abs(player.getTurns()) + ". Turn skipped...");
							player.increaseTurns();
							continue;
						}
						
						player.increaseTurns();
						
						do {
							turn(player);
							player.decreaseTurns();
						} while (player.getTurns() > 0);
						
					}
					if (gameEndTrigger)	break;
					System.out.println(GameSystem.RESET + "Turn ended. Next player...");
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
				
				System.out.printf("%s%s%s has %s%s%,d%s%s and owns %s%s%,d%s%s of assets %s for a total of %s%s%,d%s%s.%n", 
						COLOUR_PLAYER, player.getName(), RESET, 
						COLOUR_RESOURCE, PRE, player.getBalance(), SUF, RESET,
						COLOUR_RESOURCE, PRE, propertyValue, SUF, RESET,
						player.getOwnedSquares().toString(), 
						COLOUR_RESOURCE, PRE, player.getBalance()+propertyValue, SUF, RESET);
				
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
	* reads from csv file rent prices
	*/
	private static void setupBoard() {
		
		ArrayList<Square> squares = new ArrayList<Square>(12);
		ArrayList<Area> areas = new ArrayList<Area>(10);
		ArrayList<Group> groups = new ArrayList<Group>(4);
		
		squares.add(new Go("Go"));
		squares.add(new Parking("Spa"));
		
		groups.add(new Group("Field A"));
		groups.add(new Group("Field B"));
		groups.add(new Group("Field C"));
		groups.add(new Group("Field D"));

	
		File file = new File("Solaropoly.csv");
		
		try {
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			br.readLine();
			line = br.readLine();
			
			while(line!=null) {
				
				String[] data = line.split(",");
				int baseRent = Integer.parseInt(data[0]);
				int oneHouse = Integer.parseInt(data[1]);
				int twoHouse = Integer.parseInt(data[2]);
				int threeHouse = Integer.parseInt(data[3]);
				int oneHotel = Integer.parseInt(data[4]);
				String areaName = data[5];
				int groupIndex = Integer.parseInt(data[6]);
				int cost = Integer.parseInt(data[7]);
				
				int[] monopolyProfile = {baseRent, baseRent*2};
				int[] developmentProfile = {baseRent, oneHouse, twoHouse, threeHouse, oneHotel};
				
				Area area = new Area(areaName, groups.get(groupIndex), cost, monopolyProfile, developmentProfile);
				areas.add(area);
				
				line = br.readLine();	
				
			}
			
			squares.addAll(areas);
			
			for (Group group : groups) {
				group.setAreas(areas);
			}
			
			board.setSquares(squares);
			board.setGroups(new HashSet<Group>(groups));
			br.close();
	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

		while (num<MIN_PLAYERS || num>MAX_PLAYERS) {
			System.out.println(RESET+"How many are playing? Type a number between "+COLOUR_OPTION+MIN_PLAYERS+RESET
				+" and "+COLOUR_OPTION+MAX_PLAYERS+RESET+" and press Enter."+COLOUR_INPUT);
			try {
				num = SCANNER.nextInt();
				if (num<MIN_PLAYERS || num>MAX_PLAYERS) System.out.println(RESET+"Sorry, invalid number.");
			} catch (InputMismatchException e) {
				System.out.println(RESET+"Sorry, need a whole number."+COLOUR_INPUT);
				SCANNER.nextLine();
			} catch (Exception e) {
				System.out.println(RESET+"Sorry, try again."+COLOUR_INPUT);
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
					
					System.out.printf("%n%sPlayer %d, please enter your name: %s", RESET, playerNum, COLOUR_INPUT);	

					String name = SCANNER.nextLine();

					if (names.contains(name)) {
						
						System.out.printf("%sSorry, %s, players need unique names - maybe call yourself 'Funky %s'?%n%s", RESET, name, name, COLOUR_INPUT);
						
					} else {

						playersBuilder.add(new Player(name, STARTING_BALANCE, 0));
						names.add(name);
						resolved = true;
						System.out.printf("%sWelcome %s%s%s! You start the game with a balance of %s%s%,d%s%s.%n", RESET, COLOUR_PLAYER, playersBuilder.get(playerNum-1).getName(), RESET, COLOUR_RESOURCE, PRE, STARTING_BALANCE, SUF, RESET);
					
					}

				} catch (Exception e) {
					
					System.out.println(RESET+"Sorry, that wasn't a valid name."+COLOUR_INPUT);
					e.printStackTrace();
				}

			}

		}
		
		System.out.println(RESET+"Welcome, all, to SOLAROPOLY");
		
		return playersBuilder;
	}

	/**
	 * This method is called for each player in turn from the main method, and organizes 
	 * the inputs and results that will be a part of their turn, including 
	 * (1) deciding whether to abandon the game or roll and move;
	 * (2) developing any fields they have monopolized; and 
	 * (3) trading assets with other players
	 * @param player
	 */
	private static void turn(Player player) {

		boolean consent = consent(player);

		if (consent) {
			
			Board.visualMap();

			int roll = rollDice(player);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			player.move(roll);
		
		} else {
			
			playersInGame.remove(player);
			
			System.out.println(RESET+"You quit the game - all your properties will now be made available.");
			//TODO not handling a player leaving the game
			for (Square square : player.getOwnedSquares()) {
				((Area)square).removeOwnership(player);
			}
			
		}
		
		if (players.size() < 2 || playersInGame.size() < 2) {
			gameEndTrigger = true;
		}
	}

	/**
	 * This method is called before each turn to ask the player if they want to continue or leave the game.
	 * @param player - the player that needs to consent the turn
	 * @return boolean - the decision
	 */
	private static boolean consent(Player player) {
		player.displayBalance();
		String input = "";
		System.out.printf("%sIf you would like to take your turn, press Enter.%n"
				+ "Otherwise, type %sQuit%s and press Enter to leave the game. %n%s", RESET, COLOUR_OPTION, RESET, COLOUR_INPUT);
		
		while (true) {
			input = GameSystem.SCANNER.nextLine();
			
			if (input.equalsIgnoreCase("Quit") || input.equalsIgnoreCase("")) {
				break;
			} else {
				System.out.printf("%sWrong input - please either type %sQuit%s or press Enter to continue:%s", RESET, COLOUR_OPTION, RESET, COLOUR_INPUT);
			}
		}
		
		if (input.equals("")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * develop method
	 * 
	 * @param player
	 */
	public static void developArea(Player player) {
		boolean groupStatus = true;
		boolean areaStatus = true;
		ArrayList<Area> fullyDevelopedAreaStatus = new ArrayList<Area>();
		if (player.getOwnedGroups().size() > 0) {
			//TODO add a message for dont own full group
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
	
	/**
	 * rollDice method called from turn method. imitates 2 dice.
	 */
	private static int rollDice(Player player) {
		Die die1 = new Die(), die2 = new Die();

		int rollA = die1.roll();
		int rollB = die2.roll();
		
		int total = rollA+rollB;

		System.out.printf("%s%s%s, you've rolled a %d and a %d for %d total.%n", COLOUR_PLAYER, player.getName(), RESET, rollA, rollB, total);
		
		return total;
	}
	


}
