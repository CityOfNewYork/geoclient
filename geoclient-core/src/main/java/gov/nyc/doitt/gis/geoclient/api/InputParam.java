/*
 * Copyright 2013-2022 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.api;


public class InputParam
{
    public static final String BBL_BOROUGH_CODE = "bblBoroughCode";
    public static final String BBL_TAX_BLOCK = "bblTaxBlock";
    public static final String BBL_TAX_LOT = "bblTaxLot";
    public static final String BIN = "bin";
    public static final String BOROUGH_CODE = "boroughCode";
    public static final String BOROUGH_CODE2 = "boroughCode2";
    public static final String BOROUGH_CODE3 = "boroughCode3";
    public static final String COMPASS_DIRECTION = "compassDirection";
    public static final String COMPASS_DIR_NORTH_VALUE = "N";
    public static final String COMPASS_DIR_SOUTH_VALUE = "S";
    public static final String COMPASS_DIR_EAST_VALUE = "E";
    public static final String COMPASS_DIR_WEST_VALUE = "W";
    public static final String CROSS_STREET_NAMES_FLAG = "crossStreetNamesFlag";
    public static final String CROSS_STREET_NAMES_FLAG_VALUE = "E";
    public static final String GEOSUPPORT_FUNCTION_CODE = "geosupportFunctionCode";
    public static final String HOUSE_NUMBER = "houseNumber";
    public static final String MODE_SWITCH = "modeSwitch";
    public static final String MODE_SWITCH_EXTENDED_VALUE = "X";
    public static final String STREET_CODE = "streetCode";
    public static final String STREET_CODE2 = "streetCode2";
    public static final String STREET_CODE3 = "streetCode3";
    public static final String STREET_NAME = "streetName";
    public static final String STREET_NAME2 = "streetName2";
    public static final String STREET_NAME3 = "streetName3";
    public static final String NORMALIZATION_FORMAT = "streetNameNormalizationFormatFlag";
    public static final String NORMALIZATION_FORMAT_COMPACT_VALUE = "C";
    public static final String NORMALIZATION_FORMAT_SORT_VALUE = "S"; // Default if format unspecified
    public static final String NORMALIZATION_LENGTH = "streetNameNormalizationLengthLimit"; // Defaults to maximum value of 32 if unspecified
    public static final String WORK_AREA_FORMAT_INDICATOR = "workAreaFormatIndicator";
    public static final String WORK_AREA_FORMAT_INDICATOR_VALUE = "C";
    public static final String ZIP_CODE = "zipCode";
    private InputParam(){}
}
