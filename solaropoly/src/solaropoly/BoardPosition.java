/**
 * Solaropoly game
 */
package solaropoly;

/**
 * BoardPosition is the collection of the landing square, the number of times the player crossed the first square and his new position.
 * It doesn't have a default constructor because the class should be never instantiate without values on it.
 * We can access the player informations values with getSquare() getStartPassed() and getPosition().
 * The collection should be changed only by calling the constructor.
 * 
 * @author Roberto Lo Duca 40386172
 *
 */
public class BoardPosition {
	
    private Square square;
    private int startPassed;
    private int position;
    
    /**
     * Constructor with arguments
	 * @param square - The square where the player landed
	 * @param startPassed - The number of times the player passed the starting square to reach that specific square.
	 * @param position - The new position of the player
	 */
	public BoardPosition(Square square, int startPassed, int position) {
		this.square = square;
		this.startPassed = startPassed;
		this.position = position;
	}
	
	/**
	 * @return the square
	 */
	public Square getSquare() {
		return square;
	}
	
	/**
	 * @return the number of times the player passed the starting square
	 */
	public int getStartPassed() {
		return startPassed;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
}