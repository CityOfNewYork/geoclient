package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import gov.nyc.doitt.gis.geoclient.service.domain.FieldSet;

/**
 * Selectively converts latitude and longitude {@link String} values returned by
 * Geosupport to {@link Double} values. Assumes responsibility for knowing which
 * functions contain lat/long results targeted for conversion using
 * {@link FieldSet}s.
 * 
 * Currently implemented using a Spring {@link ConversionService} but if other
 * conversions become required, an interface should be extracted and used for
 * other field conversions.
 *
 * @author Matthew Lipper
 * @since 2.0
 * @see Converter
 * @see FieldSet
 * @see FieldSetConverter
 */
public class GeographicCoordinateConverter implements FieldSetConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeographicCoordinateConverter.class);

    // @Autowired
    private ConversionService conversionService;
    private Map<String, FieldSet> fieldSets;

    public GeographicCoordinateConverter(ConversionService conversionService, List<FieldSet> fieldSets) {
        this.conversionService = conversionService;
        this.fieldSets = new HashMap<>();
        for (FieldSet fieldSet : fieldSets) {
            this.fieldSets.put(fieldSet.getFunctionId(), fieldSet);
        }
    }

    @Override
    public void convert(String functionId, Map<String, Object> arguments) {
        LOGGER.debug("Checking if function {} has fields requiring conversion...");
        if(this.fieldSets.containsKey(functionId)){
            FieldSet fieldSet = this.fieldSets.get(functionId);
            LOGGER.debug("Converting function {} fields {} from Strings to Doubles...", functionId, fieldSet.getFieldNames());
            convert(arguments, fieldSet.getFieldNames());
        }
    }

    protected void convert(Map<String, Object> arguments, Set<String> fieldNames) {
        for (String name : fieldNames) {
            LOGGER.debug("Checking for field name {}...", name);
            if (arguments.containsKey(name)) {
                Object value = arguments.get(name);
                LOGGER.debug("Current value of field {}: {}", name, value);
                if (value != null) {
                    String stringValue = value.toString();
                    Double doubleValue = convert(stringValue);
                    arguments.put(name, doubleValue);
                    LOGGER.debug("Converted field {} from String: {} to Double: {}", name, stringValue, doubleValue);
                }
            }
        }
    }

    private Double convert(String coordinateString) {
        return this.conversionService.convert(coordinateString, Double.class);
    }
}
