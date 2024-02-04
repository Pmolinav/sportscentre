package net.pmolinav.bookings.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookings.mapper.ActivityMapper;
import net.pmolinav.bookings.repository.ActivityRepository;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.dto.ActivityType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@EnableJpaRepositories("net.pmolinav.bookings.repository")
@EntityScan("net.pmolinav.bookingslib.model")
//@Sql(scripts = "/db-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/db-cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ActivityControllerFunctionalTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void findAllActivitiesNotFound() throws Exception {
        mockMvc.perform(get("/activities"))
                .andExpect(status().isNotFound());

    }

    @Test
    void findAllActivitiesHappyPath() throws Exception {
        mockMvc.perform(get("/activities"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createActivityHappyPath() throws Exception {
        ActivityDTO requestDto = new ActivityDTO(ActivityType.GYM, "Gym", "Gym activity",
                BigDecimal.valueOf(25), new Date(), null);

        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.activityId").exists())
                .andExpect(jsonPath("$.type", is(ActivityType.GYM.name())))
                .andExpect(jsonPath("$.name", is("Gym")))
                .andExpect(jsonPath("$.description", is("Gym activity")))
                .andExpect(jsonPath("$.price", is(25.00)));
    }
}

