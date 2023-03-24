/**
 * Solaropoly game
 */
package solaropoly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class represents the game board, which consists of a number of squares and groups.
 * The squares represent the locations on the board where players can land and take actions.
 * The groups represent the collections of squares that players can buy and improve.
 * 
 * @author Roberto Lo Duca 40386172
 * 
 */
public class Board {
	
	/**
	 * Maximum number of squares from the requirements of the game.
	 */
	private static final int MAX_SQUARES = 20;
	
	/**
	 * Minimum number of squares from the requirements of the game.
	 */
	private static final int MIN_SQUARES = 6;
	
	/**
	 * Maximum number of groups from the requirements of the game.
	 */
	private static final int MAX_GROUPS = 4;
	
	/**
	 * Minimum number of groups from the requirements of the game.
	 */
	private static final int MIN_GROUPS = 2;
	
	private ArrayList<Square> squares = new ArrayList<Square>();
	private HashSet<Group> groups = new HashSet<Group>();
	
	/**
	 * Just a default constructor :D
	 */
	public Board() {}
	
	/**
	 * Constructor with arguments. It accept any array of squares or set of groups.
	 * @param squares - The squares of the game board. It will be casted in an ArrayList.
	 * 					Must contain between {@value #MIN_SQUARES} and {@value #MAX_SQUARES} inclusive squares.
	 * @param groups - The groups of the game board. It will be casted in a HashSet. Duplicates will be ignored.
	 * @throws IllegalArgumentException - thrown if the rules of the game from the requirements are not respected.
	 */
	public Board(List<Square> squares, Set<Group> groups) throws IllegalArgumentException {
		this.setSquares(squares);
		this.setGroups(groups);
	}
	
	/**
	 * @return the squares of the game board.
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}
	
	/**
	 * The requirements are a board with a number of squares between {@value #MIN_SQUARES} and {@value #MAX_SQUARES} inclusive.
	 * @param squares The squares of the game board. It will be casted in an ArrayList.
	 * @throws IllegalArgumentException - respect the requirements, minimum and maximum size.
	 */
	public void setSquares(List<Square> squares) throws IllegalArgumentException {
		int totalsize = this.getSize() + squares.size();
		
		if (totalsize >= MIN_SQUARES && totalsize <= MAX_SQUARES) {
			this.squares = (ArrayList<Square>) squares;
		} else if (totalsize > MAX_SQUARES) {
			this.squares = (ArrayList<Square>) squares.subList(MAX_SQUARES + 1, totalsize);
			System.err.printf("The List of squares was too long, and we need to comply with the rules.\n"
					+ "%d is the maximum number of squares allowed, but %d squares were provided.\n"
					+ "%d squares were truncated from the end of the List and the board.\n",
					MAX_SQUARES, totalsize, totalsize - MAX_SQUARES);
		} else if (totalsize < MIN_SQUARES) {
			throw new IllegalArgumentException("The size of the list should be incremented. The minimum number of squares is " + MIN_SQUARES);
		}
	}
	
	/**
	 * @return the groups of the game board.
	 */
	public HashSet<Group> getGroups() {
		return groups;
	}
	
	/**
	 * The requirements are a board with a number of squares between {@value #MIN_GROUPS} and {@value #MAX_GROUPS} inclusive. Duplicates will be ignored.
	 * @param The groups of the game board. It will be casted in a HashSet.
	 * @throws IllegalArgumentException - respect the requirements, the logic of the game and minimum and maximum size.
	 * 									  The board should contain only groups that are present in the board
	 * 									  and that are "Area" objects (rule applied in the Group class).
	 */
	public void setGroups(Set<Group> groups) throws IllegalArgumentException {
		ArrayList<Area> areas = new ArrayList<Area>();
		
		for (Group group : groups) {
			areas.addAll(group.getAreas());
		}
		if (!squares.containsAll(areas)) {
			throw new IllegalArgumentException("The Set of groups has elements (squares of type area) that are not present on the board.\n"
					+ "please correct the groups to match the areas/squares present in the boards.");
		}
		
		int totalsize = this.getGroups().size() + groups.size();
		
		if (totalsize >= MIN_GROUPS && totalsize <= MAX_GROUPS) {
			this.groups = (HashSet<Group>) groups;
		} else if (totalsize > MAX_GROUPS) {
			throw new IllegalArgumentException("The Set of groups was too long, and we need to comply with the rules.\n"
					+ MAX_GROUPS + "is the maximum number of groups allowed, but " + totalsize + " groups were provided.\n");
		} else if (totalsize < MIN_GROUPS) {
			throw new IllegalArgumentException("The size of the Set should be incremented. The minimum number of groups is " + MIN_GROUPS);
		}
	}
	
	@Override
	public String toString() {
		return "Board [squares=" + this.getSize() + ", groups=" + groups.size() + "]";
	}
	
	/**
	 * Board size in squares.
	 * @return the size of the game board, so the total number of squares that it contains
	 */
	public int getSize() {
		return this.squares.size();
	}
	
	
	//TODO: I still need to implement the new methods that I added in the class diagram:
	
	public BoardPosition getSquare(int diceRoll) throws IndexOutOfBoundsException {
		int oldPosition = 0;
		return this.getSquare(oldPosition, diceRoll);
	}
	
	public BoardPosition getSquare(int oldPosition, int diceRoll) throws IndexOutOfBoundsException {
		if (!this.squares.isEmpty()) {
			oldPosition = (oldPosition < 0) ? oldPosition : 0;
			diceRoll = (diceRoll < 0) ? diceRoll : 0;
			int overrunPosition = oldPosition + diceRoll;
	        int startPassed = overrunPosition / getSize();
	        int newPosition = overrunPosition % getSize();
			Square square = this.squares.get(newPosition);
			return new BoardPosition(square, startPassed);
		} else {
			throw new IndexOutOfBoundsException("The List of squares is empty. Please fill the board with squares before using the method.");
		}
	}
	
	public int getPosition(Square square) {
		
		return 0;
	}
	
	public Group getGroup(Square square) {
		
		return null;
	}
}
