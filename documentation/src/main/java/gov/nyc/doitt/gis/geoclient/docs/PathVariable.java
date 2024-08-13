/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.docs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides constants for path variables and their associated endpoints.
 *
 * @author mlipper
 */
public enum PathVariable {

    ADDRESS("address"),
    ADDRESS_POINT("addresspoint"),
    BBL("bbl"),
    BIN("bin"),
    BLOCKFACE("blockface"),
    INTERSECTION("intersection"),
    PLACE("place"),
    SEARCH("search"),
    STREETCODE("streetcode"),
    NORMALIZE("normalize"),
    VERSION("version");

    private static final Map<String, PathVariable> BY_PATH_STRING = new HashMap<>();

    static {
        for (PathVariable uv : values()) {
            BY_PATH_STRING.put(uv.pathString, uv);
        }
    }

    private final String pathString;

    private PathVariable(String pathString) {
        this.pathString = pathString;
    }

    public static PathVariable fromString(String string) {
        if (!BY_PATH_STRING.containsKey(string)) {
            throw new IllegalArgumentException(String.format("Unrecognized UriVariable#pathString: %s", string));
        }
        return BY_PATH_STRING.get(string);
    }

    public static Set<String> stringValues() {
        return BY_PATH_STRING.keySet();
    }

    @Override
    public String toString() {
        return this.pathString;
    }
}
