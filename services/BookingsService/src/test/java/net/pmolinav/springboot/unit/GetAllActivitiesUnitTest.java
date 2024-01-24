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
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.when;
//
//class GetAllActivitiesUnitTest extends BaseUnitTest {
//
//    List<Activity> expectedResult;
//    ResponseEntity<List<Activity>> result;
//
//    @Test
//    void getAllActivitiesHappyPath() {
//        whenFindAllReturnedValidActivities();
//        andGetAllActivitiesIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getAllActivitiesNotFound() {
//        whenFindAllReturnedNoActivities();
//        andGetAllActivitiesIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getAllActivitiesServerError() {
//        whenFindAllReturnedAnException();
//        andGetAllActivitiesIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindAllReturnedValidActivities() {
//        Activity activity1 = new Activity(1L, ActivityType.POOL.name(),
//                "somePoolActivity", "somePoolDescription", BigDecimal.ONE);
//        Activity activity2 = new Activity(1L, ActivityType.GYM.name(),
//                "someGymActivity", "someGymDescription", BigDecimal.TEN);
//
//        expectedResult = List.of(activity1, activity2);
//
//        when(activityRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedNoActivities() {
//        expectedResult = Collections.emptyList();
//
//        when(activityRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedAnException() {
//        when(activityRepositoryMock.findAll()).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andGetAllActivitiesIsCalledInController() {
//        result = activityController.getAllActivities();
//    }
//
//    private void andGetAllActivitiesIsCalledWithNotFoundException() {
//        try {
//            result = activityController.getAllActivities();
//        } catch (NotFoundException notFoundException) {
//            assertEquals("No activities found.", notFoundException.getMessage());
//        }
//    }
//
//    private void andGetAllActivitiesIsCalledWithException() {
//        try {
//            result = activityController.getAllActivities();
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
