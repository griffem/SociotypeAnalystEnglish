package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * Предикат исполняется, если заданный аспект находится в ментальном кольце.
 */
public class MentalPredicate extends PositionPredicate {
	public MentalPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(1, 2, 3, 4));
	}

	@Override
	public String toString() {
		return "Mental";
	}
}
