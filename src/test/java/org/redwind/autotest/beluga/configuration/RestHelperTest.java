package org.redwind.autotest.beluga.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.io.File;
import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class RestHelperTest {

    private static WireMockServer wireMockServer;
    private RestHelper restHelper;
    private URL testUrl;

    @BeforeAll
    static void setupServer() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        restHelper = new RestHelper();
        testUrl = new URL("http://localhost:8089/test");
    }

    @Test
    void testGet() {
        wireMockServer.stubFor(get(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"success\"}")));

        Header authHeader = new Header("Authorization", "Bearer dummy-token");

        Response response = restHelper.get(ContentType.JSON, testUrl, authHeader);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("success"));
    }

    @Test
    void testPost() throws Exception {
        wireMockServer.stubFor(post(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody("{\"status\":\"created\"}")));

        File dummyFile = File.createTempFile("test-body", ".json");
        dummyFile.deleteOnExit();
        Header authHeader = new Header("Authorization", "Bearer dummy-token");

        Response response = restHelper.post(ContentType.JSON, testUrl, authHeader, dummyFile);

        assertEquals(201, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("created"));
    }

    @Test
    void testDelete() {
        wireMockServer.stubFor(delete(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(204)));

        Header authHeader = new Header("Authorization", "Bearer dummy-token");

        Response response = restHelper.delete(testUrl, authHeader);

        assertEquals(204, response.getStatusCode());
    }
}
