/**
 * 
 */
package solaropoly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author samil
 *
 */
public class Player {
	
	// instance vars

	private String name;
	private int balance;
	private int position;
	private HashSet<Square> ownedSquares; // TODO make this TreeSet/ArrayList
	private ArrayList<Group> ownedGroups; 
	
	// setget
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) throws IllegalArgumentException, IllegalStateException {
		if (name.strip() == "" || name == null) {
			throw new IllegalArgumentException("Need at least one non-whitespace character");
		} else if (name.length() > 20) {
			this.name = name.substring(0, 19);
			System.out.println("We did have to shorten that - sorry!");
		} else {
			this.name = name;
		}
	}
	
	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		if (position >= GameSystem.BOARD_SIZE || position < 0) {
			throw new IllegalArgumentException("Invalid player position");
		} else {
			this.position = position;
		}
	}
	
	/**
	 * @return the ownedSquares
	 */
	public HashSet<Square> getOwnedSquares() {
		return ownedSquares;
	}
	
	/**
	 * @param ownedSquares the ownedSquares to set
	 */
	public void setOwnedSquares(HashSet<Square> ownedSquares) {
		this.ownedSquares = ownedSquares;
	}
	
	/**
	 * @return the ownedGroups
	 */
	public ArrayList<Group> getOwnedGroups() {
		return ownedGroups;
	}

	/**
	 * @param ownedGroups the ownedGroups to set
	 */
	public void setOwnedGroups(ArrayList<Group> ownedGroups) {
		this.ownedGroups = ownedGroups;
	}

	
	// constr
	
	public Player() {}
	
	/**
	 * @param name
	 * @param balance
	 * @param position
	 */
	public Player(String name, int balance, int position) {
		this.setName(name);
		this.balance = balance;
		this.setPosition(position);
		this.ownedSquares = new HashSet<Square>();
	};
	
	// methods
	
	public void displayBalance() {
		System.out.printf("%s, your current balance is £%,d.%n", this.name, this.balance);
		// TODO another version showing properties too
	}
	
	public void increaseBalance(int credit) {
		this.setBalance(this.balance + credit);
	}
	
	public void decreaseBalance(int debit) {
		this.setBalance(this.balance - debit);
	}
	
	public void gainOwnership(Square square) {
		this.ownedSquares.add(square);
	}
	
}
