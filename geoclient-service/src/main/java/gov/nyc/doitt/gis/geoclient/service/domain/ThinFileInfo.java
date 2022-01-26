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

import java.util.List;

public class ThinFileInfo extends BaseFileInfo {
    private List<String> recordTypes;
    private List<String> fillerFields;

    public ThinFileInfo() {
        super();
    }

    public ThinFileInfo(List<String> recordTypes, String tag, String date, String release, String recordCount,
            List<String> fillerFields) {
        super(tag, date, release, recordCount);
        this.recordTypes = recordTypes;
        this.fillerFields = fillerFields;
    }

    public List<String> getRecordTypes() {
        return recordTypes;
    }

    public void setRecordTypes(List<String> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public List<String> getFillerFields() {
        return fillerFields;
    }

    public void setFillerFields(List<String> fillerFields) {
        this.fillerFields = fillerFields;
    }

    @Override
    public String toString() {
        return "ThinFileInfo [recordTypes=" + recordTypes + ", fillerFields=" + fillerFields + ", tag=" + tag
                + ", date=" + date + ", release=" + release + ", recordCount=" + recordCount + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((recordTypes == null) ? 0 : recordTypes.hashCode());
        result = prime * result + ((fillerFields == null) ? 0 : fillerFields.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((recordCount == null) ? 0 : recordCount.hashCode());
        result = prime * result + ((release == null) ? 0 : release.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ThinFileInfo other = (ThinFileInfo) obj;
        if (recordTypes == null) {
            if (other.recordTypes != null) {
                return false;
            }
        } else if (!recordTypes.equals(other.recordTypes)) {
            return false;
        }
        if (fillerFields == null) {
            if (other.fillerFields != null) {
                return false;
            }
        } else if (!fillerFields.equals(other.fillerFields)) {
            return false;
        }
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        if (recordCount == null) {
            if (other.recordCount != null) {
                return false;
            }
        } else if (!recordCount.equals(other.recordCount)) {
            return false;
        }
        if (release == null) {
            if (other.release != null) {
                return false;
            }
        } else if (!release.equals(other.release)) {
            return false;
        }
        if (tag == null) {
            if (other.tag != null) {
                return false;
            }
        } else if (!tag.equals(other.tag)) {
            return false;
        }
        return true;
    }

}
