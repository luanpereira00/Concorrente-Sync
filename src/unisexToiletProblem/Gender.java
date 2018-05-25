package unisexToiletProblem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author luanpereira
 * An enum to represent Gender
 */
public enum Gender {
	MALE,
	FEMALE;
	
	/**
	 * Aux method to get random gender
	 */
	private static final List<Gender> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	/**
	 * Aux method to get random gender
	 */
	private static final int SIZE = VALUES.size();
	
	/**
	 * Aux method to get random gender
	 */
	private static final Random RANDOM = new Random();
	
	/**
	 * Method to get random gender
	 * @return the gender
	 */
	public static Gender randomGender()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
