package gov.nyc.doitt.gis.geoclient.parser.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

class AssertTest {

    @Test
    void testHasText() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText(" o ", "bar");
    }

    @Test
    void testHasText_null() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText((String)null, "bar"), "bar");
    }

    @Test
    void testHasText_empty() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText("", "bar"), "bar");
    }

    @Test
    void testHasText_whitespaceOnly() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText("  ", "bar"), "bar");
    }

    @Test
    void testIsTrue() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.isTrue(2 > 1, "bar");
    }

    @Test
    void testIsTrue_false() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.isTrue(1 > 1, "bar"), "bar");
    }

    @Test
    void testNotEmpty() {
        Collection<String> collection = new ArrayList<>();
        collection.add("foo");
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar");
    }

    @Test
    void testNotEmpty_null() {
        Collection<String> collection = null;
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar"), "bar");

    }

    @Test
    void testNotEmpty_empty() {
        Collection<String> collection = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar"), "bar");
    }

    @Test
    void testNotNull() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.notNull(new Object(), "bar");
    }

    @Test
    void testNotNull_null() {
        assertThrows(IllegalArgumentException.class,
                () ->  gov.nyc.doitt.gis.geoclient.parser.util.Assert.notNull((Object)null, "bar"), "bar");
    }

}
