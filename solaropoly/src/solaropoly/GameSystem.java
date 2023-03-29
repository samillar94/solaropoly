/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The executable class of the system, starting the Solaropoly command-line game.
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
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE


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

			/// game starts - do while (and try-catch?), cycling through players until game
			/// end triggered
			while (!gameEndTrigger) {

				for (Player player : players) {
					if (playersInGame.contains(player)) {
						
						Thread.sleep(2000); 
						
						turn(player);
					}
					if (gameEndTrigger)	break;
				}

			}
			
			/// game ending
			System.out.println("\nGAME OVER!\nAssets accumulated:");
			
			/// TODO put this into a method so it can also be called when players leave early 
			for (Player player : players) {
				
				int propertyValue = 0;
				
				for (Square area : player.getOwnedSquares()) {
					propertyValue += ((Area) area).getCost();
				}
				
				System.out.printf("%s%s%s has £%,d cash and owns £%,d of assets %s for a total of £%,d.%n", 
						RED_BRIGHT, player.getName(), RESET, 
						player.getBalance(), 
						propertyValue, 
						player.getOwnedSquares().toString(), 
						player.getBalance()+propertyValue);
				
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
		squares.add(new Parking("The Sea"));
		
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
	 * Takes user input at the start of the game to set the number of players whose names
	 * will be registered.
	 * @return the number of players
	 */
	private static int setNumPlayers() {

		int num = 0;
		
		while (num == 0) {
			System.out.println("How many are playing? Type a number between "+MIN_PLAYERS
				+" and "+MAX_PLAYERS+" and press Enter.");
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
						
						System.out.printf("Sorry, %s, players need unique names - maybe call yourself 'Funky %s'?%n", name, name);
						
					} else {
					
						playersBuilder.add(new Player(name, STARTING_BALANCE, 0));
						names.add(name);
						resolved = true;
						System.out.printf("Welcome %s%s%s! You start the game with a balance of %s%,d%s.%n", RED_BRIGHT, playersBuilder.get(playerNum-1).getName(), RESET, PRE, STARTING_BALANCE, SUF);
					
					}
					
				} catch (Exception e) {
					
					System.out.println("Sorry, that wasn't a valid name.");
					e.printStackTrace();
				}
				
			}
			
		}
		
		System.out.println("Welcome, all, to SOLAROPOLY");
		
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
			
			player.move(rollDice(player));
			
		/// TODO	
//			develop(player);
//			trade(player);
		
		} else {
			
			playersInGame.remove(player);
			
			System.out.println("You quit the game - all your properties will now be made available.");
			
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
		player.getAttention();
		player.displayBalance();
		String input = "";
		System.out.printf("If you would like to take your turn, press Enter.%n"
				+ "Otherwise, type Quit and press Enter to leave the game. ", player.getName());
		
		while (true) {
			input = GameSystem.SCANNER.nextLine();
			
			if (input.equalsIgnoreCase("Quit") || input.equalsIgnoreCase("")) {
				break;
			} else {
				System.out.println("Wrong imput. please choose Quit (case is ignored) or just press Enter to continue");
			}
		}
		
		if (input.equals("")) {
			return true;
		} else {
			return false;
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

		System.out.printf("%n%s, you've rolled a %d and a %d for %d total, landing you in position %d.%n", player.getName(), rollA, rollB, total, player.getPosition()+total);
		
		return total;
	}
	

}
