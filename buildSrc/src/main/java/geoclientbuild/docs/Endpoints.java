package geoclientbuild.docs;

import java.util.HashMap;
import java.util.Map;

public class Endpoints {

    public static final String ADDRESS = "address";
    public static final String SEARCH = "search";
    public static final String VERSION = "version";
    public static final String RC_NOT_IMPLEMENTED_JSON_TEMPLATE = "{ \"returnCode\": \"EE\", \"message\": \"Endpoint %s has not been implemented.\" }";

    private final String baseUri;
    private final Map<String, Endpoint> endpoints = new HashMap<>();

    /**
     * @param baseUri
     */
    public Endpoints(String baseUri) {
        this.baseUri = baseUri;
        endpoints.put(ADDRESS, new Endpoint(ADDRESS, baseUri));
        endpoints.put(SEARCH, new Endpoint(SEARCH, baseUri));
        endpoints.put(VERSION, new Endpoint(VERSION, baseUri));
    }

    public String getBaseUri() {
        return baseUri;
    }

    public boolean contains(String name) {
        return this.endpoints.containsKey(name);
    }

    public Endpoint get(String name) {
        return this.endpoints.get(name);
    }

    public Endpoint getAddress() {
        return endpoints.get(ADDRESS);
    }

    public Endpoint getSearch() {
        return endpoints.get(SEARCH);
    }

    public Endpoint getVersion() {
        return endpoints.get(VERSION);
    }


}
