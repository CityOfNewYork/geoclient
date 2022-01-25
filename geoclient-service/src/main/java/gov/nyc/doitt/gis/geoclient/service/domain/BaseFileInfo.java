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
package gov.nyc.doitt.gis.geoclient.service.domain;

import java.text.SimpleDateFormat;

class BaseFileInfo {

    private static final SimpleDateFormat STRING_TO_DATE = new SimpleDateFormat("yyMMdd");
    private static final SimpleDateFormat DATE_TO_STRING = new SimpleDateFormat("yyyy-MM-dd");

    protected String tag;
    protected String date;
    protected String release;
    protected String recordCount;

    public BaseFileInfo() {
    }

    public BaseFileInfo(String tag, String date, String release, String recordCount) {
        super();
        this.tag = tag;
        this.date = date;
        this.release = release;
        this.recordCount = recordCount;
    }

    public String getFormattedDate() {
        return applyFormat(this.date);
    }

    String applyFormat(String dateString) {
        try {
            return DATE_TO_STRING.format(STRING_TO_DATE.parse(dateString));
        } catch (Exception ignored) {
            return dateString;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }
}
