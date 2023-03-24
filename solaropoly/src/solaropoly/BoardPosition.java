/**
 * Solaropoly game
 */
package solaropoly;

import java.util.Map;
import java.util.Map.Entry;

/**
 * BoardPosition is the pair of the square and the position that needs to be returned from a method.
 * It doesn't have a default constructor because the class should be never instantiate without values on it.
 * This Class implement the Map.Entry class to allow to access the key and the value.
 * We can access the key and the value with getSquare and getStartPassed
 * It is not allowed to use setters because the pair should be changed only by calling the constructor
 * 
 * @author Roberto Lo Duca 40386172
 *
 */
public class BoardPosition implements Map.Entry<Square, Integer> {
	
    private Square square;
    private Integer startPassed;
    
    /**
     * Constructor with arguments
	 * @param square - The square
	 * @param startPassed - The number of times the player passed the starting square to reach that specific square.
	 */
	public BoardPosition(Square square, Integer startPassed) {
		this.square = square;
		this.startPassed = startPassed;
	}
	
	/**
	 * @return the square
	 */
	public Square getSquare() {
		return square;
	}
	
	/**
	 * @return the startPassed
	 */
	public Integer getStartPassed() {
		return startPassed;
	}
	
	// Implement Map.Entry methods
    @Override
    public Square getKey() {
        return square;
    }
    
    @Override
    public Integer getValue() {
        return startPassed;
    }
    
    @Override
    public Integer setValue(Integer value) {
        Integer oldValue = startPassed;
        startPassed = value;
        return oldValue;
    }
}