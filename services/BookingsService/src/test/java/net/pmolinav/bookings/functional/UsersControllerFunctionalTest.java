package net.pmolinav.bookings.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookings.mapper.UserMapper;
import net.pmolinav.bookings.repository.UserRepository;
import net.pmolinav.bookings.security.WebSecurityConfig;
import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@EnableJpaRepositories("net.pmolinav.bookings.repository")
@EntityScan("net.pmolinav.bookingslib.model")
class UsersControllerFunctionalTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAllUsersNotFound() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllUsersHappyPath() throws Exception {
        givenSomeUsersPreviouslyStoredWithIds(1, 2);
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
        givenSomeUsersPreviouslyStoredWithIds(1, 2);
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
        givenSomeUsersPreviouslyStoredWithIds(3, 4);
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
        givenSomeUsersPreviouslyStoredWithIds(5, 6);

        mockMvc.perform(delete("/users/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/users/6"))
                .andExpect(status().isOk());
    }

    private void givenSomeUsersPreviouslyStoredWithIds(long id, long id2) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {

                String insertUserQuery = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                        "VALUES (" + id + ", 'someUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'John Doe', 'john@example.com', 'ADMIN', '2024-02-14 10:00:00', NULL);";
                statement.executeUpdate(insertUserQuery);

                String insertUserQuery2 = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                        "VALUES (" + id2 + ", 'otherUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'Jane Smith', 'jane@example.com', 'ADMIN', '2024-02-14 10:30:00', '2024-02-14 11:15:00');";
                statement.executeUpdate(insertUserQuery2);
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}

