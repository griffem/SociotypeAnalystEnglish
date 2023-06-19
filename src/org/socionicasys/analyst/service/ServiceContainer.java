package org.socionicasys.analyst.service;

import java.util.ResourceBundle;

/**
 * Класс, хранящий статические экземпляры глобальных сервисов.
 */
public final class ServiceContainer {
	private static final ResourceBundle RESOURCE_BUNDLE =
			ResourceBundle.getBundle("resources.org.socionicasys.analyst.messages");

	private ServiceContainer() {
	}

	/**
	 * Возвращает основное хранилище ресурсов для приложения.
	 *
	 * @return основной {@link ResourceBundle} приложения
	 */
	public static ResourceBundle getResourceBundle() {
		return RESOURCE_BUNDLE;
	}
}
