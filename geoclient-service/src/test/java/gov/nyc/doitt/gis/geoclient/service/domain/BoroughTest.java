package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class BoroughTest
{

    @Test
    public void testParseInt()
    {
        assertThat(Borough.parseInt("1"), equalTo(1));
        assertThat(Borough.parseInt("2"), equalTo(2));
        assertThat(Borough.parseInt("3"), equalTo(3));
        assertThat(Borough.parseInt("4"), equalTo(4));
        assertThat(Borough.parseInt("5"), equalTo(5));
        assertThat(Borough.parseInt("12"), equalTo(12));
        assertThat(Borough.parseInt("-78"), equalTo(-78));
        assertThat(Borough.parseInt("Manhattan"), equalTo(1));
        assertThat(Borough.parseInt("MN"), equalTo(1));
        assertThat(Borough.parseInt("BRONX"), equalTo(2));
        assertThat(Borough.parseInt("bx"), equalTo(2));
        assertThat(Borough.parseInt("brooklyn"), equalTo(3));
        assertThat(Borough.parseInt("bk"), equalTo(3));
        assertThat(Borough.parseInt("qUeeNs"), equalTo(4));
        assertThat(Borough.parseInt("qN"), equalTo(4));
        assertThat(Borough.parseInt("staten island"), equalTo(5));
        assertThat(Borough.parseInt("STATEN IS"), equalTo(5));
        assertThat(Borough.parseInt("si"), equalTo(5));
        assertThat(Borough.parseInt("man"), equalTo(0));
    }

}
