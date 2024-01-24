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
//class DeleteUserUnitTest extends BaseUnitTest {
//
//    long userId = 1L;
//    User expectedUser;
//    ResponseEntity<HttpStatus> result;
//
//    @Test
//    void deleteUserHappyPath() {
//        whenFindUserByIdReturnedAValidUser();
//        andDeleteUserIsCalledInController();
//        thenReceivedStatusIsOK();
//    }
//
//    @Test
//    void deleteUserNotFound() {
//        whenFindUserByIdReturnedNoUser();
//        andDeleteUserIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void deleteUserServerError() {
//        whenFindUserByIdReturnedAnException();
//        andDeleteUserIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindUserByIdReturnedAValidUser() {
//        expectedUser = new User(userId, "someUsername",
//                WebSecurityConfig.passwordEncoder().encode("somePassword"),
//                "someName", "some@email.com", Role.ADMIN.name());
//
//        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
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
//    private void andDeleteUserIsCalledInController() {
//        result = userController.deleteUser(userId);
//    }
//
//    private void andDeleteUserIsCalledWithNotFoundException() {
//        try {
//            result = userController.deleteUser(userId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("User with id %s does not exist.", userId), notFoundException.getMessage());
//        }
//    }
//
//    private void andDeleteUserIsCalledWithException() {
//        try {
//            result = userController.deleteUser(userId);
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
