package net.pmolinav.configuration.functionals;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import net.pmolinav.configuration.security.AuthCredentials;
import net.pmolinav.configuration.security.WebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EntityScan("net.pmolinav.bookingslib.model")
class LoginBOControllerFunctionalTest extends AbstractContainerBaseTest {

    //TODO: Review how to mock Feign Client
    private AuthCredentials request;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserClient userClient;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loginHappyPath() throws Exception {
        giveSomeValidRequest();
        giveSomeUserFromClientOK();

        String regex = "^Bearer\\s[a-zA-Z0-9-_.]+$";

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.request)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, matchesRegex(regex)));
    }

    @Test
    void loginBadRequest() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUnauthorized() throws Exception {
        giveSomeValidRequest();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthCredentials("fakeUser", "fakePass"))))
                .andExpect(status().isUnauthorized());
    }

    private void giveSomeValidRequest() {
        this.request = new AuthCredentials("Admin", "Admin");
    }

    private void giveSomeUserFromClientOK() {
        User returnedUser = new User(1L,
                this.request.getUsername(),
                WebSecurityConfig.passwordEncoder().encode(this.request.getPassword()),
                "somename",
                "soem@email.com",
                Role.USER.name(),
                new Date(),
                null);
        when(userClient.findUserByUsername(anyString())).thenReturn(returnedUser);
        //Mock with stubFor
//        stubFor(get(urlEqualTo("/users/brauls/repos"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/vnd.github.v3+json")
//                        .withBody(jsonBody)));
    }
}

