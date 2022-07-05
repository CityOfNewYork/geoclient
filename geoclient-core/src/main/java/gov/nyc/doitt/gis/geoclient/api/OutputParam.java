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

public class OutputParam {
    public static final String HR_DSNAME = "dsname";
    public static final int HR_DSNAME_COUNT = 20;
    public static final String HR_FILE_APEQUIV = "apequiv";
    public static final String HR_FILE_AUXSEG = "auxseg";
    public static final String HR_FILE_GEO = "geo";
    public static final int HR_FILE_GEO_COUNT = 9;
    public static final String HR_FILE_GRID1R = "grid1R";
    public static final String HR_FILE_INFO_DATE = "FileDate";
    public static final String HR_FILE_INFO_RECORD_COUNT = "FileRecordCount";
    public static final String HR_FILE_INFO_RECORD_TYPE = "FileRecordType";
    public static final String HR_FILE_INFO_RELEASE = "FileRelease";
    public static final String HR_FILE_INFO_TAG = "FileTag";
    public static final String HR_FILE_THIN = "thin";
    public static final String HR_FILE_THIN_FILLER = "Filler";
    public static final int HR_FILE_THIN_FILLER_COUNT = 3;
    public static final int HR_FILE_THIN_REC_TYPE_COUNT = 3;
    public static final String HR_FILE_TPAD = "tpad";
    public static final String XCOORD = "xCoordinate";
    public static final String XCOORD_INTERNAL_LABEL = "internalLabelXCoordinate";
    public static final String YCOORD = "yCoordinate";
    public static final String YCOORD_INTERNAL_LABEL = "internalLabelYCoordinate";

    // Return code information is from WA1 output fields
    public static final String GEOSUPPORT_RETURN_CODE = "geosupportReturnCode";
    public static final String GEOSUPPORT_RETURN_CODE2 = "geosupportReturnCode2";
    public static final String MESSAGE = "message";
    public static final String MESSAGE2 = "message2";
    public static final String REASON_CODE = "reasonCode";
    public static final String REASON_CODE2 = "reasonCode2";
    public static final String REASON_CODE_QUALIFIER = "reasonCodeQualifier";
    public static final String REASON_CODE_QUALIFIER2 = "reasonCodeQualifier2";

    private OutputParam() {
    }

}
