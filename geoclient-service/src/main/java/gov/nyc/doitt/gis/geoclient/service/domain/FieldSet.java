package gov.nyc.doitt.gis.geoclient.service.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FieldSet {

    private final String functionId;
    private final Set<String> fieldNames;

    public FieldSet(String functionId, String... fieldNames) {
        this.functionId = functionId;
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
