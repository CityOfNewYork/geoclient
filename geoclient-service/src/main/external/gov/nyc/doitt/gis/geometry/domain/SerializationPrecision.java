/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + units;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SerializationPrecision other = (SerializationPrecision) obj;
        if (units != other.units)
            return false;
        return true;
    }

}
