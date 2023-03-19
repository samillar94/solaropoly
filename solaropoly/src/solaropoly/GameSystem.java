/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author samil
 *
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

	private static boolean gameEndTrigger = false;

	public static ArrayList<Player> players = new ArrayList<Player>();

	/**
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

	private static int setNumPlayers() {

		int num = 0;
		/// TODO scanner code
		
		
		
		players = new ArrayList<Player>(num);
		return num;

	}

	private static ArrayList<Player> registerPlayers() {
		/// TODO scanner
		
		
		ArrayList<Player> playersBuilder = new ArrayList<Player>();
		return playersBuilder;
	}

	private static void turn(Player player) {
		/// TODO do you want to take the turn?
		/// TODO roll dice
		/// TODO move on board and activate square
		/// TODO smash or pass square
		/// TODO develop if you can

	}

}
