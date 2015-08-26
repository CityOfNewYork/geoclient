package gov.nyc.doitt.gis.geoclient.function;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class GeosupportConfigurationTest
{

    @Test
    public void testRequiredArguments()
    {
        DefaultConfiguration config = new DefaultConfiguration();
        assertNull(config.requiredArguments());
        Map<String, Object> args = new HashMap<String, Object>();
        config.setRequiredArguments(args);
        assertNotSame(args, config.requiredArguments());
        assertEquals(args, config.requiredArguments());
    }

}
