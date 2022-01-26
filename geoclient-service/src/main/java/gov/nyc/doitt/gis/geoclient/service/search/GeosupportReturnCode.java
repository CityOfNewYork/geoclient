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

import gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue;

public class GeosupportReturnCode {
    private String returnCode;
    private String reasonCode;
    private String message;

    public boolean isCompassDirectionRequired() {
        return ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.is(this.returnCode);
    }

    public boolean isRejected() {
        if (ReturnCodeValue.SUCCESS.is(this.returnCode) || ReturnCodeValue.WARN.is(this.returnCode)) {
            return false;
        }
        return true;
    }

    public boolean hasSimilarNames() {
        return ReturnCodeValue.NOT_RECOGNIZED_WITH_SIMILAR_NAMES.is(this.returnCode);
    }

    public boolean hasReasonCode() {
        return this.reasonCode != null;
    }

    public boolean hasMessage() {
        return this.message != null;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String geosupportReturnCode) {
        this.returnCode = geosupportReturnCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GeosupportReturnCode [returnCode=" + returnCode + ", reasonCode=" + reasonCode + ", message=" + message
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((reasonCode == null) ? 0 : reasonCode.hashCode());
        result = prime * result + ((returnCode == null) ? 0 : returnCode.hashCode());
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
        GeosupportReturnCode other = (GeosupportReturnCode) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (reasonCode == null) {
            if (other.reasonCode != null)
                return false;
        } else if (!reasonCode.equals(other.reasonCode))
            return false;
        if (returnCode == null) {
            if (other.returnCode != null)
                return false;
        } else if (!returnCode.equals(other.returnCode))
            return false;
        return true;
    }
}
