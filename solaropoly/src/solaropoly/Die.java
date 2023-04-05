/**
* Solaropoly game
*/
package solaropoly;

import java.util.Random;

/**
* This class is used to create die objects.
* It returns a random value between 1 and the number of sides of the die
* 
* @author Dan
*
*/
public class Die {
   private int sides;
   private int result;
   private Random random;
   
   /**
    * In a real-word scenario the number of sides of a dice should be 4.
    */
   private static final int MIN_SIDES = 4;

   /**
    * Default constructor
    * It sets the default number of sides at 6 (a normal die)
    */
   public Die() {
       this(6);
   }

   /**
    * Constructor with arguments
    * @param sides - the number of sides of the die to set
    * @throws IllegalArgumentException - the side should be a number greater than 1
    */
   public Die(int sides) throws IllegalArgumentException {
       this.setSides(sides);
       this.random = new Random(); // update the seed by creating a new random object
   }

   /**
	 * @return the sides
	 */
	public int getSides() {
		return sides;
	}

	/**
	 * @param sides the sides to set
	 * @throws IllegalArgumentException - the side should be a number greater than {@value #MIN_SIDES}.
	 */
	public void setSides(int sides) throws IllegalArgumentException {
		if (sides >= MIN_SIDES) {
			this.sides = sides;
		} else {
			throw new IllegalArgumentException("The sides of a dice must be a number greater than " + MIN_SIDES);
		}
	}

	/**
	 * The last value generated is stored in the object.
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

   // Methods
	
	/**
	 * It stores in the die object the result and it returns the result. 
	 * @return - the result of the die
	 */
	public int roll() {
       this.result = random.nextInt(sides) + 1;
       return result;
   }
}
