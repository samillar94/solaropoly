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

	// game parameter constants
	
	public static final int STARTING_BALANCE = 15000000;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final String CURRENCY_SYMBOL = "kWh";
	
	// essential components
	
	public static final Scanner SCANNER = new Scanner(System.in);

	private static boolean gameEndTrigger = false;

	public static ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		// set number of players
		setNumPlayers();
		
		// register players
		players = registerPlayers();
		//int playersTotal = players.size();
		
		// game starts - do while, cycling through players
		
	}
	
	private static int setNumPlayers() {
		
		int num = 0;
		// TODO scanner code
		players = new ArrayList<Player>(num);
		return num;
		
	}
	
	private static ArrayList<Player> registerPlayers() {
		// TODO scanner fo
		ArrayList<Player> playerBuilder = new ArrayList<Player>();
		return playerBuilder;
	}
}
