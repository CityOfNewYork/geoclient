package gov.nyc.doitt.gis.geoclient.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Boroughs {

    public static final Borough MANHATTAN = new Manhattan();
    public static final Borough BRONX = new Bronx();
    public static final Borough BROOKLYN = new Brooklyn();
    public static final Borough QUEENS = new Queens();
    public static final Borough STATEN_ISLAND = new StatenIsland();

    public static final List<Borough> THE_FIVE_BOROUGHS = Arrays.asList(MANHATTAN, BRONX, BROOKLYN, QUEENS,
            STATEN_ISLAND);

    public static final Borough fromCode(String code) {
        return findFirst((Borough b) -> b.getCode().equalsIgnoreCase(code));
    }

    public static final Borough fromName(String name) {
        return findFirst((Borough b) -> b.getName().equalsIgnoreCase(name));
    }

    public static final Borough findFirst(Predicate<? super Borough> predicate) {
        Optional<Borough> result = THE_FIVE_BOROUGHS.stream().filter(predicate).findFirst();
        return result.orElse(null);
    }

    public static class Manhattan extends Borough {
        public Manhattan() {
            super("1", "Manhattan");
        }
    }

    public static class Bronx extends Borough {
        public Bronx() {
            super("2", "Bronx");
        }
    }

    public static class Brooklyn extends Borough {
        public Brooklyn() {
            super("3", "Brooklyn");
        }
    }

    public static class Queens extends Borough {
        public Queens() {
            super("4", "Queens");
        }
    }

    public static class StatenIsland extends Borough {
        public StatenIsland() {
            super("5", "Staten Island");
        }
    }

    private Boroughs() {
    }
}
