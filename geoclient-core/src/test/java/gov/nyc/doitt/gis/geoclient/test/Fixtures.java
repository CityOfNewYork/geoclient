package gov.nyc.doitt.gis.geoclient.test;

import gov.nyc.doitt.gis.geoclient.config.GeoclientXmlReader;
import gov.nyc.doitt.gis.geoclient.config.xml.ConfigurationConverter;
import gov.nyc.doitt.gis.geoclient.config.xml.FieldConverter;

public class Fixtures
{
    private final ConfigurationConverter.Metadata configurationConverterMetadata = GeoclientXmlReader.getConfigurationConverterMetadata();
    private final FieldConverter.Metadata fieldConverterMetadata = GeoclientXmlReader.getFieldConverterMetadata();
    
    public ConfigurationConverter.Metadata configurationConverterMetadata()
    {
        return configurationConverterMetadata;
    }
    
    public FieldConverter.Metadata fieldConverterMetadata()
    {
        return fieldConverterMetadata;
    }

}
