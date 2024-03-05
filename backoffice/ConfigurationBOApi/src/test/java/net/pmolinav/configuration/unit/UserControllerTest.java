package net.pmolinav.configuration.unit;

import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserControllerTest extends BaseUnitTest {

    UserDTO userDTO;
    List<User> expectedUsers;
    ResponseEntity<?> result;

    /* FIND ALL USERS */
    @Test
    void findAllUsersHappyPath() {
        whenFindAllUsersInServiceReturnedValidUsers();
        andFindAllUsersIsCalledInController();
        thenVerifyFindAllUsersHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
        thenReceivedResponseBodyAsStringIs(String.valueOf(expectedUsers));
    }

    @Test
    void findAllUsersNotFound() {
        whenFindAllUsersInServiceThrowsNotFoundException();
        andFindAllUsersIsCalledInController();
        thenVerifyFindAllUsersHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.NOT_FOUND);
    }

    @Test
    void findAllUsersServerError() {
        whenFindAllUsersInServiceThrowsServerException();
        andFindAllUsersIsCalledInController();
        thenVerifyFindAllUsersHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* CREATE USER */
    @Test
    void createUserHappyPath() {
        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
        whenCreateUserInServiceReturnedAValidUser();
        andCreateUserIsCalledInController();
        thenVerifyCreateUserHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.CREATED);
        thenReceivedResponseBodyAsStringIs(String.valueOf(1));
    }

    @Test
    void createUserServerError() {
        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
        whenCreateUserInServiceThrowsServerException();
        andCreateUserIsCalledInController();
        thenVerifyCreateUserHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* FIND USER BY ID */
    @Test
    void findUserByIdHappyPath() {
        whenFindUserByIdInServiceReturnedValidUser();
        andFindUserByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
        thenReceivedResponseBodyAsStringIs(String.valueOf(expectedUsers.get(0)));
    }

    @Test
    void findUserByIdNotFound() {
        whenFindUserByIdInServiceThrowsNotFoundException();
        andFindUserByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.NOT_FOUND);
    }

    @Test
    void findUserByIdServerError() {
        whenFindUserByIdInServiceThrowsServerException();
        andFindUserByIdIsCalledInController();
        thenVerifyFindByIdHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* FIND USER BY USERNAME - NOT ALLOWED IN CONTROLLER. ONLY INTERNAL REQUEST*/

    /* DELETE USER */
    @Test
    void deleteUserHappyPath() {
        whenDeleteUserInServiceIsOk();
        andDeleteUserIsCalledInController();
        thenVerifyDeleteUserHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.OK);
    }

    @Test
    void deleteUserNotFound() {
        whenDeleteUserInServiceThrowsNotFoundException();
        andDeleteUserIsCalledInController();
        thenVerifyDeleteUserHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteUserServerError() {
        whenDeleteUserInServiceThrowsServerException();
        andDeleteUserIsCalledInController();
        thenVerifyDeleteUserHasBeenCalledInService();
        thenReceivedStatusCodeIs(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void givenValidUserDTOForRequest(String username, String password, String name, String email, Role role) {
        userDTO = new UserDTO(username, password, name, email, role, new Date(), null);
    }

    private void whenFindAllUsersInServiceReturnedValidUsers() {
        expectedUsers = List.of(
                new User(1L, "someUser", "somePassword", "someName",
                        "some@email.com", Role.USER.name(), new Date(), null),
                new User(2L, "someUser", "somePassword", "someName",
                        "some@email.com", Role.USER.name(), new Date(), null));

        when(userBOServiceMock.findAllUsers()).thenReturn(expectedUsers);
    }

    private void whenFindAllUsersInServiceThrowsNotFoundException() {
        when(userBOServiceMock.findAllUsers()).thenThrow(new NotFoundException("Not Found"));
    }

    private void whenFindAllUsersInServiceThrowsServerException() {
        when(userBOServiceMock.findAllUsers())
                .thenThrow(new UnexpectedException("Internal Server Error", 500));
    }

    private void whenCreateUserInServiceReturnedAValidUser() {
        when(userBOServiceMock.createUser(any())).thenReturn(1L);
    }

    private void whenCreateUserInServiceThrowsServerException() {
        when(userBOServiceMock.createUser(any(UserDTO.class)))
                .thenThrow(new UnexpectedException("Internal Server Error", 500));
    }

    private void whenFindUserByIdInServiceReturnedValidUser() {
        expectedUsers = List.of(
                new User(1L, "someUser", "somePassword", "someName",
                        "some@email.com", Role.USER.name(), new Date(), null));

        when(userBOServiceMock.findUserById(1L)).thenReturn(expectedUsers.get(0));
    }

    private void whenFindUserByIdInServiceThrowsNotFoundException() {
        when(userBOServiceMock.findUserById(1L)).thenThrow(new NotFoundException("Not Found"));
    }

    private void whenFindUserByIdInServiceThrowsServerException() {
        when(userBOServiceMock.findUserById(1L))
                .thenThrow(new UnexpectedException("Internal Server Error", 500));
    }

    private void whenFindUserByUsernameInServiceReturnedValidUser() {
        expectedUsers = List.of(
                new User(1L, "someUser", "somePassword", "someName",
                        "some@email.com", Role.USER.name(), new Date(), null));

        when(userBOServiceMock.findUserByUsername(eq(expectedUsers.get(0).getUsername()))).thenReturn(expectedUsers.get(0));
    }

    private void whenDeleteUserInServiceIsOk() {
        doNothing().when(userBOServiceMock).deleteUser(anyLong());
    }

    private void whenDeleteUserInServiceThrowsNotFoundException() {
        doThrow(new NotFoundException("Not Found"))
                .when(userBOServiceMock)
                .deleteUser(anyLong());
    }

    private void whenDeleteUserInServiceThrowsServerException() {
        doThrow(new UnexpectedException("Internal Server Error", 500))
                .when(userBOServiceMock)
                .deleteUser(anyLong());
    }

    private void andFindAllUsersIsCalledInController() {
        result = userController.findAllUsers(this.requestUid);
    }

    private void andFindUserByIdIsCalledInController() {
        result = userController.getUserById(this.requestUid, 1L);
    }

    private void andCreateUserIsCalledInController() {
        result = userController.createUser(this.requestUid, userDTO);
    }

    private void andDeleteUserIsCalledInController() {
        result = userController.deleteUser(this.requestUid, 1L);
    }

    private void thenVerifyFindAllUsersHasBeenCalledInService() {
        verify(userBOServiceMock, times(1)).findAllUsers();
    }

    private void thenVerifyCreateUserHasBeenCalledInService() {
        verify(userBOServiceMock, times(1)).createUser(any(UserDTO.class));
    }

    private void thenVerifyFindByIdHasBeenCalledInService() {
        verify(userBOServiceMock, times(1)).findUserById(anyLong());
    }

    private void thenVerifyDeleteUserHasBeenCalledInService() {
        verify(userBOServiceMock, times(1)).deleteUser(anyLong());
    }

    private void thenReceivedStatusCodeIs(HttpStatus httpStatus) {
        assertEquals(httpStatus, result.getStatusCode());
    }

    private void thenReceivedResponseBodyAsStringIs(String expectedResult) {
        assertNotNull(result);
        assertEquals(expectedResult, String.valueOf(result.getBody()));
    }
}
