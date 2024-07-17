package geoclientbuild.docs;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

// See https://github.com/eugenp/tutorials/blob/master/apache-httpclient/src/test/java/com/baeldung/httpclient/conn/HttpClientConnectionManagementLiveTest.java
public class DefaultHttpClientManager  implements HttpClientManager {

    private final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    @Override
    public CloseableHttpClient getCloseableHttpClient() throws InterruptedException, IOException {
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

}
