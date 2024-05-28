package net.pmolinav.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.pmolinav.dto.AuthCredentials;
import org.springframework.http.HttpHeaders;

import static org.junit.Assert.fail;

public class LoginStepDefsTest extends BaseSystemTest {

    private final String localURL = "http://localhost:8082";

    @Given("^invalid auth token$")
    public void givenInvalidAuthToken() {
        authToken = "invalidAuthToken";
    }

    @When("^an user with username (.*) and password (.*) tries to log in$")
    public void anUserTriesToLogIn(String user, String password) {
        try {
            executePost(localURL + "/authenticate",
                    objectMapper.writeValueAsString(new AuthCredentials(user, password)));
            authToken = authResponse.getHeader(HttpHeaders.AUTHORIZATION);
        } catch (Exception e) {
            fail();
        }
    }

}