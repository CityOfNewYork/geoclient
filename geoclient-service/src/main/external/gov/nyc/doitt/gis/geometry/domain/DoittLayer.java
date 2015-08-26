package gov.nyc.doitt.gis.geometry.domain;

/**
 * The DoittLayer is a spatial data layer that can be drawn on a map by the map
 * service.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public class DoittLayer implements Cloneable {

	/**
	 * The unique id of this layer.
	 */
	private String id;

	/**
	 * A boolean value that indicates whether or not this layer will be shown in
	 * the map's legend.
	 */
	private boolean legend = true;

	/**
	 * The maximum relative scale factor at which this DFoittLayer will be drawn
	 * on a map.
	 */
	private long maxRelativeScaleFactor = -1;

	/**
	 * The minimum relative scale factor at which this DFoittLayer will be drawn
	 * on a map.
	 */
	private long minRelativeScaleFactor = -1;

	/**
	 * A friendly name for this layer.
	 */
	private String name;

	/**
	 * A boolean value that indicates whether or not this layer will be drawn on
	 * the map.
	 */
	private boolean visible = true;

	private boolean visibleAtScale = true;

	public DoittLayer() {
	}

	/**
	 * @param id
	 *            The unique id of this layer.
	 * @param name
	 *            A friendly name for this layer.
	 * @param visible
	 *            A boolean value that indicates whether or not this layer will
	 *            be drawn on the map.
	 * @param legend
	 *            A boolean value that indicates whether or not this layer will
	 *            be shown in the map's legend.
	 */
	public DoittLayer(String id, String name, boolean visible, boolean legend) {
		setId(id);
		setName(name);
		setVisible(visible);
		setLegend(legend);
	}

	public Object clone() {
		try {
			return super.clone();

		} catch (CloneNotSupportedException e) {
			// safe to ignore, we are supporting clone
			return null;
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof DoittLayer))
			return false;

		DoittLayer other = (DoittLayer) o;

		boolean idEquals;
		if (id == null && other.getId() == null) {
			idEquals = true;
		} else if (id == null || other.getId() == null) {
			idEquals = false;
		} else {
			idEquals = id.equals(other.getId());
		}
		
		if (!idEquals) {
			return false;
		}
		
		boolean nameEquals;
		if (name == null && other.getName() == null) {
			nameEquals = true;
		} else if (name == null || other.getName() == null) {
			nameEquals = false;
		} else {
			nameEquals = name.equals(other.getName());
		}
		return nameEquals;
	}

	/**
	 * @return The unique id of this layer.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The maximum relative scale factor at which this DFoittLayer will
	 *         be drawn on a map.
	 */
	public long getMaxRelativeScaleFactor() {
		return maxRelativeScaleFactor;
	}

	/**
	 * @return The minimum relative scale factor at which this DFoittLayer will
	 *         be drawn on a map.
	 */
	public long getMinRelativeScaleFactor() {
		return minRelativeScaleFactor;
	}

	/**
	 * @return The friendly name for this layer.
	 */
	public String getName() {
		return name;
	}

	public int hashCode() {
		int hashCode = 0;
		if (id != null) {
			hashCode += id.hashCode();
		}
		
		if (name != null) {
			hashCode += 37 * name.hashCode();
		}
		
		return hashCode;
	}

	boolean isGreaterThanMinScale(double relativeScaleFactor) {
		if (!isMinScaleSet())
			return true;
		return minRelativeScaleFactor <= relativeScaleFactor;
	}

	/**
	 * @return A boolean value that indicates whether or not this layer will be
	 *         shown in the map's legend.
	 */
	public boolean isLegend() {
		return legend;
	}

	boolean isLessThanMaxScale(double relativeScaleFactor) {
		if (!isMaxScaleSet())
			return true;
		return maxRelativeScaleFactor >= relativeScaleFactor;
	}

	boolean isMaxScaleSet() {
		return maxRelativeScaleFactor >= 0;
	}

	boolean isMinScaleSet() {
		return minRelativeScaleFactor >= 0;
	}

	/**
	 * @return A boolean value that indicates whether or not this layer will be
	 *         drawn on the map.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @return A boolean value that indicates whether or not this layer will be
	 *         drawn on the map at the current map scale.
	 */
	public boolean isVisibleAtScale() {
		return visibleAtScale;
	}

	/**
	 * @param relativeScaleFactor
	 *            A double value representing the relative scale factor of a map.
	 * @return A boolean value that indicates whether or not this layer will be
	 *         drawn on the map at the specified relative scale factor.
	 */
	public boolean isVisibleAtScale(double relativeScaleFactor) {
		return isGreaterThanMinScale(relativeScaleFactor)
				&& isLessThanMaxScale(relativeScaleFactor);
	}

	/**
	 * @param id
	 *            The unique id of this layer.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param legend
	 *            A boolean value that indicates whether or not this layer will
	 *            be shown in the map's legend.
	 */
	public void setLegend(boolean legend) {
		this.legend = legend;
	}

	/**
	 * @param maxRelativeScaleFactor
	 *            The maximum relative scale factor at which this DFoittLayer
	 *            will be drawn on a map.
	 */
	public void setMaxRelativeScaleFactor(long maxRelativeScaleFactor) {
		this.maxRelativeScaleFactor = maxRelativeScaleFactor;
	}

	/**
	 * @param minRelativeScaleFactor
	 *            The minimum relative scale factor at which this DFoittLayer
	 *            will be drawn on a map.
	 */
	public void setMinRelativeScaleFactor(long minRelativeScaleFactor) {
		this.minRelativeScaleFactor = minRelativeScaleFactor;
	}

	/**
	 * @param name
	 *            A friendly name for this layer.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param visible
	 *            A boolean value that indicates whether or not this layer will
	 *            be drawn on the map.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @param visibleAtScale
	 *            A boolean value that indicates whether or not this layer will
	 *            be drawn on the map at the current map scale.
	 */
	public void setVisibleAtScale(boolean visibleAtScale) {
		this.visibleAtScale = visibleAtScale;
	}

	public String toString() {
		return this.getClass().getName() + ": id - " + id + " name - " + name;
	}
}
