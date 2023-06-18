package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * The predicate is executed if the given aspect is in the Ego.
 */
public class EgoPredicate extends PositionPredicate {
	public EgoPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(1, 2));
	}

	@Override
	public String toString() {
		return "Ego";
	}
}
