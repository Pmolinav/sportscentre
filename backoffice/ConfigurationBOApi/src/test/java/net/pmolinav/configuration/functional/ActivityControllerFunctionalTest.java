package net.pmolinav.configuration.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.dto.ActivityType;
import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.ActivityClient;
import net.pmolinav.configuration.client.UserClient;
import net.pmolinav.configuration.mapper.ActivityMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EntityScan("net.pmolinav.bookingslib.model")
class ActivityControllerFunctionalTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityClient activityClient;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private static UserClient userClient;

    @Test
    void findAllActivitiesNotFound() throws Exception {
        when(userClient.findUserByUsername(anyString()))
                .thenReturn(new User(1L, username, password, "someUser",
                        "some@email.com", Role.ADMIN.name(), new Date(), null));

        String userRequest = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        mockMvc.perform(get("/activities")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllActivitiesHappyPath() throws Exception {
        MvcResult result = mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andReturn();

        List<Activity> activityResponseList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Activity>>() {
                });

        Assertions.assertEquals(2, activityResponseList.size());
        Assertions.assertEquals(ActivityType.FOOTBALL.name(), activityResponseList.get(0).getType());
        Assertions.assertEquals(ActivityType.TENNIS.name(), activityResponseList.get(1).getType());
    }

    @Test
    void createActivityHappyPath() throws Exception {
        ActivityDTO requestDto = new ActivityDTO(ActivityType.GYM, "Gym", "Gym activity",
                BigDecimal.valueOf(25), new Date(), null);

        MvcResult result = mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody, matchesPattern("\\d+"));
    }

    @Test
    void findActivityByIdNotFound() throws Exception {
        mockMvc.perform(get("/activities/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findActivityByIdHappyPath() throws Exception {
        MvcResult result = mockMvc.perform(get("/activities/3"))
                .andExpect(status().isOk())
                .andReturn();

        Activity activityResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<Activity>() {
                });

        Assertions.assertEquals(ActivityType.FOOTBALL.name(), activityResponse.getType());
        Assertions.assertEquals(BigDecimal.valueOf(25).longValue(), activityResponse.getPrice().longValue());
    }

    @Test
    void deleteActivityByIdNotFound() throws Exception {
        mockMvc.perform(delete("/activities/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteActivityByIdHappyPath() throws Exception {
        mockMvc.perform(delete("/activities/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/activities/6"))
                .andExpect(status().isOk());
    }
}

