package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Aspect;
import org.socionicasys.analyst.types.Function;
import org.socionicasys.analyst.types.Sign;
import org.socionicasys.analyst.types.Sociotype;
import org.socionicasys.analyst.util.EqualsUtil;
import org.socionicasys.analyst.util.HashUtil;

/**
 * Предикат исполняется, если заданная функция имеет свойства заданного знака.
 * Поскольку функция со знаком '-' компетентна и в зоне '+', то отметка '+' работает для всех функций.
 */
public class SignPredicate implements Predicate {
	/**
	 * Аспект предиката.
	 */
	private final Aspect aspect;

	/**
	 * Знак функции с заданным аспектом.
	 */
	private final Sign sign;

	public SignPredicate(Aspect aspect, Sign sign) {
		this.aspect = aspect;
		this.sign = sign;
	}

	@Override
	public CheckResult check(Sociotype sociotype) {
		Function socioFunc = sociotype.getFunctionByAspect(aspect);
		Sign actualSign = socioFunc.getSign();
		if(socioFunc.getDimension() > 1) {
			return CheckResult.fromBoolean(actualSign == sign);
		} else {
			return CheckResult.fromBoolean(true); // Gives ACCORD for 1 dimensional functions regardless
		}
		
	}

	@Override
	public String toString() {
		return String.format(" %s ", sign);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SignPredicate)) {
			return false;
		}

		SignPredicate otherPredicate = (SignPredicate) obj;
		return EqualsUtil.areEqual(aspect, otherPredicate.aspect) && sign == otherPredicate.sign;
	}

	@Override
	public int hashCode() {
		HashUtil hashUtil = new HashUtil();
		hashUtil.hash(aspect);
		hashUtil.hash(sign);
		return hashUtil.getComputedHash();
	}
}
