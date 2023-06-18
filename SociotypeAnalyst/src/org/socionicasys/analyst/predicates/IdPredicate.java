package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * The predicate is executed if the given aspect is in the Id.
 */
public class IdPredicate extends PositionPredicate {
	public IdPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(7, 8));
	}

	@Override
	public String toString() {
		return "Id";
	}
}
