package geoclientbuild.docs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

public class Endpoint {

    private final String name;
    private final String baseUri;

    /**
     * Shared endpoint request configuration.
     *
     * @param name
     * @param baseUri
     */
    public Endpoint(String name, String baseUri) {
        this.name = name;
        this.baseUri = baseUri;
    }

    public HttpGet httpGetRequest(Map<String, String> parameters) {
        return buildHttpGet(parameters);
    }

    public HttpGet httpGetRequest() {
        return buildHttpGet();
    }

    public String getName() {
        return name;
    }

    List<NameValuePair> queryParams(Map<String, String> parameters) {
        List<NameValuePair> params = new ArrayList<>();
        parameters.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
        return params;
    }

    HttpGet buildHttpGet(Map<String, String> parameters) {
        HttpGet httpGet = buildUri();
        URI uri = new URIBuilder(httpGet.getUri()).addParameters(queryParams(parameters)).build();
        httpGet.setUri(uri);
        return httpGet;
    }

    HttpGet buildHttpGet() {
        String uriString = this.baseUri + "/" + this.name;
        try {
            return new HttpGet(uriString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((baseUri == null) ? 0 : baseUri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Endpoint other = (Endpoint) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (baseUri == null) {
            if (other.baseUri != null)
                return false;
        } else if (!baseUri.equals(other.baseUri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Endpoint [" + name + ": " + baseUri + "/" + name + "]";
    }

}
