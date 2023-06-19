package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * Предикат исполняется, если заданный аспект находится в Супериде.
 */
public class SuperidPredicate extends PositionPredicate {
	public SuperidPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(5, 6));
	}

	@Override
	public String toString() {
		return "Super Id";
	}
}
