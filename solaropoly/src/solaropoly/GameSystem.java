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
	/// set from csv
	public static int startingBalance;
	public static int productionTarget;
	public static int maxTurns;
	public static Player currentPlayer;
	/// set here
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final String RES_PRE = ""; /// resource unit prefix
	public static final String RES_SUF = " GET"; /// resource unit suffix
	public static final String OUT_PRE = ""; /// output unit prefix
	public static final String OUT_SUF = " MW"; /// resource unit suffix
	public static final char SEPARATOR_CHAR = '_'; // Separator to use in the getPlayerAttention to separate the action for each player

	/// design language colours
	public static final String RESET = ColourLibrary.RESET;
	public static final String COLOUR_PLAYER = ColourLibrary.RED_BOLD_BRIGHT;
	public static final String COLOUR_OTHERPLAYER = ColourLibrary.RED_BOLD;
	public static final String COLOUR_INPUT = ColourLibrary.YELLOW;
	public static final String COLOUR_OPTION = ColourLibrary.YELLOW_BRIGHT;
	public static final String COLOUR_RESOURCE = ColourLibrary.GREEN_BRIGHT;
	public static final String COLOUR_OUTPUT = ColourLibrary.CYAN_BRIGHT;
	public static final String COLOUR_LOCATION = ColourLibrary.CYAN_BOLD;
	
	/// essential components
	public static Board board = new Board();
	public static final Scanner SCANNER = new Scanner(System.in);
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static final String BOARD_FILE = "solaropoly-solar.csv";
	
	/**
	 * Stores only players that are still in game
	 */
	public static ArrayList<Player> playersInGame = new ArrayList<Player>();;

	
	
	/// Main thread

	/**
	 * Main method - execution starts here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			setupBoard();
			
			welcome();
			
			/// set number of players
			int numPlayers = setNumPlayers();
			
			/// register players
			players = registerPlayers(numPlayers);
			playersInGame.addAll(players);
			
			/// TODO Replace board map with list that can accommodate other than 12 squares
//			board.visualMap();
			
			/// game starts - cycling through players until game
			/// end triggered
			int firstPlayerIndex = 0;
			int turnsLeft = maxTurns;
			
			for (int playerIndex = firstPlayerIndex; !gameEndTrigger(playerIndex, turnsLeft); playerIndex = (playerIndex+1)%numPlayers) {
				
				Player player = players.get(playerIndex);

				if (playersInGame.contains(player)) {
					
					player.getAttention();
					Thread.sleep(1000);
					
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
				
				// grammar
				String isAre = "are";
				String s = "s";
				if (turnsLeft==2) {
					isAre = "is";
					s = "";
				}
				
				// turn end message
				System.out.printf("%sTurn ended. Total output stands at %s%s%,d%s%s. There %s %d turn%s left.%n"
						, RESET
						, COLOUR_OUTPUT, OUT_PRE, getTotalOutput(), OUT_SUF, RESET
						, isAre, --turnsLeft, s
						);

			}
			
			gameEnd(turnsLeft);


		} catch (InterruptedException e) {

			System.out.println("Sorry, the sleep was interrupted and the softwear engine crashed.");
			e.printStackTrace();

		} catch (Exception e) {

			System.out.println("Sorry, something went wrong and the softwear engine crashed.");
			e.printStackTrace();

		}

	}
	
	private static void gameEnd(int turnsLeft) {
		
		// pluralise turns correctly
		String s = "s";
		if (turnsLeft == 1) s = "";
		
		// draw line
		int consoleWidth = 80; // Default console width
        try {
            consoleWidth = Integer.parseInt(System.getenv("COLUMNS"));
        } catch (NumberFormatException e) {
            // Ignore the exception and use the default console width
        }
        System.out.println(String.valueOf(SEPARATOR_CHAR).repeat(consoleWidth)+"\n");
		
		// win or lose message
		if (getTotalOutput() >= productionTarget) {
			
			System.out.printf("CONGRATULATIONS!! The project reached its target output of %s%s%,d%s%s with "
					+ "%d turn%s to spare!%n%n"
					, COLOUR_OUTPUT, OUT_PRE, productionTarget, OUT_SUF, RESET
					, turnsLeft, s
					);
			System.out.printf("%s%s%s, you generated the %dth megawatt, and as thanks you receive a "
					+ "beautifully sculpted commemorative ScamCoin made from one tonne of recycled plastic recovered "
					+ "from the Pacific Ocean. Wear it with pride.%n%n"
					, COLOUR_PLAYER, currentPlayer.getName(), RESET
					, productionTarget
					);
			
		} else {
			
			System.out.printf("Unfortunately, you've all run out of time and not met the target combined output of %s%s%,d%s%s.%n%n"
					, COLOUR_OUTPUT, OUT_PRE, productionTarget, OUT_SUF, RESET
					);
			
		}

		/// final scores
		System.out.println("Assets accumulated by each player:");

		/// TODO put this into a method so it can also be called when players leave
		/// early
		for (Player player : playersInGame) {

			int propertyValue = 0;

			for (Square area : player.getOwnedSquares()) {
				propertyValue += ((Area) area).getCost();
			}

			System.out.printf(
					"%s%s%s has %s%s%,d%s%s and owns %s%s%,d%s%s of assets %s.%nThe company is valued at %s%s%,d%s%s "
					+ "and is generating %s%s%,d%s%s of power.%n%n"
					, COLOUR_PLAYER, player.getName(), RESET
					, COLOUR_RESOURCE, RES_PRE, player.getBalance(), RES_SUF, RESET
					, COLOUR_RESOURCE, RES_PRE, propertyValue, RES_SUF, RESET, player.getOwnedSquares().toString()
					, COLOUR_RESOURCE, RES_PRE, player.getBalance() + propertyValue, RES_SUF, RESET
					, COLOUR_OUTPUT, OUT_PRE, player.getOutput(), OUT_SUF, RESET
					);

		}

		System.out.println("Thank you for playing SOLAROPOLY!");

		
	}

	/**
	 * Welcome message setting the scene for the game
	 */
	private static void welcome() {
		
		/// find max output
		int maxOutput = 0;
		for (Square square : board.getSquares()) {
			if (square instanceof Area) {
				maxOutput += ((Area) square).getMaxOutput();
			}
		}
		
		System.out.println(ColourLibrary.WHITE_BOLD+"\n    Welcome to SOLAROPOLY!    \n\n"+RESET
				+"In this game, you'll each take the role of a solar energy startup competing for "
				+"space to set up your solar farms, factories and grids across the globe. \n\n"
				+"Starting the game with "
				+COLOUR_RESOURCE+ RES_PRE+ String.format("%,d",startingBalance)+ RES_SUF+ RESET
				+" (Green Energy Tokens) each, provided by the United Nations for the project, "
				+"the goal is to maximise energy production among all companies and reach the target of "
				+COLOUR_OUTPUT+ OUT_PRE+ String.format("%,d",productionTarget)+ OUT_SUF+ RESET
				+" within "+maxTurns+" turns - so compete and collaborate wisely.\n\n"
				+"But also, the player whose power increase tips the total energy capture over the target "
				+"recieves a legendary commemorative ScamCoin! \n\n"
				+"You can increase power output by buying areas with basic facilities, completing sets of complementary facilities in regions (vertical integration of operations), and developing those completed sets. You'll be able to trade with other players to complete sets, start development and increase your individual and collective chances of hitting the target."
				+ "The theoretical maximum output for this board is "
				+COLOUR_OUTPUT+ OUT_PRE+ String.format("%,d",maxOutput)+ OUT_SUF+ RESET
				+".\n"
				);
		
		
	}
	
	/**
	* Reads game, square and group parameters from csv.
	* The csv should include Group data before Square data.
	* The read order of Game parameters and Cards doesn't matter.
	*/
	private static void setupBoard() {

		ArrayList<Square> squares = new ArrayList<Square>(14);
		ArrayList<Group> groups = new ArrayList<Group>(4);
		ArrayList<Card> cards = new ArrayList<Card>();
	
		File file = new File(BOARD_FILE);
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			String line;
			br.readLine();
			line = br.readLine();
			int counter = 1;
						
			while(line!=null) {
			
				String[] data = line.split(",");
	
				// switch on datatype
				switch (data[0]) {
				case "Game":
					
					switch (data[3]) {
					
					case "startingBalance": 
						
						startingBalance = Integer.parseInt(data[4]);
						break;
						
					case "productionTarget":
						
						productionTarget = Integer.parseInt(data[4]);
						break;
						
					case "maxTurns":
						
						maxTurns = Integer.parseInt(data[4]);
						break;
						
					default:
						
						System.err.println("Line "+counter+" (Game) in board setup csv skipped due to invalid \"name\" value");
					
					}
					break;
					
				case "Group":
					
					String groupName = data[3];
					int minorDevCost = Integer.parseInt(data[12]);
					int majorDevCost = Integer.parseInt(data[13]);
					int monopolyOutput = Integer.parseInt(data[14]);
					int minorDevOutput = Integer.parseInt(data[15]);
					int majorDevOutput = Integer.parseInt(data[16]);
					
					groups.add(new Group(groupName, minorDevCost, majorDevCost, monopolyOutput, minorDevOutput, majorDevOutput));
					break;
					
				case "Square":
					
					switch (data[2]) {
					
					case "Area":
						
						String areaName = data[3];
						int cost = Integer.parseInt(data[4]);
						int baseRent = Integer.parseInt(data[5]);
						int oneDev = Integer.parseInt(data[6]);
						int twoDev = Integer.parseInt(data[7]);
						int threeDev = Integer.parseInt(data[8]);
						int majorDev = Integer.parseInt(data[9]);
						int baseOutput = Integer.parseInt(data[10]);
						int groupIndex = Integer.parseInt(data[11]);
						
						int[] monopolyProfile = {baseRent, baseRent*2};
						int[] developmentProfile = {baseRent, oneDev, twoDev, threeDev, majorDev};
						
						Area area = new Area(areaName, groups.get(groupIndex), cost, monopolyProfile, developmentProfile, baseOutput);
						squares.add(area);
						break;
						
					case "Sunrise":
						
						squares.add(new Sunrise(data[3], Integer.parseInt(data[4])));
						break;
						
					case "Holiday":
						
						squares.add(new Holiday(data[3]));
						break;	
						
					case "Failure":
						
						squares.add(new Failure(data[3], Integer.parseInt(data[4])));
						break;	
						
					case "Event":
						
						// TODO add cards
						squares.add(new Event(data[3], null));
						break;	
						
					default:
						
						System.err.println("Line "+counter+" (Square) in board setup csv skipped due to invalid \"type\" value");
					
					}
					
					break;
				
				case "Card":
					
					String eventText = data[3];
					int move = Integer.parseInt(data[17]);
					int earn = Integer.parseInt(data[18]);
					int turns = Integer.parseInt(data[19]);

					cards.add(new Card(eventText, move, earn, turns));
					
					break;
					
				default: 
					
					System.err.println("Line "+counter+" in board setup csv skipped due to invalid \"data\" value");
					
				}
				
				line = br.readLine();	
				counter++;
				
			}
			
			board.setSquares(squares);
			
			for (Group group : groups) {
				group.setAreas(squares);
			}
			
			board.setGroups(new HashSet<Group>(groups));
			
			for (Square square : board.getSquares()) {
				if (square instanceof Event) {
					((Event) square).addCards(cards);
				} 
			}
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File not found - check BOARD_FILE in Game.java");
			e.printStackTrace();
			
		} catch (IOException e) {
			
			System.out.println("Input exception - check csv file for issues");
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
				
				System.out.println(RESET + "Sorry, try again." + COLOUR_INPUT);
				
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

						System.out.printf(
								"%sSorry, %s, players need unique names - maybe call yourself 'Funky %s'?%n%s", RESET,
								name, name, COLOUR_INPUT);

					} else {

						playersBuilder.add(new Player(name, startingBalance, 0));
						names.add(name);
						resolved = true;
						System.out.printf("%sWelcome %s%s%s! You start the game with a balance of %s%s%,d%s%s.%n", RESET, COLOUR_PLAYER, playersBuilder.get(playerNum-1).getName(), RESET, COLOUR_RESOURCE, RES_PRE, startingBalance, RES_SUF, RESET);
					
					}

				} catch (Exception e) {

					System.out.println(RESET + "Sorry, that wasn't a valid name." + COLOUR_INPUT);
					e.printStackTrace();
				}

			}

		}
		
		System.out.println(RESET+"\nRight everyone, let's go catch some rays!");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		return playersBuilder;
	}

	/**
	 * This method is called for each player in turn from the main method, and
	 * organizes the inputs and results that will be a part of their turn, including
	 * (1) deciding whether to abandon the game or roll and move; (2) developing any
	 * fields they have monopolized; and (3) trading assets with other players
	 * 
	 * @param player
	 */
	private static void turn(Player player) {

		boolean consent = consent(player);

		if (consent) {
			
			// TODO Replace map with list that can accommodate other than 12 Squares
//			board.visualMap();

			int roll = rollDice(player);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			player.move(roll);
		
		} else {

			playersInGame.remove(player);

			System.out.println(RESET + "You quit the game - all your properties will now be made available.");
			// TODO not handling a player leaving the game
			for (Square square : player.getOwnedSquares()) {
				((Area) square).removeOwnership(player);
			}

		}
		
	}

	/**
	 * This method is called before each turn to ask the player if they want to
	 * continue or leave the game.
	 * 
	 * @param player - the player that needs to consent the turn
	 * @return boolean - the decision
	 */
	private static boolean consent(Player player) {
		player.displayBalance();
		String input = "";
		System.out.printf(
				"%sIf you would like to take your turn, press Enter.%n"
						+ "Otherwise, type %sQuit%s and press Enter to leave the game. %n%s",
				RESET, COLOUR_OPTION, RESET, COLOUR_INPUT);

		while (true) {
			input = GameSystem.SCANNER.nextLine();

			if (input.equalsIgnoreCase("Quit") || input.equalsIgnoreCase("")) {
				break;
			} else {
				System.out.printf("%sWrong input - please either type %sQuit%s or press Enter to continue:%s", RESET,
						COLOUR_OPTION, RESET, COLOUR_INPUT);
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
		try{
		if (player.getOwnedGroups().size() > 0) {

			do {
				displayMenu(player);
				System.out.println();
				System.out.println(
						"If you wish to develop within a completed set please enter the set name, else, to skip just press Enter");
				String inputGroup = SCANNER.nextLine();

				if (inputGroup == null || inputGroup == "") {
					System.out.println("System recognises you do not wish to develop");
					groupStatus = false;
				} else {

					for (Group group : player.getOwnedGroups()) {
						try{
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
										try{
										if (square.getName().equalsIgnoreCase(inputArea)) {

											Area area;
											try{
											if (square instanceof Area) {
												area = (Area) square;
												try {
													if (group.canAreaBeDeveloped(area, group)) {

														if (area.getDevelopmentLevel() < 2) {
															try {
																if (player.getBalance() >= area.getGroup()
																		.getMinorDevCost()) {
																	area.setDevelopmentLevel();
																	System.out.println(area.getName() + " level: "
																			+ area.getDevelopmentLevel());
																}
															} catch (Exception e) {
																System.out.println("Insufficient funds");
															}

														} else if (area.getDevelopmentLevel() == 2) {
															try {
																if (player.getBalance() >= area.getGroup()
																		.getMajorDevCost()) {
																	area.setDevelopmentLevel();
																	System.out.println("Major development achieved."
																			+ area.getName() + " developed "
																			+ area.getDevelopmentLevel() + " times");
																}
															} catch (Exception e) {
																System.out.println("Insufficient funds");
															}

														} else {
															throw new IllegalArgumentException(
																	"Error: not able to develop area.");

														}

													}
												} catch (Exception e) {
													System.out.println("Areas must be developed equally");
												}

											}
										 	} catch (Exception e) {
												System.out.println("Please enter a square that is an area");
											}

										}
										}catch (Exception e) {
											System.out.println("Please enter an area that you own");
										}
										 
									}
									// condition for all areas fully developed
									do {
										for (Square square : player.getOwnedSquares()) {
											Area area;

											if (square instanceof Area) {
												area = (Area) square;
												if (area.getDevelopmentLevel() == 3) {
													fullyDevelopedAreaStatus.add(area);
												}
											}
										}
									} while (fullyDevelopedAreaStatus.size() != player.getOwnedSquares().size());

									if (fullyDevelopedAreaStatus.size() == player.getOwnedSquares().size()) {
										areaStatus = false;
										System.out.println("All areas owned are fully developed.");
									}

								}

							} while (areaStatus != false);

						} 
						}catch (Exception e) {
							System.out.println("Please enter a set that you own");
						}
					}
				}

			} while (groupStatus != false);
		}
	 	} catch (Exception e) {
			System.out.println("You do not own any sets");
		}
	}

	/**
	 * generates the menu using the getmenuitems method
	 * 
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
	 * generates a each row for each property owned by each player during
	 * development to see their owned properties and associated costs with
	 * development
	 * 
	 * @param player
	 * @return
	 */
	public static List<MenuItem> getMenuItems(Player player) {

		List<MenuItem> menuItems = new ArrayList<>();

		int playerBalance = player.getBalance();

		for (Group group : player.getOwnedGroups()) {

			for (Square square : player.getOwnedSquares()) {

				if (square instanceof Area) {
					Area area = (Area) square;
					String groupName = group.getName();
					String squareName = square.getName();
					String developmentLevel = "";
					int developmentCost = 0; // if costs are in an array, can iterate through them for each cost

					if (player.getOwnedSquares().contains(area)) {
						developmentLevel = Integer.toString(area.getDevelopmentLevel());

						if (area.getDevelopmentLevel() < 2) {
							developmentCost = area.getGroup().getMinorDevCost();
						} else if (area.getDevelopmentLevel() == 2) {
							developmentCost = area.getGroup().getMajorDevCost();
						}
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
	 * @author andrewscott class to store the menu item objects
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
	
	/**
	 * Game end trigger - called before every turn
	 * @return
	 */
	public static boolean gameEndTrigger(int playerIndex, int turnsLeft) {
		
		// trigger game end if only one player
		if (players.size() < 2 || playersInGame.size() < 2) return true;
		
		// trigger game end if turns run out
		if (turnsLeft<1) return true;
		
		// trigger game end if productionTarget reached
		currentPlayer = players.get(playerIndex);
		return (getTotalOutput() >= productionTarget);
		
	}
	
	/**
	 * Finds the total project output using area.getCurrentOutput();
	 * @return
	 */
	public static int getTotalOutput() {
		int totalOutput = 0;
		for (Square square : board.getSquares()) 
			if (square instanceof Area) 
				totalOutput += ((Area) square).getCurrentOutput();
		return totalOutput;
	}

}
