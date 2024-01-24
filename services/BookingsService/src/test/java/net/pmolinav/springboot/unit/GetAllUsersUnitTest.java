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
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.when;
//
//class GetAllUsersUnitTest extends BaseUnitTest {
//
//    List<User> expectedResult;
//    ResponseEntity<List<User>> result;
//
//    @Test
//    void getAllUsersHappyPath() {
//        whenFindAllReturnedValidUsers();
//        andGetAllUsersIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getAllUsersNotFound() {
//        whenFindAllReturnedNoUsers();
//        andGetAllUsersIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getAllUsersServerError() {
//        whenFindAllReturnedAnException();
//        andGetAllUsersIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindAllReturnedValidUsers() {
//        User user1 = new User(1L, "someUsername",
//                WebSecurityConfig.passwordEncoder().encode("somePassword"),
//                "someName", "some@email.com", Role.ADMIN.name());
//
//        User user2 = new User(2L, "otherUsername",
//                WebSecurityConfig.passwordEncoder().encode("otherPassword"),
//                "otherName", "other@email.com", Role.USER.name());
//
//        expectedResult = List.of(user1, user2);
//
//        when(userRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedNoUsers() {
//        expectedResult = Collections.emptyList();
//
//        when(userRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedAnException() {
//        when(userRepositoryMock.findAll()).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andGetAllUsersIsCalledInController() {
//        result = userController.getAllUsers();
//    }
//
//    private void andGetAllUsersIsCalledWithNotFoundException() {
//        try {
//            result = userController.getAllUsers();
//        } catch (NotFoundException notFoundException) {
//            assertEquals("No users found.", notFoundException.getMessage());
//        }
//    }
//
//    private void andGetAllUsersIsCalledWithException() {
//        try {
//            result = userController.getAllUsers();
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
