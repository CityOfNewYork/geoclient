/*
 * Copyright 2013-2019 the original author or authors.
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

import java.util.Collection;
import java.util.Collections;

public class PointGeometry extends GeometryImpl {
	private DoittPoint point;

	public PointGeometry() {
	}

	public PointGeometry(DoittPoint point) {
		this.point = point;
	}

	public DoittPoint getPoint() {
		return point;
	}

	public void setPoint(DoittPoint point) {
		this.point = point;
	}

	@Override
	public Collection<DoittPoint> getAllPoints() {
		return Collections.singleton(point);
	}
	
	@Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        PointGeometry other = (PointGeometry) obj;
        if (point == null)
        {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }
	
	
}
