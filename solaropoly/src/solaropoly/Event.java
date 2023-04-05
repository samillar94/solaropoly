/**
 * Solaropoly game
 */
package solaropoly;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;

/**
 * This class generate the square object for event. This object will store a series of cards.
 * Each card has specific functionalities that will be applied to the player. The result of
 * the card could be a negative, positive or both for the player. The properties for each card
 * are stored in a Queue of Card objects and they are sorted randomly from this class and
 * applied to the player by the act method of this square. Once the card is used it will be appended
 * at the end of the cards Queue.
 * 
 * @author Roberto Lo Duca 40386172
 *
 */
public class Event extends Square {
	
	/**
	 * The cards that needs to be triggered by the act method
	 */
	private final ArrayDeque<Card> cards = new ArrayDeque<>();
	
	/**
	 * Default constructor
	 */
	public Event() {}

	/**
	 * Constructor with arguments
	 * @param name - the name of the square
	 * @param cards - the List of cards to set, shuffle and convert in Queue
	 */
	public Event(String name, List<Card> cards) {
		super(name);
		if (cards != null) {
			this.addCards(cards);
		}
	}

	/**
	 * @return the cards
	 */
	public ArrayDeque<Card> getCards() {
		return cards;
	}
	
	/**
	 * This method return the next card that will be applied to the player
	 * @return the card
	 */
	public Card getCard() {
		return cards.peekFirst();
	}
	
	/**
	 * This method add a card at the end of the Queue
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	/**
	 * This method shuffles and adds cards at the end of the Queue
	 */
	public void addCards(List<Card> cards) {
		Collections.shuffle(cards); // Shuffle the list
		this.cards.addAll(cards);
	}
	/**
	 * This method will loop the Queue of cards and apply their properties
	 * to the player who landed on the Event square.
	 */
	@Override
	public void act(Player player) {
		Card element = this.cards.pollFirst(); // take the card in the queue
		String input;
		
		System.out.println("You landed in the Event Square. Please press Enter to take a card and read it...");
		
		while (true) {
			input = GameSystem.SCANNER.nextLine();

			if (input.equalsIgnoreCase("")) {
				break;
			} else {
				System.out.println("Wrong input - please just press Enter to continue your turn");
			}
		}
        System.out.print(element.getEvent());
        player.move(element.getMovePosition());
        player.increaseBalance((int) element.getEarn()); // TODO: should the balance be a long?
        player.setTurns(element.getTurns());
        
        this.cards.offerLast(element); // put the card at the bottom of the Queue
	}
}
