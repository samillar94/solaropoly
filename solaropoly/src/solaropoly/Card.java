/**
 * Solaropoly game
 */
package solaropoly;

/**
 * This class creates objects of cards to be stored and used in the Event square
 * 
 * @author Roberto Lo Duca 40386172
 *
 */
public class Card {
	private String event;
	private int movePosition;
	private long earn;
	private int turns;
	
	/**
	 * Default constructor
	 */
	public Card() {}

	/**
	 * Constructor with arguments.
	 * @param event - it is a message to explain the details of the card event.
	 * @param movePosition - it moves the player back or forward from his position.
	 * @param earn - it reduce or increase if positive the {@value GameSystem#SUF} of the player.
	 * @param turns - it gives if positive or stop if negative a certain amount of turns to the player.
	 */
	public Card(String event, int movePosition, long earn, int turns) {
		this.event = event;
		this.movePosition = movePosition;
		this.earn = earn;
		this.turns = turns;
	}
	
	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * it is a message to explain the details of the card event.
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	
	/**
	 * @return the movePosition
	 */
	public int getMovePosition() {
		return movePosition;
	}
	
	/**
	 * it moves the player back or forward from his position.
	 * @param movePosition the movePosition to set
	 */
	public void setMovePosition(int movePosition) {
		this.movePosition = movePosition;
	}
	
	/**
	 * @return the earn
	 */
	public long getEarn() {
		return earn;
	}
	
	/**
	 * it reduce or increase if positive the {@value GameSystem#SUF} of the player.
	 * @param earn the earn to set
	 */
	public void setEarn(long earn) {
		this.earn = earn;
	}
	
	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}
	
	/**
	 * it gives if positive or stop if negative a certain amount of turns to the player.
	 * @param turns the turns to set
	 */
	public void setTurns(int turns) {
		this.turns = turns;
	}	
}
