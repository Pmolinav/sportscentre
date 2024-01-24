//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.ActivityType;
//import net.pmolinav.springboot.exception.NotFoundException;
//import net.pmolinav.springboot.model.Activity;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class GetActivityByIdUnitTest extends BaseUnitTest {
//
//    long activityId = 1L;
//    Activity expectedResult;
//    ResponseEntity<Activity> result;
//
//    @Test
//    void getActivityByIdHappyPath() {
//        whenFindActivityByIdReturnedAValidActivity();
//        andGetAllActivitiesIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getActivityByIdNotFound() {
//        whenFindActivityByIdReturnedNoActivity();
//        andGetActivityByIdIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getActivityByIdServerError() {
//        whenFindActivityByIdReturnedAnException();
//        andGetActivityByIdIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindActivityByIdReturnedAValidActivity() {
//        expectedResult = new Activity(activityId, ActivityType.GYM.name(),
//                "someName", "someDescription", BigDecimal.TEN);
//
//        when(activityRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedResult));
//    }
//
//    private void whenFindActivityByIdReturnedNoActivity() {
//        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());
//    }
//
//    private void whenFindActivityByIdReturnedAnException() {
//        when(activityRepositoryMock.findById(activityId)).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andGetAllActivitiesIsCalledInController() {
//        result = activityController.getActivityById(activityId);
//    }
//
//    private void andGetActivityByIdIsCalledWithNotFoundException() {
//        try {
//            result = activityController.getActivityById(activityId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("Activity with id %s does not exist.", activityId), notFoundException.getMessage());
//        }
//    }
//
//    private void andGetActivityByIdIsCalledWithException() {
//        try {
//            result = activityController.getActivityById(activityId);
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
