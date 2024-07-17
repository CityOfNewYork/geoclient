package geoclientbuild.docs;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

public interface HttpClientManager {
    CloseableHttpClient getCloseableHttpClient() throws InterruptedException, IOException;
}
