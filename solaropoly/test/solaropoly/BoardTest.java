/**
 * 
 */
package solaropoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashSet;
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
	// private static final int MIN_SQUARES = 6;
	
	/**
	 * Maximum number of squares from the requirements of the game.
	 */
	// private static final int MAX_SQUARES = 20;
	
	/**
	 * Minimum number of groups from the requirements of the game.
	 */
	// private static final int MIN_GROUPS = 2;
	
	/**
	 * Maximum number of groups from the requirements of the game.
	 */
	// private static final int MAX_GROUPS = 4;
	
	// test the boundaries of the possible lengths of the squares List, the relative error message, exceptions and the list type.
	LinkedList<Square> squaresLowInvalid = new LinkedList<Square>();
	ArrayList<Square> squaresLowValid = new ArrayList<Square>();
	ArrayList<Square> squaresMidValid = new ArrayList<Square>();
	LinkedList<Square> squaresMaxValid = new LinkedList<Square>();
	ArrayList<Square> squaresMaxInvalid = new ArrayList<Square>();
	
	// test the boundaries of the possible lengths of the groups Set, exceptions and the Set type
	TreeSet<Group> groupsLowInvalid = new TreeSet<Group>();
	HashSet<Group> groupsLowValid = new HashSet<Group>();
	HashSet<Group> groupsMidValid = new HashSet<Group>();
	TreeSet<Group> groupsMaxValid = new TreeSet<Group>();
	HashSet<Group> groupsMaxInvalid = new HashSet<Group>();
	
	// test the Set groups, the relative error message if groups have missing Areas that are indeed in the board and the Set type
	TreeSet<Group> groupsLowInvalidMissingAreasValid = new TreeSet<Group>();
	HashSet<Group> groupsLowValidMissingAreasValid = new HashSet<Group>();
	
	// test the Set groups, the relative exception if groups have Areas that are not present in the board and the Set type
	TreeSet<Group> groupsLowInvalidWrongAreasInvalid = new TreeSet<Group>();
	HashSet<Group> groupLowValidWrongAreasInvalid = new HashSet<Group>();
	
	Board board;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		
		//squares = 
		
	}

	/**
	 * Test method for {@link solaropoly.Board#Board()}.
	 */
	@Test
	void testBoard() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#Board(java.util.List, java.util.Set)}.
	 */
	@Test
	void testBoardListOfSquareSetOfGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#setSquares(java.util.List)}.
	 */
	@Test
	void testSetSquares() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#setGroups(java.util.Set)}.
	 */
	@Test
	void testSetGroups() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#getSize()}.
	 */
	@Test
	void testGetSize() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#getSquare(int)}.
	 */
	@Test
	void testGetSquareInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#getSquare(int, int)}.
	 */
	@Test
	void testGetSquareIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#getPosition(solaropoly.Square)}.
	 */
	@Test
	void testGetPosition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link solaropoly.Board#getGroup(solaropoly.Area)}.
	 */
	@Test
	void testGetGroup() {
		fail("Not yet implemented");
	}

}
