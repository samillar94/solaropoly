/**
 * 
 */
package solaropoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Roberto Lo Duca 40386172
 *
 */
class BoardTest {
	
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
	
	// add the areas for the boundaries
	ArrayList<Area> areasMidValid = new ArrayList<>();
	ArrayList<Area> areasMaxValid = new ArrayList<>();
	ArrayList<Area> areasMaxInvalid = new ArrayList<>();
	
	// areas of the group
	ArrayList<Area> commonAreas = new ArrayList<>();
	ArrayList<Area> notCommonAreas = new ArrayList<>();
	
	// test the boundaries of the possible lengths of the squares List, the relative error message, exceptions and the list type.
	LinkedList<Square> squaresLowInvalid = new LinkedList<Square>();
	ArrayList<Square> squaresLowValid = new ArrayList<Square>();
	ArrayList<Square> squaresMidValid = new ArrayList<Square>();
	LinkedList<Square> squaresMaxValid = new LinkedList<Square>();// use this one to test the getSquareByType method
	ArrayList<Square> squaresMaxInvalid = new ArrayList<Square>();
	
	// test the boundaries of the possible lengths of the groups Set, exceptions and the Set type
	HashSet<Group> groupsLowInvalid = new HashSet<Group>();
	HashSet<Group> groupsLowValid = new HashSet<Group>();
	HashSet<Group> groupsMidValid = new HashSet<Group>();
	HashSet<Group> groupsMaxValid = new HashSet<Group>();
	HashSet<Group> groupsMaxInvalid = new HashSet<Group>();
	
	// test the Set groups, the relative error message if groups have missing Areas that are indeed in the board and the Set type
	HashSet<Group> groupsMissingAreasValid = new HashSet<Group>();
	
	// test the Set groups, the relative exception if groups have Areas that are not present in the board and the Set type
	HashSet<Group> groupsWrongAreasInvalid = new HashSet<Group>();
	
	// test the groups areas
	Group groupCommonAreas = new Group();
	Group groupNotCommonAreas = new Group();
	
	Board board;
	Sunrise sunrise;
	Failure failure;
	Holiday holiday;
	Event event;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		
		// squares
		sunrise = new Sunrise();
		failure = new Failure();
		holiday = new Holiday();
		event = new Event();
		
		// a common areas of the board to test the group
		int numberOfAreasGroup = 2; // the minimum number of areas for group
		for (int i = 0; i < numberOfAreasGroup; i++) {
			commonAreas.add(new Area());
		}
		
		// not common areas of the board to test the group
		for (int i = 0; i < numberOfAreasGroup; i++) {
			notCommonAreas.add(new Area());
		}
		
		// there are 4 additional squares + 2 common areas, so - 6 should be added
		int numberOfAreasMidValid = 13 - 6;
		int numberOfAreasMaxValid = 20 - 6;
		int numberOfAreasMaxInvalid = 21 - 6;
		
		for (int i = 0; i < numberOfAreasMidValid; i++) {
			areasMidValid.add(new Area());
		}
		for (int i = 0; i < numberOfAreasMaxValid; i++) {
			areasMaxValid.add(new Area());
		}
		for (int i = 0; i < numberOfAreasMaxInvalid; i++) {
			areasMaxInvalid.add(new Area());
		}
		
		// 5 squares in total
		squaresLowInvalid.add(sunrise);
		squaresLowInvalid.add(failure);
		squaresLowInvalid.add(holiday);
		squaresLowInvalid.add(event);
		squaresLowInvalid.add(new Area());
		
		// 6 squares in total
		squaresLowValid.add(sunrise);
		squaresLowValid.add(failure);
		squaresLowValid.add(holiday);
		squaresLowValid.add(event);
		squaresLowValid.addAll(commonAreas);
		
		// 13 squares in total
		squaresMidValid.add(sunrise);
		squaresMidValid.add(failure);
		squaresMidValid.add(holiday);
		squaresMidValid.add(event);
		squaresMidValid.addAll(commonAreas);
		squaresMidValid.addAll(areasMidValid);
		
		// 20 squares in total
		squaresMaxValid.add(sunrise);
		squaresMaxValid.add(failure);
		squaresMaxValid.add(holiday);
		squaresMaxValid.add(event);
		squaresMaxValid.addAll(commonAreas);
		squaresMaxValid.addAll(areasMaxValid);
		
		// 21 squares in total
		squaresMaxInvalid.add(sunrise);
		squaresMaxInvalid.add(failure);
		squaresMaxInvalid.add(holiday);
		squaresMaxInvalid.add(event);
		squaresMaxInvalid.addAll(commonAreas);
		squaresMaxInvalid.addAll(areasMaxInvalid);
		
		// groups
		int numberOfGroupsLowInvalid = 1;
		int numberOfGroupsLowValid = 2;
		int numberOfGroupsMidValid = 3;
		int numberOfGroupsMaxValid = 4;
		int numberOfGroupsMaxInvalid = 5;
		
		// empty groups for testing purposes only
		for (int i = 0; i < numberOfGroupsLowInvalid; i++) {
			groupsLowInvalid.add(new Group());
		}
		for (int i = 0; i < numberOfGroupsLowValid; i++) {
			groupsLowValid.add(new Group());
		}
		for (int i = 0; i < numberOfGroupsMidValid; i++) {
			groupsMidValid.add(new Group());
		}
		for (int i = 0; i < numberOfGroupsMaxValid; i++) {
			groupsMaxValid.add(new Group());
		}
		for (int i = 0; i < numberOfGroupsMaxInvalid; i++) {
			groupsMaxInvalid.add(new Group());
		}
		
		// check group areas matching with the squares on the board
		groupCommonAreas.setAreas(commonAreas);
		groupNotCommonAreas.setAreas(notCommonAreas);
		
		groupsMissingAreasValid.add(groupCommonAreas);
		groupsMissingAreasValid.add(new Group());
		
		groupsWrongAreasInvalid.add(groupCommonAreas);
		groupsWrongAreasInvalid.add(groupNotCommonAreas);
	}

	/**
	 * Test method for {@link solaropoly.Board#Board()}.
	 */
	@Test
	void testBoardDefaultConstructor() {
		board = new Board();
		assertNotNull(board);
	}

	/**
	 * Test method for {@link solaropoly.Board#Board(java.util.List, java.util.Set)}.
	 */
	@Test
	void testBoardConstructorWithArgumentsValid() {
		board = new Board(squaresLowValid, groupsLowValid);
		assertEquals(squaresLowValid, board.getSquares());
		assertEquals(groupsLowValid, board.getGroups());
	}
	
	/**
	 * Test method for {@link solaropoly.Board#Board(java.util.List, java.util.Set)}.
	 */
	@Test
	void testBoardConstructorWithArgumentsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			board = new Board(squaresLowInvalid, groupsLowValid);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			board = new Board(squaresLowValid, groupsLowInvalid);
		});
	}

	/**
	 * Test method for {@link solaropoly.Board#setSquares(java.util.List)}.
	 */
	@Test
	void testSetGetSquaresValid() {
		board.setSquares(squaresLowValid);
		assertEquals(squaresLowValid, board.getSquares());
		board.setSquares(squaresMidValid);
		assertEquals(squaresMidValid, board.getSquares());
		board.setSquares(squaresMaxValid);
		assertEquals(squaresMaxValid, board.getSquares());
		board.setSquares(squaresMaxInvalid);
		assertEquals(squaresMaxInvalid.subList(0, squaresMaxInvalid.size() - 1), board.getSquares());
	}
	
	/**
	 * Test method for {@link solaropoly.Board#setSquares(java.util.List)}.
	 */
	@Test
	void testSetGetSquaresInvalid() {
		assertEquals(
			assertThrows(IllegalArgumentException.class, () -> {
				board.setSquares(squaresLowInvalid);
			}).getMessage(), "The size of the list should be incremented. The minimum number of squares is " + MIN_SQUARES);
	}

	/**
	 * Test method for {@link solaropoly.Board#setGroups(java.util.Set)}.
	 */
	@Test
	void testSetGetGroupsValid() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		assertEquals(groupsLowValid, board.getGroups());
		board.setGroups(groupsMidValid);
		assertEquals(groupsMidValid, board.getGroups());
		board.setGroups(groupsMaxValid);
		assertEquals(groupsMaxValid, board.getGroups());
		
		// check if all the groups are valid when there are missing areas
		board.setSquares(squaresMaxValid);
		board.setGroups(groupsMissingAreasValid);
		assertEquals(groupsMissingAreasValid, board.getGroups());
	}
	
	/**
	 * Test method for {@link solaropoly.Board#setGroups(java.util.Set)}.
	 */
	@Test
	void testSetGetGroupsInvalid() {
		IndexOutOfBoundsException checkSquaresList = assertThrows(IndexOutOfBoundsException.class, () -> {
			board.setGroups(groupsLowValid);
		});
		IllegalArgumentException containsAllAreasGroups = assertThrows(IllegalArgumentException.class, () -> {
			board.setSquares(squaresMaxValid);
			board.setGroups(groupsWrongAreasInvalid);
		});
		IllegalArgumentException groupsWasTooShort = assertThrows(IllegalArgumentException.class, () -> {
			board.setSquares(squaresLowValid);
			board.setGroups(groupsLowInvalid);
		});
		IllegalArgumentException groupsWasTooLong = assertThrows(IllegalArgumentException.class, () -> {
			board.setSquares(squaresLowValid);
			board.setGroups(groupsMaxInvalid);
		});
		assertEquals(checkSquaresList.getMessage(), "The List of squares is empty. Please fill the board with squares before using the method.");
		assertEquals(containsAllAreasGroups.getMessage(), "The Set of groups has elements (squares of type Area) that are not present on the board.\n"
				+ "please correct the groups to match the areas/squares present in the board.");
		assertEquals(groupsWasTooShort.getMessage(), "The size of the Set should be incremented. The minimum number of groups is " + MIN_GROUPS);
		assertEquals(groupsWasTooLong.getMessage(), "The Set of groups was too long, and we need to comply with the rules.\n" + MAX_GROUPS
				+ "is the maximum number of groups allowed, but " + groupsMaxInvalid.size() + " groups were provided.\n");
	}

	/**
	 * Test method for {@link solaropoly.Board#getSize()}.
	 */
	@Test
	void testGetSize() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		
		assertEquals(squaresLowValid.size(), board.getSize());
	}

	/**
	 * Test method for {@link solaropoly.Board#getSquaresByType(java.lang.Class)}.
	 */
	@Test
	void testGetSquaresByType() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		
		assertEquals(sunrise, board.getSquaresByType(Sunrise.class).get(0));
		assertEquals(failure, board.getSquaresByType(Failure.class).get(0));
		assertEquals(holiday, board.getSquaresByType(Holiday.class).get(0));
		assertEquals(event, board.getSquaresByType(Event.class).get(0));
		assertEquals(commonAreas, board.getSquaresByType(Area.class));
	}

	/**
	 * Test method for {@link solaropoly.Board#getSquare(int)}.
	 */
	@Test
	void testGetSquare() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		int position1 = 5;
		int position2 = board.getSize() + 5; // loop the board and return the same result
		
		assertEquals(squaresLowValid.get(position1), board.getSquare(position1));
		assertEquals(board.getSquare(position1), board.getSquare(position2));
	}

	/**
	 * Test method for {@link solaropoly.Board#getBoardPosition(int, int)}.
	 */
	@Test
	void testGetBoardPositionPositive() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		int previousPositionPlayer = 3; // the player is in the event square
		int diceRoll = board.getSize() + 5; // we should pass the Sunrise square and have the new position
		int newPosition = (previousPositionPlayer + diceRoll) % board.getSize();
		int startPassed = ((previousPositionPlayer + diceRoll) - 1) / board.getSize();
		
		BoardPosition boardPosition = board.getBoardPosition(previousPositionPlayer, diceRoll);
		
		assertEquals(squaresLowValid.get(newPosition), boardPosition.getSquare());
		assertEquals(startPassed, boardPosition.getStartPassed());
		assertEquals(newPosition, boardPosition.getPosition());
	}
	
	/**
	 * Test method for {@link solaropoly.Board#getBoardPosition(int, int)}.
	 */
	@Test
	void testGetBoardPositionNegative() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		int previousPositionPlayer = 3; // the player is in the event square
		int diceRoll = - (board.getSize() + 5); // we should pass the Sunrise square and have the new position
		int newPosition = board.getSize() + ((previousPositionPlayer + diceRoll) % board.getSize());
		int startPassed = 0;
		
		BoardPosition boardPosition = board.getBoardPosition(previousPositionPlayer, diceRoll);
		
		assertEquals(squaresLowValid.get(newPosition), boardPosition.getSquare());
		assertEquals(startPassed, boardPosition.getStartPassed());
		assertEquals(newPosition, boardPosition.getPosition());
	}

	/**
	 * Test method for {@link solaropoly.Board#getPosition(solaropoly.Square)}.
	 */
	@Test
	void testGetPositionValid() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsLowValid);
		
		assertEquals(squaresLowValid.indexOf(sunrise), board.getPosition(sunrise));
	}
	
	/**
	 * Test method for {@link solaropoly.Board#getPosition(solaropoly.Square)}.
	 */
	@Test
	void testGetPositionInvalid() {
		IndexOutOfBoundsException checkSquaresList = assertThrows(IndexOutOfBoundsException.class, () -> {
			board.getPosition(sunrise);
		});
		assertEquals(checkSquaresList.getMessage(), "The List of squares is empty. Please fill the board with squares before using the method.");
	}

	/**
	 * Test method for {@link solaropoly.Board#getGroup(solaropoly.Area)}.
	 */
	@Test
	void testGetGroupValid() {
		board.setSquares(squaresLowValid);
		board.setGroups(groupsMissingAreasValid);
		Area area = commonAreas.get(0);
		
		assertEquals(board.getGroup(area), groupCommonAreas);
	}
	
	/**
	 * Test method for {@link solaropoly.Board#getGroup(solaropoly.Area)}.
	 */
	@Test
	void testGetGroupInvalid() {
		board.setSquares(squaresLowValid);
		Area area = commonAreas.get(0);
		
		IndexOutOfBoundsException checkGroupsSet = assertThrows(IndexOutOfBoundsException.class, () -> {
			board.getGroup(area);
		});
		assertEquals(checkGroupsSet.getMessage(), "The Set of groups is empty. Please fill the board with groups before using the method.");
	}

}
