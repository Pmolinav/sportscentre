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
//class DeleteActivityUnitTest extends BaseUnitTest {
//
//    long activityId = 1L;
//    Activity expectedActivity;
//    ResponseEntity<HttpStatus> result;
//
//    @Test
//    void deleteActivityHappyPath() {
//        whenFindActivityByIdReturnedAValidActivity();
//        andDeleteActivityIsCalledInController();
//        thenReceivedStatusIsOK();
//    }
//
//    @Test
//    void deleteActivityNotFound() {
//        whenFindActivityByIdReturnedNoActivity();
//        andDeleteActivityIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void deleteActivityServerError() {
//        whenFindActivityByIdReturnedAnException();
//        andDeleteActivityIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindActivityByIdReturnedAValidActivity() {
//        expectedActivity = new Activity(activityId, ActivityType.POOL.name(),
//                "someName", "someDescription", BigDecimal.TEN);
//
//        when(activityRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedActivity));
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
//    private void andDeleteActivityIsCalledInController() {
//        result = activityController.deleteActivity(activityId);
//    }
//
//    private void andDeleteActivityIsCalledWithNotFoundException() {
//        try {
//            result = activityController.deleteActivity(activityId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("Activity with id %s does not exist.", activityId), notFoundException.getMessage());
//        }
//    }
//
//    private void andDeleteActivityIsCalledWithException() {
//        try {
//            result = activityController.deleteActivity(activityId);
//        } catch (IllegalArgumentException e) {
//            assertNull(e.getMessage());
//        }
//    }
//
//    private void thenReceivedStatusIsOK() {
//        assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    private void thenResultIsNull() {
//        assertNull(result);
//    }
//}
