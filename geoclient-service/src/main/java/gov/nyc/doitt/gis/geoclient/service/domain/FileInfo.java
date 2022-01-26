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

public class FileInfo extends BaseFileInfo {

    private String recordType;

    public FileInfo() {
        super();
    }

    public FileInfo(String recordType, String tag, String date, String release, String recordCount) {
        super(tag, date, release, recordCount);
        this.recordType = recordType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "FileInfo [recordType=" + recordType + ", tag=" + tag + ", date=" + date + ", release="
                + release + ", recordCount=" + recordCount + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((recordType == null) ? 0 : recordType.hashCode());
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
        FileInfo other = (FileInfo) obj;
        if (recordType == null) {
            if (other.recordType != null) {
                return false;
            }
        } else if (!recordType.equals(other.recordType)) {
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
            if (other.release != null){
                return false;
            }
        } else if (!release.equals(other.release)) {
            return false;
        }
        if (tag == null) {
            if (other.tag != null){
                return false;
            }
        } else if (!tag.equals(other.tag)) {
            return false;
        }
        return true;
    }

}
