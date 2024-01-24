//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.Role;
//import net.pmolinav.springboot.exception.NotFoundException;
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
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class GetUserByIdUnitTest extends BaseUnitTest {
//
//    long userId = 1L;
//    User expectedResult;
//    ResponseEntity<User> result;
//
//    @Test
//    void getUserByIdHappyPath() {
//        whenFindUserByIdReturnedAValidUser();
//        andGetAllUsersIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getUserByIdNotFound() {
//        whenFindUserByIdReturnedNoUser();
//        andGetUserByIdIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getUserByIdServerError() {
//        whenFindUserByIdReturnedAnException();
//        andGetUserByIdIsCalledWithException();
//        thenResultIsNull();
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
//    private void andGetAllUsersIsCalledInController() {
//        result = userController.getUserById(userId);
//    }
//
//    private void andGetUserByIdIsCalledWithNotFoundException() {
//        try {
//            result = userController.getUserById(userId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("User with id %s does not exist.", userId), notFoundException.getMessage());
//        }
//    }
//
//    private void andGetUserByIdIsCalledWithException() {
//        try {
//            result = userController.getUserById(userId);
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
