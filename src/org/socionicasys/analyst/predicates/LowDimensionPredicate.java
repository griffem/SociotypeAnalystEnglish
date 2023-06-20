package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * Предикат исполняется, если функция с заданным аспектом является маломерной.
 */
public class LowDimensionPredicate extends PositionPredicate {
	public LowDimensionPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(3, 4, 5, 6));
	}

	@Override
	public String toString() {
		return "Low-Dimensionality";
	}
}
