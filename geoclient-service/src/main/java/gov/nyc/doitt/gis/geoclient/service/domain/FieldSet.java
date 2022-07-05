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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Immutable data class for defining a set of field names associated with a
 * particular function.
 *
 * @author Matthew Lipper
 * @since 2.0
 */
public class FieldSet {

    private final String functionId;
    private final Set<String> fieldNames;

    /**
     * Creates an immutable FieldSet using a function identifier and a variable
     * number of field names.
     *
     * @param functionId Function identifier which cannot be null
     * @param fieldNames Names of fields which cannot be null
     * @throws IllegalArgumentException if either functionId or fieldNames arguments are null.
     */
    public FieldSet(String functionId, String... fieldNames) {
        if(null == functionId) {
            throw new IllegalArgumentException("functionId argument cannot be null.");
        }
        this.functionId = functionId;
        if(null == fieldNames) {
            throw new IllegalArgumentException("fieldNames argument cannot be null.");
        }
        this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
    }

    public String getFunctionId() {
        return functionId;
    }

    public Set<String> getFieldNames() {
        return fieldNames;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldNames == null) ? 0 : fieldNames.hashCode());
        result = prime * result + ((functionId == null) ? 0 : functionId.hashCode());
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
        FieldSet other = (FieldSet) obj;
        if (fieldNames == null) {
            if (other.fieldNames != null)
                return false;
        } else if (!fieldNames.equals(other.fieldNames))
            return false;
        if (functionId == null) {
            if (other.functionId != null)
                return false;
        } else if (!functionId.equals(other.functionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FieldSet [functionId=" + functionId + ", fieldNames=" + fieldNames + "]";
    }

}
