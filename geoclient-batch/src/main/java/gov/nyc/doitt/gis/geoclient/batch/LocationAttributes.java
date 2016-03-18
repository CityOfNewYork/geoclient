package gov.nyc.doitt.gis.geoclient.batch;

import java.util.Map;

public interface LocationAttributes {
	Map<String, Object> getAttributes();
	void setAttributes(Map<String, Object> attributes);
}
