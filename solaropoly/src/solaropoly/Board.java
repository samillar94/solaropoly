/**
 * Solaropoly game
 */
package solaropoly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class represents the game board, which consists of a number of squares
 * and groups. The squares represent the locations on the board where players
 * can land and take actions. The groups represent the collections of squares
 * that players can buy and improve.
 * 
 * @author Roberto Lo Duca 40386172
 * 
 */
public class Board {

	/**
	 * Minimum number of squares from the requirements of the game.
	 */
	private static final int MIN_SQUARES = 6;

	/**
	 * Maximum number of squares from the requirements of the game.
	 */
	private static final int MAX_SQUARES = 20;

	/**
	 * Minimum number of groups from the requirements of the game.
	 */
	private static final int MIN_GROUPS = 2;

	/**
	 * Maximum number of groups from the requirements of the game.
	 */
	private static final int MAX_GROUPS = 4;

	private ArrayList<Square> squares = new ArrayList<Square>();
	private HashSet<Group> groups = new HashSet<Group>();

	/**
	 * Just a default constructor :D
	 */
	public Board() {
	}

	/**
	 * Constructor with arguments. It accept any array of squares or set of groups.
	 * 
	 * @param squares - The squares of the game board. It will be casted in an
	 *                ArrayList. Must contain between {@value #MIN_SQUARES} and
	 *                {@value #MAX_SQUARES} inclusive squares.
	 * @param groups  - The groups of the game board. It will be casted in a
	 *                HashSet. Duplicates will be ignored.
	 * @throws IllegalArgumentException - thrown if the rules of the game from the
	 *                                  requirements are not respected.
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
	 * The requirements are a board with a number of squares between
	 * {@value #MIN_SQUARES} and {@value #MAX_SQUARES} inclusive.
	 * 
	 * @param squares The squares of the game board. It will be casted in an
	 *                ArrayList.
	 * @throws IllegalArgumentException - respect the requirements, minimum and
	 *                                  maximum size.
	 */
	public void setSquares(List<Square> squares) throws IllegalArgumentException {
		int totalsize = squares.size();

		if (totalsize >= MIN_SQUARES && totalsize <= MAX_SQUARES) {
			this.squares = new ArrayList<>(squares);
		} else if (totalsize > MAX_SQUARES) {
			this.squares = new ArrayList<>(squares.subList(0, MAX_SQUARES));
			System.err.printf(
					"The List of squares was too long, and we need to comply with the rules.\n"
							+ "%d is the maximum number of squares allowed, but %d squares were provided.\n"
							+ "%d squares were truncated from the end of the List and the board.\n",
					MAX_SQUARES, totalsize, totalsize - MAX_SQUARES);
		} else if (totalsize < MIN_SQUARES) {
			throw new IllegalArgumentException(
					"The size of the list should be incremented. The minimum number of squares is " + MIN_SQUARES);
		}
	}

	/**
	 * @return the groups of the game board.
	 */
	public HashSet<Group> getGroups() {
		return groups;
	}

	/**
	 * The requirements are a board with a number of squares between
	 * {@value #MIN_GROUPS} and {@value #MAX_GROUPS} inclusive. Duplicates will be
	 * ignored.
	 * 
	 * @param The groups of the game board. It will be casted in a HashSet.
	 * @throws IllegalArgumentException  - respect the requirements, the logic of
	 *                                   the game and minimum and maximum size. The
	 *                                   board should contain only groups that are
	 *                                   present in the board and that are "Area"
	 *                                   objects (rule applied in the Group class).
	 * @throws IndexOutOfBoundsException - if the squares List is empty.
	 */
	public void setGroups(Set<Group> groups) throws IllegalArgumentException, IndexOutOfBoundsException {
		checkSquaresList();
		ArrayList<Area> areasGroups = new ArrayList<Area>();
		for (Group group : groups) {
			areasGroups.addAll(group.getAreas());
		}

		if (!this.squares.containsAll(areasGroups)) {
			throw new IllegalArgumentException(
					"The Set of groups has elements (squares of type Area) that are not present on the board.\n"
							+ "please correct the groups to match the areas/squares present in the board.");
		} else if (!areasGroups.containsAll(this.getSquaresByType(Area.class))) {
			System.err.printf(
					"The Set of groups does not contains all the elements (squares of type Area) that are present on the board.\n"
							+ "please be aware that not all the areas in this board have a group.");
		}

		int totalsize = groups.size();
		if (totalsize >= MIN_GROUPS && totalsize <= MAX_GROUPS) {
			this.groups = new HashSet<Group>(groups);
		} else if (totalsize > MAX_GROUPS) {
			throw new IllegalArgumentException(
					"The Set of groups was too long, and we need to comply with the rules.\n" + MAX_GROUPS
							+ "is the maximum number of groups allowed, but " + totalsize + " groups were provided.\n");
		} else if (totalsize < MIN_GROUPS) {
			throw new IllegalArgumentException(
					"The size of the Set should be incremented. The minimum number of groups is " + MIN_GROUPS);
		}
	}

	@Override
	public String toString() {
		return "Board [squares=" + this.getSize() + ", groups=" + this.groups.size() + "]";
	}

	// Methods

	/**
	 * This method returns the board size in squares.
	 * 
	 * @return the size of the game board, so the total number of squares that it
	 *         contains
	 */
	public int getSize() {
		return this.squares.size();
	}

	/**
	 * This method is used to retrieve from the board all the squares of a certain
	 * type.
	 * 
	 * @param type - the type of square to retrieve. use: NameClassSquare.class and
	 *             put instead of NameClassSquare the name of your class.
	 * @return the size of the game board, so the total number of squares that it
	 *         contains.
	 * @throws IndexOutOfBoundsException - if the squares List is empty.
	 */
	public <T> ArrayList<T> getSquaresByType(Class<T> type) throws IndexOutOfBoundsException {
		return this.getSquaresByType(this.squares, type);
	}

	/**
	 * This method is used to retrieve from a given List of squares all the squares
	 * of a certain type.
	 * 
	 * @param squares - The List to filter.
	 * @param type    - the type of square to retrieve. use: NameClassSquare.class
	 *                and put instead of NameClassSquare the name of your class.
	 * @return the size of the game board, so the total number of squares that it
	 *         contains.
	 * @throws IndexOutOfBoundsException - if the squares List is empty.
	 */
	public <T> ArrayList<T> getSquaresByType(List<Square> squares, Class<T> type) throws IndexOutOfBoundsException {
		checkSquaresList();
		ArrayList<T> result = new ArrayList<>();
		for (Square square : squares) {
			if (type.isInstance(square)) {
				result.add(type.cast(square));
			}
		}
		return result;
	}

	/**
	 * This method returns the square on the board in a given position.
	 * 
	 * @param index - the position in the board to retrieve.
	 * @return Square - the square in that position in the board.
	 * @throws IndexOutOfBoundsException - if there is an error with the calculation
	 *                                   of the index or if the squares List is
	 *                                   empty.
	 */
	public Square getSquare(int index) throws IndexOutOfBoundsException {
		int oldPosition = 0;
		return this.getBoardPosition(oldPosition, index).getSquare();
	}

	/**
	 * This method returns the square on the board, the number of times the player
	 * has passed the first square and his new position. It uses the player's
	 * current position and the dice roll result to calculate the new position.
	 * 
	 * @param oldPosition - the player's current position on the board. The dice
	 *                    roll result will be added to this value.
	 * @param diceRoll    - the result of the dice roll or a number to be added to
	 *                    the player's current position.
	 * @return BoardPosition - a container class called BoardPosition consisting of
	 *         a Square object of the new position, an integer representing the
	 *         number of times the player has looped around the board and an integer
	 *         with the new index position. The values can be accessed using the
	 *         getSquare() getStartPassed() and getPosition() methods from the
	 *         class.
	 * @throws IndexOutOfBoundsException - if there is an error with the calculation
	 *                                   of the index or if the squares List is
	 *                                   empty.
	 */
	public BoardPosition getBoardPosition(int oldPosition, int diceRoll) throws IndexOutOfBoundsException {
		checkSquaresList();
		oldPosition = (oldPosition < 0) ? 0 : oldPosition;
		int overrunPosition = oldPosition + diceRoll;
		int startPassed = 0;
		int newPosition;
		if (overrunPosition < 0) {
			newPosition = getSize() + (overrunPosition % getSize());
		} else {
			startPassed = (overrunPosition - 1) / getSize(); // -1 ensures landing on Sunrise doesn't count
			newPosition = overrunPosition % getSize();
		}
		Square newSquare = this.squares.get(newPosition);
		return new BoardPosition(newSquare, startPassed, newPosition);
	}

	/**
	 * This method return the position of a given Square.
	 * 
	 * @param square - the Square to check in order to determine the position.
	 * @return the number corresponding to the position of the Square.
	 * @throws IndexOutOfBoundsException - if the squares List is empty.
	 */
	public int getPosition(Square square) throws IndexOutOfBoundsException {
		checkSquaresList();
		return this.squares.indexOf(square);
	}

	/**
	 * This method return the Group of a particular Area
	 * 
	 * @param area - the Area to check in order to determine the Group.
	 * @return the Group object of the Area.
	 * @throws IndexOutOfBoundsException - if the groups Set is empty.
	 */
	public Group getGroup(Area area) throws IndexOutOfBoundsException {
		checkGroupsSet();
		for (Group group : this.groups) {
			if (group.getAreas().contains(area)) {
				return group;
			}
		}
		return null;
	}

	// Business rules

	/**
	 * Business rule to apply if the squares List is needed and not empty in a
	 * method
	 * 
	 * @throws IndexOutOfBoundsException - if the squares List is empty.
	 */
	private void checkSquaresList() throws IndexOutOfBoundsException {
		if (this.squares.isEmpty()) {
			throw new IndexOutOfBoundsException(
					"The List of squares is empty. Please fill the board with squares before using the method.");
		}
	}

	/**
	 * Business rule to apply if the groups Set is needed and not empty in a method
	 * 
	 * @throws IndexOutOfBoundsException - if the groups Set is empty.
	 */
	private void checkGroupsSet() throws IndexOutOfBoundsException {
		if (this.groups.isEmpty()) {
			throw new IndexOutOfBoundsException(
					"The Set of groups is empty. Please fill the board with groups before using the method.");
		}
	}

	/**
	 * This method give players visualized map
	 */
	public void visualMap() {
		// Map<Area,Integer> areaInfor=new HashMap<>();
		// for(int loop=2;loop<10;loop++) {
		// areaInfor.put((Area)GameSystem.board.getSquare(loop),0);
		// }
		System.out.println("\nHere is the board map - players move clockwise:");
		StringBuilder sb = new StringBuilder();
		ArrayList<Area> areas = new ArrayList<>();
		ArrayList<String> areaInfor = new ArrayList<>();
		for (int loop = 2; loop < 20; loop++) {
			try {
				Area aa = ((Area) GameSystem.board.getSquare(loop));
				areas.add(aa);
			} catch (Exception e) {
				continue;
			}
		}
		for (Area a : areas) {
			String addedInfor = null;
			if (a.getOwner() != null) {
				if (a.getOwner().equals(GameSystem.playersInGame.get(0))) {
					addedInfor = String.format("%s%-25s%d%s", ColourLibrary.CYAN, a.getName(), a.getDevelopmentLevel(),
							GameSystem.RESET);
				} else if (a.getOwner().equals(GameSystem.playersInGame.get(1))) {
					addedInfor = String.format("%s%-25s%d%s", ColourLibrary.BLUE, a.getName(), a.getDevelopmentLevel(),
							GameSystem.RESET);
				} else if (a.getOwner().equals(GameSystem.playersInGame.get(2))) {
					addedInfor = String.format("%s%-25s%d%s%s", ColourLibrary.GREEN, a.getName(), a.getDevelopmentLevel(),
							GameSystem.RESET);
				} else if (a.getOwner().equals(GameSystem.playersInGame.get(3))) {
					addedInfor = String.format("%s%-25s%d%10s%s", ColourLibrary.PURPLE, a.getName(),
							a.getDevelopmentLevel(),a.getOwner(),GameSystem.RESET);
				}
			} else {
				addedInfor = String.format("%s%-30s%d%s", ColourLibrary.WHITE_BOLD_BRIGHT, a.getName(),
						a.getDevelopmentLevel(), GameSystem.RESET);
			}
			areaInfor.add(addedInfor);
			;
		}
		sb.append(String.format("%20s%-29s", ColourLibrary.WHITE_BOLD_BRIGHT, "Sunrise")
				+ String.format("%-60s", areaInfor.get(0)) + String.format("%-60s", areaInfor.get(1))
				+ String.format("%s%-39s", ColourLibrary.WHITE_BOLD_BRIGHT, "Event"));
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%172s",areaInfor.get(2)));
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%40s", areaInfor.get(9)));
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%172s",areaInfor.get(3)));
		sb.append("\n");
		sb.append(String.format("%s%85s%s",ColourLibrary.YELLOW,"Welcome to Solaropoly",ColourLibrary.RESET));
		sb.append("\n");
		sb.append(String.format("%40s", areaInfor.get(8)));
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%172s",areaInfor.get(4)));
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%10s%-20s", ColourLibrary.WHITE_BOLD_BRIGHT, "Holiday")
				+ String.format("%-50s", areaInfor.get(7)) + String.format("%-50s", areaInfor.get(6))+ String.format("%-50s", areaInfor.get(5))
				+ String.format("%s%-39s%s", ColourLibrary.WHITE_BOLD_BRIGHT, "Penalty",ColourLibrary.RESET));
		
		System.out.println(sb);
		System.out.println();
	}
	public void textMap() {
		System.out.printf("%s%s%s%s%s\n\n","Position 1: ",ColourLibrary.YELLOW,"Sunrise ",ColourLibrary.RESET,"Type :Start Point");
		ArrayList<Area> areas = new ArrayList<>();
		for (int loop = 2; loop < 20; loop++) {
			try {
				Area aa = ((Area) GameSystem.board.getSquare(loop));
				areas.add(aa);
			} catch (Exception e) {
				continue;
			}
		}
		for(int loop=0;loop<2;loop++) {
			System.out.println("Position "+(loop+2)+": "+ColourLibrary.CYAN +areas.get(loop).getName()+ColourLibrary.RESET  +"\n Type:Area"+areas.get(loop).detailsArea());
		}
		System.out.printf("%s%s%s%s%s\n\n","Position 4: ",ColourLibrary.YELLOW,"Event ",ColourLibrary.RESET,"Type :Card");
		for(int loop=2;loop<5;loop++) {
			System.out.println("Position "+(loop+3)+": "+ColourLibrary.CYAN +areas.get(loop).getName()+ColourLibrary.RESET  +"\n Type:Area"+areas.get(loop).detailsArea());
		}
		System.out.printf("%s%s%s%s%s\n\n","Position 8: ",ColourLibrary.YELLOW,"Penalty ",ColourLibrary.RESET,"Type :Card");
		for(int loop=5;loop<8;loop++) {
			System.out.println("Position "+(loop+4)+": "+ColourLibrary.CYAN +areas.get(loop).getName()+ColourLibrary.RESET  +"\n Type:Area"+areas.get(loop).detailsArea());
		}
		System.out.printf("%s%s%s%s%s\n\n","Position 12: ",ColourLibrary.YELLOW,"Penalty ",ColourLibrary.RESET,"Type :Break square");
		for(int loop=8;loop<10;loop++) {
			System.out.println("Position "+(loop+5)+": "+ColourLibrary.CYAN +areas.get(loop).getName()+ColourLibrary.RESET  +"\n Type:Area"+areas.get(loop).detailsArea());
		}
	}
	
}
