package org.socionicasys.analyst;

import org.socionicasys.analyst.model.AData;
import org.socionicasys.analyst.predicates.*;
import org.socionicasys.analyst.types.Aspect;
import org.socionicasys.analyst.types.Sign;
import org.socionicasys.analyst.types.Sociotype;

import java.util.*;

/**
 * Содержит в себе код сверки социотипа и набора данных из пометок.
 */
public final class SocionicsType {
	private SocionicsType() {
	}

	/**
	 * Проверяет, соответствует ли данный ТИМ конкретным отметкам.
	 *
	 * @param type ТИМ
	 * @param predicates список предикотов из текстовой отметки
	 * @return соответствует ли ТИМ отметке
	 */
	public static CheckResult matches(Sociotype type, Collection<Predicate> predicates) {
		boolean successFound = false;
		for (Predicate predicate : predicates) {
			switch (predicate.check(type)) {
			case FAIL:
				return CheckResult.FAIL;
			case IGNORE:
				break;
			case SUCCESS:
				successFound = true;
				break;
			}
		}
		return successFound ? CheckResult.SUCCESS : CheckResult.IGNORE;
	}

	/**
	 * Преобразует набор отметок в список предикатов.
	 *
	 * @param data данные из отметки
	 * @return список предикатов для отметки
	 */
	public static Collection<Predicate> createPredicates(AData data) {
		String aspectCode = data.getAspect();
		if (aspectCode == null) {
			return Collections.emptyList();
		}

		Aspect baseAspect = Aspect.byAbbreviation(aspectCode);
		Collection<Predicate> predicates = new ArrayList<Predicate>();

		String secondAspectCode = data.getSecondAspect();
		if (secondAspectCode != null) {
			String modifier = data.getModifier();
			Aspect toAspect = Aspect.byAbbreviation(secondAspectCode);
			if (AData.BLOCK.equals(modifier)) {
				predicates.add(new BlockPredicate(baseAspect, toAspect));
			}
			else if (AData.JUMP.equals(modifier)) {
				predicates.add(new JumpPredicate(baseAspect, toAspect));
			}
		}

		String sign = data.getSign();
		if (sign != null) {
			Sign convertedSign;
			if (sign.equals(AData.PLUS)) {
				convertedSign = Sign.PLUS;
			} else if (sign.equals(AData.MINUS)) {
				convertedSign = Sign.MINUS;
			} else {
				throw new IllegalArgumentException("Illegal sign in SocionicsType.matches()");
			}
			predicates.add(new SignPredicate(baseAspect, convertedSign));
		}

		String dimension = data.getDimension();
		if (dimension != null) {
			Predicate dimensionPredicate;
			if (dimension.equals(AData.D1)) {
				dimensionPredicate = new DimensionPredicate(baseAspect, 1);
			} else if (dimension.equals(AData.D2)) {
				dimensionPredicate = new DimensionPredicate(baseAspect, 2);
			} else if (dimension.equals(AData.D3)) {
				dimensionPredicate = new DimensionPredicate(baseAspect, 3);
			} else if (dimension.equals(AData.D4)) {
				dimensionPredicate = new DimensionPredicate(baseAspect, 4);
			} else if (dimension.equals(AData.MALOMERNOST)) {
				dimensionPredicate = new LowDimensionPredicate(baseAspect);
			} else if (dimension.equals(AData.MNOGOMERNOST)) {
				dimensionPredicate = new HighDimensionPredicate(baseAspect);
			} else if (dimension.equals(AData.ODNOMERNOST)) {
				dimensionPredicate = new Dimension1Predicate(baseAspect);
			} else if (dimension.equals(AData.INDIVIDUALNOST)) {
				dimensionPredicate = new IndividualityPredicate(baseAspect);
			} else {
				throw new IllegalArgumentException("Illegal dimension in SocionicsType.matches()");
			}
			predicates.add(dimensionPredicate);
		}

		String fd = data.getFD();
		if (fd != null) {
			Predicate fdPredicate;
			if (fd.equals(AData.MENTAL)) {
				fdPredicate = new MentalPredicate(baseAspect);
			} else if (fd.equals(AData.VITAL)) {
				fdPredicate = new VitalPredicate(baseAspect);
			} else if (fd.equals(AData.EVALUATORY)) {
				fdPredicate = new EvaluatoryPredicate(baseAspect);
			} else if (fd.equals(AData.SITUATIONAL)) {
				fdPredicate = new SituationalPredicate(baseAspect);
			} else {
				throw new IllegalArgumentException("Illegal FD in SocionicsType.matches()");
			}
			predicates.add(fdPredicate);
		}

		String blocks = data.getBlocks();
		if (blocks != null) {
			Predicate blocksPredicate;
			if (blocks.equals(AData.EGO)) {
				blocksPredicate = new EgoPredicate(baseAspect);
			} else if (blocks.equals(AData.SUPEREGO)) {
				blocksPredicate = new SuperegoPredicate(baseAspect);
			} else if (blocks.equals(AData.SUPERID)) {
				blocksPredicate = new SuperidPredicate(baseAspect);
			} else if (blocks.equals(AData.ID)) {
				blocksPredicate = new IdPredicate(baseAspect);
			} else {
				throw new IllegalArgumentException("Illegal Blocks in SocionicsType.matches()");
			}
			predicates.add(blocksPredicate);
		}

		return predicates;
	}
}
