//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.ActivityDTO;
//import net.pmolinav.springboot.dto.ActivityType;
//import net.pmolinav.springboot.exception.BadRequestException;
//import net.pmolinav.springboot.model.Activity;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class CreateActivityUnitTest extends BaseUnitTest {
//
//    ActivityDTO activityDTO;
//    Activity expectedResult;
//    ResponseEntity<Activity> result;
//
//    @Test
//    void createActivityHappyPath() {
//        givenValidActivityDTOForRequest(ActivityType.FOOTBALL, "someActivity", "someDescription", BigDecimal.TEN);
//        whenMapperWorkedAsExpected();
//        whenSaveActivityReturnedAValidActivity();
//        andCreateActivityIsCalledInController();
//        thenReceivedStatusIsCreated();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void createActivityServerError() {
//        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", BigDecimal.TEN);
//        whenSaveActivityReturnedAnException();
//        andCreateActivityIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void createActivityNoBodyBadRequest() {
//        andCreateActivityIsCalledWithBadRequestException("Body is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createActivityNoActivityTypeBadRequest() {
//        givenValidActivityDTOForRequest(null, "someActivity", "someDescription", BigDecimal.TEN);
//        andCreateActivityIsCalledWithBadRequestException("Activity type is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createActivityNoNameBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.TENNIS, null, "someDescription", BigDecimal.TEN);
//        andCreateActivityIsCalledWithBadRequestException("Activity name is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createActivityNoPriceBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", null);
//        andCreateActivityIsCalledWithBadRequestException("Activity price is mandatory and must be greater than zero.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createActivityZeroPriceBadRequest() {
//        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", BigDecimal.ZERO);
//        andCreateActivityIsCalledWithBadRequestException("Activity price is mandatory and must be greater than zero.");
//        thenResultIsNull();
//    }
//
//    private void givenValidActivityDTOForRequest(ActivityType type, String name, String description, BigDecimal price) {
//        activityDTO = new ActivityDTO(type, name, description, price);
//    }
//
//    private void whenMapperWorkedAsExpected() {
//        expectedResult = new Activity(1L, activityDTO.getType().name(), activityDTO.getName(),
//                activityDTO.getDescription(), BigDecimal.TEN);
//
//        when(activityMapperMock.activityDTOToActivityEntity(any())).thenReturn(expectedResult);
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
//    private void andCreateActivityIsCalledInController() {
//        result = activityController.createActivity(activityDTO);
//    }
//
//    private void andCreateActivityIsCalledWithBadRequestException(String expectedMessage) {
//        try {
//            result = activityController.createActivity(activityDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(expectedMessage, badRequestException.getMessage());
//        }
//    }
//
//    private void andCreateActivityIsCalledWithException() {
//        try {
//            result = activityController.createActivity(activityDTO);
//        } catch (IllegalArgumentException e) {
//            assertNull(e.getMessage());
//        }
//    }
//
//    private void thenReceivedStatusIsCreated() {
//        assertEquals(HttpStatus.CREATED, result.getStatusCode());
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
