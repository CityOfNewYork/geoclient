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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.math.NumberUtils;

public class Boroughs {

    public static final Borough MANHATTAN = new Manhattan();
    public static final Borough BRONX = new Bronx();
    public static final Borough BROOKLYN = new Brooklyn();
    public static final Borough QUEENS = new Queens();
    public static final Borough STATEN_ISLAND = new StatenIsland();

    public static final int UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE = 0;

    public static final List<Borough> THE_FIVE_BOROUGHS = Arrays.asList(MANHATTAN, BRONX, BROOKLYN, QUEENS,
            STATEN_ISLAND);

    // TODO Decide if this method should trim whitespace (currently, does not)
    public static final Borough fromCode(String code) {
        return findFirst((Borough b) -> b.getCode().equalsIgnoreCase(code));
    }

    public static final Borough fromName(String name) {
        return findFirst((Borough b) -> b.isAlsoKnownAs(name));
    }

    // Originally from gov.nyc.doitt.gis.geoclient.service.domain.Borough in the geoclient-service project.
    public static int parseInt(String boroughCodeOrName)
    {
        // TODO revisit this logic?
        // If argument is a parse-able integer, return it regardless of validity as an actual borough code
        if (NumberUtils.isParsable(boroughCodeOrName))
        {
            return Integer.valueOf(boroughCodeOrName);
        }

        Borough borough = fromName(boroughCodeOrName);

        if(borough != null) {
            return Integer.valueOf(borough.getCode());
        }

        return UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE;
    }

    public static final Borough findFirst(Predicate<? super Borough> predicate) {
        Optional<Borough> result = THE_FIVE_BOROUGHS.stream().filter(predicate).findFirst();
        return result.orElse(null);
    }

    public static class Manhattan extends Borough {
        public Manhattan() {
            super("1", "MANHATTAN", new String[] { "MN" });
        }
    }

    public static class Bronx extends Borough {
        public Bronx() {
            super("2", "BRONX", new String[] { "BX" });
        }
    }

    public static class Brooklyn extends Borough {
        public Brooklyn() {
            super("3", "BROOKLYN", new String[] { "BK" });
        }
    }

    public static class Queens extends Borough {
        public Queens() {
            super("4", "QUEENS", new String[] { "QN" });
        }
    }

    public static class StatenIsland extends Borough {
        public StatenIsland() {
            super("5", "STATEN ISLAND", new String[] { "SI", "STATEN IS" });
        }
    }

    private Boroughs() {
    }
}
