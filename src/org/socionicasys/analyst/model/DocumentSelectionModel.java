package org.socionicasys.analyst.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Describes the selection within the document, and possible marks in that selection.
 * Allows other objects to track changes in that selection.
 */
@SuppressWarnings("NestedAssignment")
public class DocumentSelectionModel {
	private boolean initialized;
	private int startOffset;
	private int endOffset;
	private String aspect;
	private String secondAspect;
	private String modifier;
	private String sign;
	private String fd;
	private String blocks;
	private String dimension;
	private String comment;

	private final PropertyChangeSupport propertyChangeSupport;

	private static final Logger logger = LoggerFactory.getLogger(DocumentSelectionModel.class);

	/**
	 * Initializes an empty selection model.
	 */
	public DocumentSelectionModel() {
		logger.trace("DocumentSelectionModel(): entering");
		propertyChangeSupport = new PropertyChangeSupport(this);
		comment = "";
		logger.trace("DocumentSelectionModel(): leaving");
	}

	/**
	 * @return whether the allocation is empty
	 */
	public boolean isEmpty() {
		return startOffset == endOffset;
	}

	/**
	 * @return whether the model is currently initialized. {@code false} means,
	 * model properties are being filled in now.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the initialization flag of the selection.
	 * 
	 * @param initialized whether the selection is initialized
	 */
	public void setInitialized(boolean initialized) {
		updateProperty("initialized", this.initialized, this.initialized = initialized);
	}

	/**
	 * @return initial selection position in the document
	 */
	public int getStartOffset() {
		return startOffset;
	}

	/**
	 * @param startOffset new value of the initial position of the selection in the document
	 */
	public void setStartOffset(int startOffset) {
		updateProperty("startOffset", this.startOffset, this.startOffset = startOffset);
	}

	/**
	 * @return конечная позиция выделения в документе
	 */
	public int getEndOffset() {
		return endOffset;
	}

	/**
	 * @param endOffset новое значение конечной позиции выделения в документе
	 */
	public void setEndOffset(int endOffset) {
		updateProperty("endOffset", this.endOffset, this.endOffset = endOffset);
	}

	/**
	 * @return основной аспект выделения, {@code null} если для выделеного фрагмента
	 * нет пометок для анализа
	 */
	public String getAspect() {
		return aspect;
	}

	/**
	 * @param aspect новый основной аспект выделения
	 */
	public void setAspect(String aspect) {
		updateProperty("aspect", this.aspect, this.aspect = aspect);
		if (aspect == null) {
			setSecondAspect(null);
			setModifier(null);
			setSign(null);
			setFD(null);
			setBlocks(null);
			setDimension(null);
			setComment("");
		} else if (AData.DOUBT.equals(aspect)) {
			setSecondAspect(null);
			setModifier(null);
		}
	}

	/**
	 * @return второй аспект выделения, {@code null} если в текущем выделенном фрагменте не отмечены
	 * блок или перевод
	 */
	public String getSecondAspect() {
		return secondAspect;
	}

	/**
	 * @param secondAspect второй аспект выделения, если отмечены перевод или блок, иначе {@code null}
	 */
	public void setSecondAspect(String secondAspect) {
		updateProperty("secondAspect", this.secondAspect, this.secondAspect = secondAspect);
	}

	/**
	 * @return модификатор выделения — отмечен ли отдельный аспект, блок, или перевод
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier модификатор выделения — отмечен ли отдельный аспект, блок, или перевод
	 */
	public void setModifier(String modifier) {
		updateProperty("modifier", this.modifier, this.modifier = modifier);
	}

	/**
	 * @return знак основного аспекта в выделении
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign знак основного аспекта в выделении
	 */
	public void setSign(String sign) {
		updateProperty("sign", this.sign, this.sign = sign);
	}

	/**
	 * @return indicator mental/vital or eval/sit
	 */
	public String getFD() {
		return fd;
	}

	/**
	 * @param fd indicator mental/vital or eval/sit
	 */
	public void setFD(String fd) {
		updateProperty("FD", this.fd, this.fd = fd);
	}

	/**
	 * @return blocks
	 */
	public String getBlocks() {
		return blocks;
	}

	/**
	 * @param fd
	 */
	public void setBlocks(String blocks) {
		updateProperty("Blocks", this.blocks, this.blocks = blocks);
	}

	/**
	 * @return индикатор размерности
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * @param dimension indicator
	 */
	public void setDimension(String dimension) {
		updateProperty("dimension", this.dimension, this.dimension = dimension);
	}

	/**
	 * @param comment comment on selection
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment комментарий к выделению
	 */
	public void setComment(String comment) {
		updateProperty("comment", this.comment, this.comment = comment);
	}

	/**
	 * @return if the selected block is empty ({@code true}), or are there any
	 * analytical marks ({@code false})
	 */
	public boolean isMarkupEmpty() {
		return aspect == null;
	}

	/**
	 * Collects current data from labels in {@link AData} for storage in the document.
	 *
	 * @return the current state of the labels
	 */
	public AData getMarkupData() {
		if (isMarkupEmpty()) {
			return null;
		}
		return new AData(aspect, secondAspect, sign, dimension, fd, blocks, modifier, comment);
	}

	/**
	 * Заполняет выделение блоком данных.
	 *
	 * @param markupData объект с данными для заполнения выделения. {@code null} очищает выделение.
	 */
	public void setMarkupData(AData markupData) {
		logger.trace("setMarkupData({}): entering", markupData);
		setInitialized(false);
		if (markupData == null) {
			setAspect(null);
		} else {
			setAspect(markupData.getAspect());
			setSecondAspect(markupData.getSecondAspect());
			setModifier(markupData.getModifier());
			setSign(markupData.getSign());
			setFD(markupData.getFD());
			setBlocks(markupData.getBlocks());
			setDimension(markupData.getDimension());
			setComment(markupData.getComment());
		}
		setInitialized(true);
		logger.trace("setMarkupData(): leaving");
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * <p>Вспомогательный метод для изменения значений свойств объекта. Создает
	 * {@link java.beans.PropertyChangeEvent}, описывающий изменение, и возвращает новое значение свойства,
	 * которое можно присвоить полю.</p>
	 *
	 * <p>Использование:
	 * {@code updateProperty("myProperty", myProperty, myProperty = newPropertyValue)}</p>
	 * 
	 * @param propertyName имя свойства
	 * @param oldValue старое значение
	 * @param newValue новое значение
	 * @param <T> тип данных свойства
	 */
	private <T> void updateProperty(String propertyName, T oldValue, T newValue) {
		logger.trace("updateProperty({}, {}, {}): entering", propertyName, oldValue, newValue);
		if (oldValue != null || newValue != null) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
		logger.trace("updateProperty({}, {}, {}): leaving", propertyName, oldValue, newValue);
	}
}
