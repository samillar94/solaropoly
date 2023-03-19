/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The executable class of the system, starting the Solaropoly command-line game.
 * @author G17
 */
public class GameSystem {

	/// game parameter constants

	public static final int STARTING_BALANCE = 15000000;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final String PRE = ""; /// resource prefix
	public static final String SUF = " kWh"; /// resource suffix

	/// essential components

	public static final Scanner SCANNER = new Scanner(System.in);

	public static boolean gameEndTrigger = false;

	public static ArrayList<Player> players = new ArrayList<Player>();

	
	/// executable code
	
	/**
	 * Main method - execution starts here
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			/// set number of players
			setNumPlayers();

			/// register players
			players = registerPlayers();

			/// game starts - do while (and try-catch?), cycling through players until game
			/// end triggered
			while (!gameEndTrigger) {

				for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {

					Thread.sleep(2000);

					turn(players.get(playerIndex));

					if (gameEndTrigger)	break;

				}

			}
			
			/// TODO final code to show balances and assets
			

		} catch (InterruptedException e) {

			System.out.println("Sorry, the sleep was interrupted and the softwear engine crashed.");
			e.printStackTrace();

		} catch (Exception e) {

			System.out.println("Sorry, something went wrong and the softwear engine crashed.");
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
	private static ArrayList<Player> registerPlayers() {
		ArrayList<Player> playersBuilder = new ArrayList<Player>();
		/// TODO scanner stuff
		
		
		return playersBuilder;
	}

	/**
	 * This method is called for each player in turn from the main method, and organises 
	 * the inputs and results that will be a part of their turn, including 
	 * (1) deciding whether to abandon the game or roll and move; 
	 * (2) developing any fields they have monopolised; and 
	 * (3) trading assets with other players
	 * @param player
	 */
	private static void turn(Player player) {
		/// TODO do you want to take the turn?
		/// TODO roll dice
		/// TODO move on board and activate square
		/// TODO develop if you can
		/// TODO trade assets 

	}

}
