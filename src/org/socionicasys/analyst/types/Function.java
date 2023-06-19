package org.socionicasys.analyst.types;

/**
 * Describes a separate function in Model A.
 */
public class Function {
	/**
	 * Aspect of function.
	 */
	private final Aspect aspect;

	/**
	 * Function location in the model: 1-8.
	 */
	private final int position;

	/**
	 * The function sign.
	 */
	private final Sign sign;

	public Function(Aspect aspect, int position, Sign sign)
	{
		this.aspect = aspect;
		if (position >= 1 && position <= 8) {
			this.position = position;
		} else {
			throw new IllegalArgumentException("Illegation position for socionics function.");
		}
		this.sign = sign;
	}

	/**
	 * @return aspect of the function
	 */
	public Aspect getAspect() {
		return aspect;
	}

	/**
	 * @return function position in Model A (1-8)
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return function sign
	 */
	public Sign getSign() {
		return sign;
	}

	/**
	 * Calculates the dimensionality of the function.
	 * 1st and 8th functions are 4-dimensional, 2nd and 7th functions are 3-dimensional, 3rd and 6th functions are 2-dimensional, 4th and 5th functions are one-dimensional.
	 * @return function dimensionality
	 */
	public int getDimension() {
		if (isMental()) {
			return 5 - position;
		} else {
			return position - 4;
		}
	}

	/**
	 * Check the function for mental (1-4) or vital (5-8).
	 * @return whether the function is mental
	 */
	public boolean isMental() {
		return (position <= 4);
	}
}
