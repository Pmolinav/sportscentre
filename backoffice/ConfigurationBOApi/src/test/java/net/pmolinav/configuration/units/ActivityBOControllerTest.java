package net.pmolinav.configuration.units;

import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.dto.ActivityType;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ActivityBOControllerTest extends BaseUnitTest {

    ActivityDTO activityDTO;
    List<Activity> expectedActivities;
    ResponseEntity<?> result;

    /* FIND ALL ACTIVITIES */
    @Test
    void findAllActivitiesHappyPath() {
        whenFindAllActivitiesInServiceReturnedValidActivities();
        andFindAllActivitiesIsCalledInController();
        thenVerifyFindAllActivitiesHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
        thenReceivedResponseBodyAsStringIs(String.valueOf(expectedActivities));
    }

    @Test
    void findAllActivitiesNotFound() {
        whenFindAllActivitiesInServiceThrowsNotFoundException();
        assertThrows(NotFoundException.class, this::andFindAllActivitiesIsCalledInController);
        thenVerifyFindAllActivitiesHasBeenCalledInService();
    }

    @Test
    void findAllActivitiesServerError() {
        whenFindAllActivitiesInServiceThrowsServerException();
        assertThrows(InternalServerErrorException.class, this::andFindAllActivitiesIsCalledInController);
        thenVerifyFindAllActivitiesHasBeenCalledInService();
    }

    /* CREATE ACTIVITY */
    @Test
    void createActivityHappyPath() {
        givenValidActivityDTOForRequest(ActivityType.FOOTBALL, "someActivity", "someDescription", 100);
        whenCreateActivityInServiceReturnedAValidActivity();
        andCreateActivityIsCalledInController();
        thenVerifyCreateActivityHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.CREATED);
        thenReceivedResponseBodyAsStringIs(String.valueOf(1));
    }

    @Test
    void createActivityServerError() {
        givenValidActivityDTOForRequest(ActivityType.PADDLE, "someActivity", "someDescription", 100);
        whenCreateActivityInServiceThrowsServerException();
        andCreateActivityIsCalledInController();
        thenVerifyCreateActivityHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* FIND ACTIVITY BY ID */
    @Test
    void findActivityByIdHappyPath() {
        whenFindActivityByIdInServiceReturnedValidActivities();
        andFindActivityByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
        thenReceivedResponseBodyAsStringIs(String.valueOf(expectedActivities.get(0)));
    }

    @Test
    void findActivityByIdNotFound() {
        whenFindActivityByIdInServiceThrowsNotFoundException();
        andFindActivityByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.NOT_FOUND);
    }

    @Test
    void findActivityByIdServerError() {
        whenFindActivityByIdInServiceThrowsServerException();
        andFindActivityByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* DELETE ACTIVITY */
    @Test
    void deleteActivityHappyPath() {
        whenDeleteActivityInServiceIsOk();
        andDeleteActivityIsCalledInController();
        thenVerifyDeleteActivityHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
    }

    @Test
    void deleteActivityNotFound() {
        whenDeleteActivityInServiceThrowsNotFoundException();
        andDeleteActivityIsCalledInController();
        thenVerifyDeleteActivityHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteActivityServerError() {
        whenDeleteActivityInServiceThrowsServerException();
        andDeleteActivityIsCalledInController();
        thenVerifyDeleteActivityHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void givenValidActivityDTOForRequest(ActivityType type, String name, String description, Integer price) {
        activityDTO = new ActivityDTO(type, name, description, price);
    }

    private void whenFindAllActivitiesInServiceReturnedValidActivities() {
        expectedActivities = List.of(
                new Activity(
                        1L, ActivityType.FOOTBALL.name(), "someActivity",
                        "someDescription", 100, new Date(), null),
                new Activity(
                        2L, ActivityType.GYM.name(), "otherActivity",
                        "otherDescription", 10, new Date(), null));

        when(activityBOServiceMock.findAllActivities()).thenReturn(expectedActivities);
    }

    private void whenFindAllActivitiesInServiceThrowsNotFoundException() {
        when(activityBOServiceMock.findAllActivities()).thenThrow(new NotFoundException("Not Found"));
    }

    private void whenFindAllActivitiesInServiceThrowsServerException() {
        when(activityBOServiceMock.findAllActivities())
                .thenThrow(new InternalServerErrorException("Internal Server Error"));
    }

    private void whenCreateActivityInServiceReturnedAValidActivity() {
        when(activityBOServiceMock.createActivity(any())).thenReturn(1L);
    }

    private void whenCreateActivityInServiceThrowsServerException() {
        when(activityBOServiceMock.createActivity(any(ActivityDTO.class)))
                .thenThrow(new CustomStatusException("Internal Server Error", 500));
    }

    private void whenFindActivityByIdInServiceReturnedValidActivities() {
        expectedActivities = List.of(
                new Activity(1L, ActivityType.FOOTBALL.name(), "someActivity",
                        "someDescription", 100, new Date(), null));

        when(activityBOServiceMock.findActivityById(1L)).thenReturn(expectedActivities.get(0));
    }

    private void whenFindActivityByIdInServiceThrowsNotFoundException() {
        when(activityBOServiceMock.findActivityById(1L)).thenThrow(new NotFoundException("Not Found"));
    }

    private void whenFindActivityByIdInServiceThrowsServerException() {
        when(activityBOServiceMock.findActivityById(1L))
                .thenThrow(new CustomStatusException("Internal Server Error", 500));
    }

    private void whenDeleteActivityInServiceIsOk() {
        doNothing().when(activityBOServiceMock).deleteActivity(anyLong());
    }

    private void whenDeleteActivityInServiceThrowsNotFoundException() {
        doThrow(new NotFoundException("Not Found"))
                .when(activityBOServiceMock)
                .deleteActivity(anyLong());
    }

    private void whenDeleteActivityInServiceThrowsServerException() {
        doThrow(new CustomStatusException("Internal Server Error", 500))
                .when(activityBOServiceMock)
                .deleteActivity(anyLong());
    }

    private void andFindAllActivitiesIsCalledInController() {
        result = activityBOController.findAllActivities(this.requestUid);
    }

    private void andFindActivityByIdIsCalledInController() {
        result = activityBOController.getActivityById(this.requestUid, 1L);
    }

    private void andCreateActivityIsCalledInController() {
        result = activityBOController.createActivity(this.requestUid, activityDTO);
    }

    private void andDeleteActivityIsCalledInController() {
        result = activityBOController.deleteActivity(this.requestUid, 1L);
    }

    private void thenVerifyFindAllActivitiesHasBeenCalledInService() {
        verify(activityBOServiceMock, times(1)).findAllActivities();
    }

    private void thenVerifyCreateActivityHasBeenCalledInService() {
        verify(activityBOServiceMock, times(1)).createActivity(any(ActivityDTO.class));
    }

    private void thenVerifyFindByIdHasBeenCalledInService() {
        verify(activityBOServiceMock, times(1)).findActivityById(anyLong());
    }

    private void thenVerifyDeleteActivityHasBeenCalledInService() {
        verify(activityBOServiceMock, times(1)).deleteActivity(anyLong());
    }

    private void thenReceivedStatusCodeIs(HttpStatus httpStatus) {
        assertEquals(httpStatus, result.getStatusCode());
    }

    private void thenReceivedResponseBodyAsStringIs(String expectedResult) {
        assertNotNull(result);
        assertEquals(expectedResult, String.valueOf(result.getBody()));
    }
}
