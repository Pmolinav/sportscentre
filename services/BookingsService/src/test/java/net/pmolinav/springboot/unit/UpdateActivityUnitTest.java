//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.ActivityType;
//import net.pmolinav.springboot.dto.Role;
//import net.pmolinav.springboot.dto.ActivityDTO;
//import net.pmolinav.springboot.exception.BadRequestException;
//import net.pmolinav.springboot.model.Activity;
//import net.pmolinav.springboot.security.WebSecurityConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class UpdateActivityUnitTest extends BaseUnitTest {
//
//    long activityId = 1L;
//    ActivityDTO activityDTO;
//    Activity expectedResult;
//    ResponseEntity<Activity> result;
//
//    @Test
//    void updateActivityHappyPath() {
//        givenValidActivityDTOForRequest(ActivityType.FOOTBALL, "someActivity", "someDescription", BigDecimal.TEN);
//        whenFindActivityByIdReturnedAValidActivity();
//        whenSaveActivityReturnedAValidActivity();
//        andUpdateActivityIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void updateActivityServerError() {
//        givenValidActivityDTOForRequest(ActivityType.GYM, "someActivity", "someDescription", BigDecimal.TEN);
//        whenFindActivityByIdReturnedAValidActivity();
//        whenSaveActivityReturnedAnException();
//        andUpdateActivityIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateActivityNoBodyBadRequest() {
//        whenFindActivityByIdReturnedAValidActivity();
//        andUpdateActivityIsCalledWithBadRequestException("Body is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateActivityNoActivityTypeBadRequest() {
//        givenValidActivityDTOForRequest(null, "someActivity", "someDescription", BigDecimal.TEN);
//        whenFindActivityByIdReturnedAValidActivity();
//        andUpdateActivityIsCalledWithBadRequestException("Activity type is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateActivityNoNameBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.TENNIS, null, "someDescription", BigDecimal.TEN);
//        whenFindActivityByIdReturnedAValidActivity();
//        andUpdateActivityIsCalledWithBadRequestException("Activity name is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateActivityNoPriceBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", null);
//        whenFindActivityByIdReturnedAValidActivity();
//        andUpdateActivityIsCalledWithBadRequestException("Activity price is mandatory and must be greater than zero.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateActivityZeroPrizeBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", BigDecimal.ZERO);
//        whenFindActivityByIdReturnedAValidActivity();
//        andUpdateActivityIsCalledWithBadRequestException("Activity price is mandatory and must be greater than zero.");
//        thenResultIsNull();
//    }
//
//    private void givenValidActivityDTOForRequest(ActivityType type, String name, String description, BigDecimal price) {
//        activityDTO = new ActivityDTO(type, name, description, price);
//    }
//
//    private void whenFindActivityByIdReturnedAValidActivity() {
//        expectedResult = new Activity(activityId, ActivityType.POOL.name(),
//                "someName", "someDescription", BigDecimal.TEN);
//
//        when(activityRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedResult));
//    }
//    private void whenFindActivityByIdReturnedNoActivity() {
//        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());
//    }
//
//    private void whenFindActivityByIdReturnedAnException() {
//        when(activityRepositoryMock.findById(activityId)).thenThrow(new IllegalArgumentException());
//    }
//
//    private void whenSaveActivityReturnedAValidActivity() {
//        when(activityRepositoryMock.save(any())).thenReturn(expectedResult);
//    }
//
//    private void whenSaveActivityReturnedAnException() {
//        when(activityRepositoryMock.save(any())).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andUpdateActivityIsCalledInController() {
//        result = activityController.updateActivity(activityId, activityDTO);
//    }
//
//    private void andUpdateActivityIsCalledWithBadRequestException(String expectedMessage) {
//        try {
//            result = activityController.updateActivity(activityId, activityDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(expectedMessage, badRequestException.getMessage());
//        }
//    }
//
//    private void andUpdateActivityIsCalledWithException() {
//        try {
//            result = activityController.updateActivity(activityId, activityDTO);
//        } catch (IllegalArgumentException e) {
//            assertNull(e.getMessage());
//        }
//    }
//
//    private void thenReceivedStatusIsOK() {
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//    }
//
//    private void thenReceivedResponseAsExpected() {
//        assertEquals(expectedResult, result.getBody());
//    }
//
//    private void thenResultIsNull() {
//        assertNull(result);
//    }
//}
