package org.socionicasys.analyst;

/**
 * Содержит константы, общие для {@link LegacyHtmlReader} и {@link LegacyHtmlWriter}.
 */
public interface LegacyHtmlFormat {
	// Кодировка файлов
	String FILE_ENCODING = "UTF-8";

	// Расширение по-умолчанию
	String EXTENSION = "htm";

	// Заголовки полей документа в файле
	String TITLE_PROPERTY_LABEL = "Document:";
	String EXPERT_PROPERTY_LABEL = "Expert(s):";
	String CLIENT_PROPERTY_LABEL = "Interwievee:";
	String DATE_PROPERTY_LABEL = "Date:";
	String COMMENT_PROPERTY_LABEL = "Comments:";
}
