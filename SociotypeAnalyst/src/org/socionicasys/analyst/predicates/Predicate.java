package org.socionicasys.analyst.predicates;

import org.socionicasys.analyst.types.Sociotype;

/**
 * Предикат, описывающий какую-либо информацию о соционических типах.
 */
public interface Predicate {
	/**
	 * Проверяет, удовлетворяет ли заданный ТИМ предикату.
	 */
	CheckResult check(Sociotype sociotype);
}
