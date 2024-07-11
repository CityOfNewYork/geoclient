package geoclientbuild.docs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

    private static final String CACHE_CONTROL_HEADER = "Cache-Control";
    private static final String CACHE_CONTROL_DEFAULT = "no-cache";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Endpoints endpoints;
    private Map<String, String> httpHeaders;

    public RestClient(String baseUri, Map<String, String> httpHeaders) {
        this.endpoints = new Endpoints(baseUri);
        this.httpHeaders = httpHeaders;
    }

    public String call(Request request) throws URISyntaxException, IOException, InterruptedException {
        if (!this.endpoints.contains(type)) {
            return String.format(Endpoints.RC_NOT_IMPLEMENTED_JSON_TEMPLATE, type);
        }
        Endpoint endpoint = endpoints.get(type);
        HttpGet httpGet = request.hasParameters() ? endpoint.httpGetRequest(request.getParameters()) : endpoint.httpGetRequest();
        addHttpHeaders(httpGet);
        return call(httpGet);
    }

    private void addHttpHeaders(HttpGet httpGet) {
        if(httpHeaders != null && !httpHeaders.isEmpty()) {
            httpHeaders.forEach((k, v) -> httpGet.addHeader(k, v));
        }
    }

    private String call(HttpGet httpGet) {
        try (CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = (CloseableHttpResponse) client
                        .execute(httpGet, () -> r)) {
            return EntityUtils.toString(response.getEntity());
        }
    }
}
