package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.service.domain.FieldSet;

/**
 * Selectively converts field values returned by specific Geosupport functions.
 * Relieves callers of the responsibility for knowing whether conversions are
 * required.
 *
 * Implmentations are typically configured {@link FieldSet}s to determine if
 * conversions are necessary and, if so, to what fields they should apply.
 *
 * @author Matthew Lipper
 * @since 2.0
 * @see FieldSet 
 */
public interface FieldSetConverter {
    void convert(String functionId, Map<String, Object> arguments);
}