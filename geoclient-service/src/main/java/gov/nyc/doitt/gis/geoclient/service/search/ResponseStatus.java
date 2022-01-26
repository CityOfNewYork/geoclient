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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.util.ArrayList;
import java.util.List;

public class ResponseStatus {
    private GeosupportReturnCode geosupportReturnCode = new GeosupportReturnCode();
    private GeosupportReturnCode geosupportReturnCode2 = new GeosupportReturnCode();
    private List<String> similarNames = new ArrayList<>();

    public boolean isCompassDirectionRequired() {
        return geosupportReturnCode.isCompassDirectionRequired();
    }

    public boolean isRejected() {
        boolean grcOneRejectedOrNull = this.geosupportReturnCode != null ? this.geosupportReturnCode.isRejected()
                : true;
        boolean grcTwoRejectedOrNull = this.geosupportReturnCode2 != null ? this.geosupportReturnCode2.isRejected()
                : true;
        return grcOneRejectedOrNull && grcTwoRejectedOrNull;
    }

    public int similarNamesCount() {
        return this.similarNames != null ? this.similarNames.size() : 0;
    }

    public GeosupportReturnCode getGeosupportReturnCode() {
        return geosupportReturnCode;
    }

    public void setGeosupportReturnCode(GeosupportReturnCode geosupportReturnCode) {
        this.geosupportReturnCode = geosupportReturnCode;
    }

    public GeosupportReturnCode getGeosupportReturnCode2() {
        return geosupportReturnCode2;
    }

    public void setGeosupportReturnCode2(GeosupportReturnCode geosupportReturnCode2) {
        this.geosupportReturnCode2 = geosupportReturnCode2;
    }

    public List<String> getSimilarNames() {
        return similarNames;
    }

    public void setSimilarNames(List<String> similarNames) {
        this.similarNames = similarNames;
    }

    @Override
    public String toString() {
        return "ResponseStatus [rc1=" + geosupportReturnCode + ", rc2=" + geosupportReturnCode2 + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geosupportReturnCode == null) ? 0 : geosupportReturnCode.hashCode());
        result = prime * result + ((geosupportReturnCode2 == null) ? 0 : geosupportReturnCode2.hashCode());
        result = prime * result + ((similarNames == null) ? 0 : similarNames.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponseStatus other = (ResponseStatus) obj;
        if (geosupportReturnCode == null) {
            if (other.geosupportReturnCode != null)
                return false;
        } else if (!geosupportReturnCode.equals(other.geosupportReturnCode))
            return false;
        if (geosupportReturnCode2 == null) {
            if (other.geosupportReturnCode2 != null)
                return false;
        } else if (!geosupportReturnCode2.equals(other.geosupportReturnCode2))
            return false;
        if (similarNames == null) {
            if (other.similarNames != null)
                return false;
        } else if (!similarNames.equals(other.similarNames))
            return false;
        return true;
    }

}
