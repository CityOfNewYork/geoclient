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
package gov.nyc.doitt.gis.geometry.transform;

import org.geotools.util.factory.Hints;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 *
 * Factory class for creating specific Transformer instances with the
 * GeoToolsTransformer class.
 *
 * A note on lat/long from:
 * http://docs.openlayers.org/library/spherical_mercator.html
 *
 * Projections in GIS are commonly referred to by their EPSG codes, identifiers
 * managed by the European Petroleum Survey Group. One common identifier is
 * EPSG:4326, which describes maps where latitude and longitude are treated as
 * X/Y values. Spherical Mercator has an official designation of EPSG:3857.
 * However, before this was established, a large amount of software used the
 * identifier EPSG:900913. This is an unofficial code, but is still the commonly
 * used code in OpenLayers. Any time you see the string EPSG:4326, you can
 * assume it describes latitude/longitude coordinates. Any time you see the
 * string EPSG:900913, it will be describing coordinates in meters in x/y.
 *
 * @author mlipper
 */
public class TransformerFactory {
    public static final String LAT_LONG_DEGREES_EPSG_NUM = "EPSG:4326";
    public static final String NYSP_FEET_EPSG_NUM = "EPSG:2263";
    static final String NYSP_REFID = "EPSG:NAD83 / New York Long Island (ftUS)";
    static final String LATLONG_REFID = "EPSG:WGS 84";

    public Transformer nyspToLatLong() throws CrsConfigurationException {
        Hints.putSystemDefault(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        return getInstance(NYSP_FEET_EPSG_NUM, LAT_LONG_DEGREES_EPSG_NUM);
    }

    public Transformer latLongToNysp() throws CrsConfigurationException {
        Hints.putSystemDefault(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        return getInstance(LAT_LONG_DEGREES_EPSG_NUM, NYSP_FEET_EPSG_NUM);
    }

    public Transformer getInstance(String sourceCrs, String targetCrs) throws CrsConfigurationException {
        return new GeoToolsTransformer(getCoordinateReferenceSystem(sourceCrs),
                getCoordinateReferenceSystem(targetCrs));
    }

    public CoordinateReferenceSystem getCoordinateReferenceSystem(String epsgNumber) throws CrsConfigurationException {
        try {
            return CRS.decode(epsgNumber);
        } catch (FactoryException e) {
            throw new CrsConfigurationException("Error decoding EPSG number " + epsgNumber, e);
        }
    }
}
