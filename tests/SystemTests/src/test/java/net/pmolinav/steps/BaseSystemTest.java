package net.pmolinav.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import net.pmolinav.HeaderSettingRequestCallback;
import net.pmolinav.ResponseResults;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.database.SportsCentreDatabaseConnector;
import net.pmolinav.systemtests.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CucumberContextConfiguration
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSystemTest {

    @Autowired
    protected RestTemplate restTemplate;
    protected static SportsCentreDatabaseConnector dbConnector;
    protected final static ObjectMapper objectMapper = new ObjectMapper();
    protected static ResponseResults authResponse = null;
    protected static ResponseResults latestResponse = null;
    protected static String authToken;
    protected static User lastUser;
    protected static Activity lastActivity;
    protected static Booking lastBooking;

    protected void executeGet(String url) {
        executeGet(url, UUID.randomUUID().toString());
    }

    void executeGet(String url, String requestUid) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(null, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url + "?requestUid=" + requestUid, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hasError) {
                return errorHandler.getResult();
            } else {
                return new ResponseResults(response);
            }
        });
    }

    protected void executePost(String url, String body) {
        executePost(url, body, UUID.randomUUID().toString());
    }

    protected void executePost(String url, String body, String requestUid) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(body, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
                .execute(url + "?requestUid=" + requestUid, HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.hasError) {
                        return (errorHandler.getResult());
                    } else {
                        return new ResponseResults(response);
                    }
                });
        if (url.contains("/login") && latestResponse != null) {
            authResponse = new ResponseResults(latestResponse.getResponse(),
                    latestResponse.getStatus(),
                    latestResponse.getBody(),
                    latestResponse.getHeaders());
        }
    }

    protected void executeDelete(String url) {
        executeDelete(url, UUID.randomUUID().toString());
    }

    void executeDelete(String url, String requestUid) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(null, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url + "?requestUid=" + requestUid, HttpMethod.DELETE, requestCallback, response -> {
            if (errorHandler.hasError) {
                return (errorHandler.getResult());
            } else {
                return new ResponseResults(response);
            }
        });
    }

    private static class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults result = null;
        private Boolean hasError = false;

        private ResponseResults getResult() {
            return result;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hasError = response.getStatusCode().isError();
            return hasError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // If unauthorized request, we will only need the response status code.
            if (response.getStatusCode().value() == 401) {
                result = new ResponseResults(response, 401, null, null);
            } else {
                result = new ResponseResults(response);
            }
        }
    }
}