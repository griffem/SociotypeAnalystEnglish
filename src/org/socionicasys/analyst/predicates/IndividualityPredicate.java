package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;

import java.util.Arrays;

/**
 * Предикат исполняется, если функция с заданным аспектом является индивидуальной (одномерной или витальной).
 */
public class IndividualityPredicate extends PositionPredicate {
	public IndividualityPredicate(Aspect aspect) {
		super(aspect, Arrays.asList(4, 5, 6, 7, 8));
	}

	@Override
	public String toString() {
		return "Individuality";
	}
}
