package net.pmolinav.bookings.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookings.mapper.ActivityMapper;
import net.pmolinav.bookings.repository.ActivityRepository;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.dto.ActivityType;
import net.pmolinav.bookingslib.model.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@EnableJpaRepositories("net.pmolinav.bookings.repository")
@EntityScan("net.pmolinav.bookingslib.model")
class ActivityControllerFunctionalTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Sql(scripts = "/cleanup-activities.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllActivitiesNotFound() throws Exception {
        mockMvc.perform(get("/activities"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/cleanup-activities.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllActivitiesHappyPath() throws Exception {
        givenSomeActivitiesPreviouslyStoredWithIds(1, 2);
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
    @Sql(scripts = "/cleanup-activities.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findActivityByIdHappyPath() throws Exception {
        givenSomeActivitiesPreviouslyStoredWithIds(3, 4);
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
        givenSomeActivitiesPreviouslyStoredWithIds(5, 6);

        mockMvc.perform(delete("/activities/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/activities/6"))
                .andExpect(status().isOk());
    }

    private void givenSomeActivitiesPreviouslyStoredWithIds(long id, long id2) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {

                String insertQuery = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                        "VALUES (" + id + ", 'FOOTBALL', 'Football', 'Football activity', 25, '2024-01-01 08:00:00', '2024-01-01 08:00:00');";
                statement.executeUpdate(insertQuery);

                String insertQuery2 = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                        "VALUES (" + id2 + ", 'TENNIS', 'Tennis', 'Tennis Activity', 15.50, '2023-01-02 10:00:00', '2024-01-02 10:00:00');";
                statement.executeUpdate(insertQuery2);
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}

