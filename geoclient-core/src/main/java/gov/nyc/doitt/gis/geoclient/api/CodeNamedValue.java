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

public class CodeNamedValue implements Named, Coded {

    private final String code;
    private final String name;
    private final boolean caseSensitive;

    public CodeNamedValue(String code, String name) {
        this(code, name, true);
    }

    public CodeNamedValue(String code, String name, boolean caseSensitive) {
        super();
        this.code = code;
        this.name = name;
        this.caseSensitive = caseSensitive;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    private String codeForHashCodeAndEquals() {
        if (this.code != null && !isCaseSensitive()) {
            return this.code.toLowerCase();
        }
        return this.code;
    }

    private String nameForHashCodeAndEquals() {
        if (this.name != null && !isCaseSensitive()) {
            return this.name.toLowerCase();
        }
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        String n = nameForHashCodeAndEquals();
        String c = codeForHashCodeAndEquals();
        result = prime * result + ((c == null) ? 0 : c.hashCode());
        result = prime * result + ((n == null) ? 0 : n.hashCode());
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
        CodeNamedValue other = (CodeNamedValue) obj;
        String n = nameForHashCodeAndEquals();
        String c = codeForHashCodeAndEquals();
        if (c == null) {
            if (other.codeForHashCodeAndEquals() != null)
                return false;
        } else if (!c.equals(other.codeForHashCodeAndEquals()))
            return false;
        if (n == null) {
            if (other.nameForHashCodeAndEquals() != null)
                return false;
        } else if (!n.equals(other.nameForHashCodeAndEquals()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CodeNamedValue [code=" + code + ", name=" + name + "]";
    }
}
