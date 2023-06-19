package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

// Activates the predicate if the given aspect is evaluatory

public class EvaluatoryPredicate extends PositionPredicate {
	public EvaluatoryPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(1, 4, 5, 8));
	}

	@Override
	public String toString() {
		return "Evaluatory";
	}
}
