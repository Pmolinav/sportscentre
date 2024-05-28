package net.pmolinav.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import net.pmolinav.HeaderSettingRequestCallback;
import net.pmolinav.ResponseResults;
import net.pmolinav.SpringDemoApplication;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.database.SportsCentreDatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringDemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSystemTest {

    @Autowired
    protected RestTemplate restTemplate;
    protected SportsCentreDatabaseConnector dbConnector;
    protected final static ObjectMapper objectMapper = new ObjectMapper();
    protected static ResponseResults authResponse = null;
    protected static ResponseResults latestResponse = null;
    protected static String authToken;
    protected static User lastUser;
    protected static Activity lastActivity;
    protected static Booking lastBooking;

    @After

    private void cleanAllAfterTests() {
        try {
            dbConnector = new SportsCentreDatabaseConnector();
            dbConnector.deleteUsers();
            dbConnector.deleteActivities();
            dbConnector.deleteBookings();
        } catch (SQLException e) {
            fail();
        }
    }

    protected void executeGet(String url) {
        executeGet(url, UUID.randomUUID().toString());
    }

    void executeGet(String url, String requestUid) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);
        Map<String, String> queryParams = Map.of("requestUid", requestUid);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(queryParams, null, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return errorHandler.getResults();
            } else {
                return new ResponseResults(response);
            }
        });
    }

    protected void executePost(String url, String body) throws IOException {
        executePost(url, body, UUID.randomUUID().toString());
    }

    protected void executePost(String url, String body, String requestUid) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);
        Map<String, String> queryParams = Map.of("requestUid", requestUid);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(queryParams, body, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
                .execute(url, HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.hadError) {
                        return (errorHandler.getResults());
                    } else {
                        return new ResponseResults(response);
                    }
                });
        if (url.equals("/authenticate") && latestResponse != null) {
            authResponse = new ResponseResults(latestResponse.getResponse());
        }
    }

    protected void executeDelete(String url) {
        executeDelete(url, UUID.randomUUID().toString());
    }

    void executeDelete(String url, String requestUid) {
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, authToken);
        Map<String, String> queryParams = Map.of("requestUid", requestUid);

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(queryParams, null, headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.DELETE, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return new ResponseResults(response);
            }
        });
    }

    @Then("^received status code is (\\d+)$")
    public void receivedStatusCodeIs(int expectedStatusCode) throws IOException {
        HttpStatus currentStatusCode = latestResponse.getResponse().getStatusCode();
        assertEquals(expectedStatusCode, currentStatusCode.value());
    }

    private static class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getStatusCode().value() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response);
        }
    }
}