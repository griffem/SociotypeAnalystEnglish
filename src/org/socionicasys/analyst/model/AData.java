package org.socionicasys.analyst.model;

import org.socionicasys.analyst.util.EqualsUtil;
import org.socionicasys.analyst.util.HashUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AData implements Serializable {
	private static final long serialVersionUID = -7524659842673948203L;

	public static final String L = "Ti";
	public static final String P = "Te";
	public static final String R = "Fi";
	public static final String E = "Fe";
	public static final String S = "Si";
	public static final String F = "Se";
	public static final String T = "Ni";
	public static final String I = "Ne";
	private static final List<String> VALID_ASPECTS = Arrays.asList(L, P, R, E, S, F, T, I);

	public static final String DOUBT = "To be clarified";

	public static final String BLOCK = "BLOCK";
	public static final String JUMP = "TRANSFER";
	private static final List<String> VALID_MODIFIERS = Arrays.asList(BLOCK, JUMP);

	private static final String BLOCK_TOKEN = "\u21C4";
	private static final String JUMP_TOKEN = "\u2794";
	private static final String SEPARATOR = ";";

	public static final String PLUS = "PLUS";
	public static final String MINUS = "MINUS";
	private static final List<String> VALID_SIGNS = Arrays.asList(PLUS, MINUS);

	public static final String D1 = "Dimensionality Ex";
	public static final String D2 = "Dimensionality Nr";
	public static final String D3 = "Dimensionality St";
	public static final String D4 = "Dimensionality Tm";
	public static final String ODNOMERNOST = "One-dimensionality";
	public static final String INDIVIDUALNOST = "Individuality";
	public static final String MALOMERNOST = "Low-dimensionality";
	public static final String MNOGOMERNOST = "High-dimensionality";
	private static final List<String> VALID_DIMENSIONS = Arrays.asList(D1, D2, D3, D4, MALOMERNOST, MNOGOMERNOST, ODNOMERNOST, INDIVIDUALNOST);

	public static final String MENTAL = "Mental";
	public static final String VITAL = "Vital";
	public static final String EVALUATORY = "Evaluatory";
	public static final String SITUATIONAL = "Situational";
	private static final List<String> VALID_FDS = Arrays.asList(MENTAL, VITAL, EVALUATORY, SITUATIONAL);

	public static final String EGO = "Ego";
	public static final String SUPEREGO = "Super Ego";
	public static final String SUPERID = "Super Id";
	public static final String ID = "Id";
	private static final List<String> VALID_BLOCKS = Arrays.asList(EGO, SUPEREGO, SUPERID, ID);

	private static final Pattern PARSE_PATTERN = buildParsePattern();
	private static final int ASPECT_GROUP = 1;
	private static final int MODIFIER_GROUP = 2;
	private static final int SECOND_ASPECT_GROUP = 3;
	private static final int SIGN_GROUP = 4;
	private static final int DIMENSION_GROUP = 5;
	private static final int FD_GROUP = 6;
	private static final int BLOCKS_GROUP = 7;

	private final String secondAspect;
	private final String modifier;
	private final String aspect;
	private final String sign;
	private final String fd;
	private final String blocks;
	private final String dimension;
	private String comment;

	public AData(String aspect, String secondAspect, String sign, String dimension, String fd, String blocks, String modifier, String comment) {
		this.aspect = aspect;
		this.secondAspect = secondAspect;
		this.sign = sign;
		this.dimension = dimension;
		this.fd = fd;
		this.blocks = blocks;
		this.modifier = modifier;
		setComment(comment);
	}

	public String getAspect() {
		return aspect;
	}

	public String getModifier() {
		return modifier;
	}

	public String getSecondAspect() {
		return secondAspect;
	}

	public String getSign() {
		return sign;
	}

	public String getDimension() {
		return dimension;
	}

	public String getFD() {
		return fd;
	}

	public String getBlocks() {
		return blocks;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? "" : comment;
	}

	public String getComment() {
		return comment;
	}

	/**
	 * @return {@code true} when the data in the mark is in the finished state.
	 */
	@SuppressWarnings("RedundantIfStatement")
	public boolean isValid() {
		if (!DOUBT.equals(aspect) && !VALID_ASPECTS.contains(aspect)) {
			return false;
		}

		if (secondAspect != null && !VALID_ASPECTS.contains(secondAspect)) {
			return false;
		}

		if (sign != null && !VALID_SIGNS.contains(sign)) {
			return false;
		}

		if (dimension != null && !VALID_DIMENSIONS.contains(dimension)) {
			return false;
		}

		if (fd != null && !VALID_FDS.contains(fd)) {
			return false;
		}

		if (blocks != null && !VALID_BLOCKS.contains(blocks)) {
			return false;
		}

		if (modifier != null && !VALID_MODIFIERS.contains(modifier)) {
			return false;
		}

		if (modifier != null && secondAspect == null) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		if (!isValid()) {
			return "(Incomplete mark-up)";
		}

		StringBuilder builder = new StringBuilder(aspect);
		if (BLOCK.equals(modifier)) {
			builder.append(BLOCK_TOKEN).append(secondAspect).append(SEPARATOR);
		} else if (JUMP.equals(modifier)) {
			builder.append(JUMP_TOKEN).append(secondAspect).append(SEPARATOR);
		} else {
			builder.append(SEPARATOR);
		}

		if (sign != null) {
			builder.append(sign).append(SEPARATOR);
		}
		if (dimension != null) {
			builder.append(dimension).append(SEPARATOR);
		}
		if (fd != null) {
			builder.append(fd).append(SEPARATOR);
		}
		if (blocks != null) {
			builder.append(blocks);
		}
		return builder.toString();
	}

	public static AData parseAData(String s) {
		if (s == null) {
			throw new IllegalArgumentException("Parse string cannot be null");
		}

		Matcher dataMatcher = PARSE_PATTERN.matcher(s);
		if (!dataMatcher.matches()) {
			throw new IllegalArgumentException(String.format("Invalid markup data '%s'", s));
		}

		String aspect = dataMatcher.group(ASPECT_GROUP);
		String modifierToken = dataMatcher.group(MODIFIER_GROUP);
		String secondAspect = dataMatcher.group(SECOND_ASPECT_GROUP);
		String sign = dataMatcher.group(SIGN_GROUP);
		String dimension = dataMatcher.group(DIMENSION_GROUP);
		String fd = dataMatcher.group(FD_GROUP);
		String blocks = dataMatcher.group(BLOCKS_GROUP);

		String modifier = null;
		if (BLOCK_TOKEN.equals(modifierToken)) {
			modifier = BLOCK;
		} else if (JUMP_TOKEN.equals(modifierToken)) {
			modifier = JUMP;
		}

		return new AData(aspect, secondAspect, sign, dimension, fd, blocks, modifier, null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AData)) {
			return false;
		}

		AData data = (AData) obj;
		return EqualsUtil.areEqual(aspect, data.aspect) &&
			EqualsUtil.areEqual(secondAspect, data.secondAspect) &&
			EqualsUtil.areEqual(modifier, data.modifier) &&
			EqualsUtil.areEqual(dimension, data.dimension) &&
			EqualsUtil.areEqual(sign, data.sign) &&
			EqualsUtil.areEqual(fd, data.fd) &&
			EqualsUtil.areEqual(blocks, data.blocks) &&
			EqualsUtil.areEqual(comment, data.comment);
	}

	@Override
	public int hashCode() {
		HashUtil hashUtil = new HashUtil();
		hashUtil.hash(aspect);
		hashUtil.hash(secondAspect);
		hashUtil.hash(modifier);
		hashUtil.hash(dimension);
		hashUtil.hash(sign);
		hashUtil.hash(fd);
		hashUtil.hash(blocks);
		hashUtil.hash(comment);
		return hashUtil.getComputedHash();
	}

	/**
	 * Forms a regular expression to parse strings into a {@code AData} instance.
	 *
	 * @return generated regular expression
	 */

	 // FIXME
	private static Pattern buildParsePattern() {
		StringBuilder patternBuilder = new StringBuilder(" *(");
		patternBuilder.append(joinRegexValues(VALID_ASPECTS));
		patternBuilder.append('|').append(DOUBT).append(')');

		patternBuilder.append("(?:([").append(BLOCK_TOKEN).append(JUMP_TOKEN).append("])");
		patternBuilder.append('(').append(joinRegexValues(VALID_ASPECTS)).append("))?;");

		patternBuilder.append("(?:(").append(joinRegexValues(VALID_SIGNS)).append(");)?");

		patternBuilder.append("(?:(").append(joinRegexValues(VALID_DIMENSIONS)).append(");)?");

		patternBuilder.append("(?:(").append(joinRegexValues(VALID_FDS)).append(");)?");

		patternBuilder.append('(').append(joinRegexValues(VALID_BLOCKS)).append(")?");

		return Pattern.compile(patternBuilder.toString());
	}

	/**
	 * Combines variants from the array into a string separated by |
	 * to create a regular expression.
	 *
	 * @param values array of values
	 * @return string of values separated by |
	 */
	private static String joinRegexValues(List<String> values) {
		StringBuilder join = new StringBuilder();
		for (String value : values) {
			join.append(value).append('|');
		}
		join.deleteCharAt(join.length() - 1);
		return join.toString();
	}
}
