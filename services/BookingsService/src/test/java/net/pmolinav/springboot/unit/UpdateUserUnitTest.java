//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.Role;
//import net.pmolinav.springboot.dto.UserDTO;
//import net.pmolinav.springboot.exception.BadRequestException;
//import net.pmolinav.springboot.model.User;
//import net.pmolinav.springboot.security.WebSecurityConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class UpdateUserUnitTest extends BaseUnitTest {
//
//    long userId = 1L;
//    UserDTO userDTO;
//    User expectedResult;
//    ResponseEntity<User> result;
//
//    @Test
//    void updateUserHappyPath() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
//        whenFindUserByIdReturnedAValidUser();
//        whenSaveUserReturnedAValidUser();
//        andUpdateUserIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void updateUserWithNoRoleOK() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", null);
//        whenFindUserByIdReturnedAValidUser();
//        whenSaveUserReturnedAValidUser();
//        andUpdateUserIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void updateUserServerError() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
//        whenFindUserByIdReturnedAValidUser();
//        whenSaveUserReturnedAnException();
//        andUpdateUserIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateUserNoBodyBadRequest() {
//        whenFindUserByIdReturnedAValidUser();
//        andUpdateUserIsCalledWithBadRequestException("Body");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateUserNoUsernameBadRequest() {
//        givenValidUserDTOForRequest(null, "somePassword", "someName", "some@email.com", Role.USER);
//        whenFindUserByIdReturnedAValidUser();
//        andUpdateUserIsCalledWithBadRequestException("Username");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateUserNoPasswordBadRequest() {
//        givenValidUserDTOForRequest("someUsername", null, "someName", "some@email.com", Role.VIEWER);
//        whenFindUserByIdReturnedAValidUser();
//        andUpdateUserIsCalledWithBadRequestException("Password");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateUserNoNameBadRequest() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", null, "some@email.com", Role.ADMIN);
//        whenFindUserByIdReturnedAValidUser();
//        andUpdateUserIsCalledWithBadRequestException("Name");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateUserNoEmailBadRequest() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", null, Role.USER);
//        whenFindUserByIdReturnedAValidUser();
//        andUpdateUserIsCalledWithBadRequestException("Email");
//        thenResultIsNull();
//    }
//
//    private void givenValidUserDTOForRequest(String username, String password, String name, String email, Role role) {
//        userDTO = new UserDTO(username, password, name, email, role);
//    }
//
//    private void whenFindUserByIdReturnedAValidUser() {
//        expectedResult = new User(userId, "someUsername",
//                WebSecurityConfig.passwordEncoder().encode("somePassword"),
//                "someName", "some@email.com", Role.ADMIN.name());
//
//        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedResult));
//    }
//
//    private void whenFindUserByIdReturnedNoUser() {
//        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());
//    }
//
//    private void whenFindUserByIdReturnedAnException() {
//        when(userRepositoryMock.findById(userId)).thenThrow(new IllegalArgumentException());
//    }
//
//    private void whenSaveUserReturnedAValidUser() {
//        when(userRepositoryMock.save(any())).thenReturn(expectedResult);
//    }
//
//    private void whenSaveUserReturnedAnException() {
//        when(userRepositoryMock.save(any())).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andUpdateUserIsCalledInController() {
//        result = userController.updateUser(userId, userDTO);
//    }
//
//    private void andUpdateUserIsCalledWithBadRequestException(String emptyValue) {
//        try {
//            result = userController.updateUser(userId, userDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(String.format("%s is mandatory.", emptyValue), badRequestException.getMessage());
//        }
//    }
//
//    private void andUpdateUserIsCalledWithException() {
//        try {
//            result = userController.updateUser(userId, userDTO);
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
