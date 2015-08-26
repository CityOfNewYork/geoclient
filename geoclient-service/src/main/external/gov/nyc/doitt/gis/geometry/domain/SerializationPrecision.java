package gov.nyc.doitt.gis.geometry.domain;

import java.text.DecimalFormat;

public abstract class SerializationPrecision implements Units {
	private static final DecimalFormat FORMATTER = new DecimalFormat();
	private int units = FEET;
	
	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getDecimalPlaces(){
		switch (units) {
		case PIXELS:
			return 0;
		case FEET:
			return 1;
		case METERS:
			return 2;
		case DECIMAL_DEGREES:
			return 4;
		default:
			return 4;
		}
	}
	
	public Double getValueWithPrecision(Double value){
		FORMATTER.setMinimumFractionDigits(0);
		FORMATTER.setMaximumFractionDigits(getDecimalPlaces());
		FORMATTER.setGroupingUsed(false);
		return new Double(FORMATTER.format(value));
	}
}
