package net.pmolinav.configuration.functionals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import net.pmolinav.configuration.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EntityScan("net.pmolinav.bookingslib.model")
class UserBOControllerFunctionalTest extends AbstractContainerBaseTest {

    //TODO: Review how to mock Authorization
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAllUsersNotFound() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllUsersHappyPath() throws Exception {
        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        List<User> userResponseList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<User>>() {
                });

        Assertions.assertEquals(2, userResponseList.size());
    }

    @Test
    void createUserHappyPath() throws Exception {
        UserDTO requestDto = new UserDTO("someUser", "somePassword", "someName",
                "some@email.com", Role.USER, new Date(), new Date());

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody, matchesPattern("\\d+"));
    }

    @Test
    void findUserByIdNotFound() throws Exception {
        mockMvc.perform(get("/users/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findUserByIdHappyPath() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/3"))
                .andExpect(status().isOk())
                .andReturn();

        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<User>() {
                });

        Assertions.assertEquals(3L, userResponse.getUserId());
    }

    @Test
    void deleteUserByIdNotFound() throws Exception {
        mockMvc.perform(delete("/users/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserByIdHappyPath() throws Exception {
        mockMvc.perform(delete("/users/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/users/6"))
                .andExpect(status().isOk());
    }

}

