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
import java.util.HashSet;

public class PolygonGeometry extends GeometryImpl {
    private Collection<ExteriorPolygon> exteriorPolygons;

    public PolygonGeometry() {
    }

    public PolygonGeometry(Collection<ExteriorPolygon> exteriorPolygons) {
        this.exteriorPolygons = exteriorPolygons;
    }

    public Collection<ExteriorPolygon> getExteriorPolygons() {
        return exteriorPolygons;
    }

    public void setExteriorPolygons(Collection<ExteriorPolygon> exteriorPolygons) {
        this.exteriorPolygons = exteriorPolygons;
    }

    @Override
    public Collection<DoittPoint> getAllPoints() {
        Collection<DoittPoint> all = new HashSet<DoittPoint>();
        if (exteriorPolygons == null) {
            return all;
        }

        for (ExteriorPolygon extPol : exteriorPolygons) {
            DoittPoint[] extPts = extPol.getExteriorPoints();
            for (int i = 0; extPts != null && i < extPts.length; i++) {
                all.add(extPts[i]);
            }
        }
        return all;
    }
}
