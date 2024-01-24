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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class CreateUserUnitTest extends BaseUnitTest {
//
//    UserDTO userDTO;
//    User expectedResult;
//    ResponseEntity<User> result;
//
//    @Test
//    void createUserHappyPath() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
//        whenMapperWorkedAsExpected();
//        whenSaveUserReturnedAValidUser();
//        andCreateUserIsCalledInController();
//        thenReceivedStatusIsCreated();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void createUserServerError() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", "some@email.com", Role.ADMIN);
//        whenSaveUserReturnedAnException();
//        andCreateUserIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void createUserNoBodyBadRequest() {
//        andCreateUserIsCalledWithBadRequestException("Body");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createUserNoUsernameBadRequest() {
//        givenValidUserDTOForRequest(null, "somePassword", "someName", "some@email.com", Role.USER);
//        andCreateUserIsCalledWithBadRequestException("Username");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createUserNoPasswordBadRequest() {
//        givenValidUserDTOForRequest("someUsername", null, "someName", "some@email.com", Role.VIEWER);
//        andCreateUserIsCalledWithBadRequestException("Password");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createUserNoNameBadRequest() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", null, "some@email.com", Role.ADMIN);
//        andCreateUserIsCalledWithBadRequestException("Name");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createUserNoEmailBadRequest() {
//        givenValidUserDTOForRequest("someUsername", "somePassword", "someName", null, Role.USER);
//        andCreateUserIsCalledWithBadRequestException("Email");
//        thenResultIsNull();
//    }
//
//    private void givenValidUserDTOForRequest(String username, String password, String name, String email, Role role) {
//        userDTO = new UserDTO(username, password, name, email, role);
//    }
//
//    private void whenMapperWorkedAsExpected() {
//        expectedResult = new User(1L, userDTO.getUsername(),
//                WebSecurityConfig.passwordEncoder().encode(userDTO.getPassword()),
//                userDTO.getName(), userDTO.getEmail(), userDTO.getRole().name());
//
//        when(userMapperMock.userDTOToUserEntity(any())).thenReturn(expectedResult);
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
//    private void andCreateUserIsCalledInController() {
//        result = userController.createUser(userDTO);
//    }
//
//    private void andCreateUserIsCalledWithBadRequestException(String emptyValue) {
//        try {
//            result = userController.createUser(userDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(String.format("%s is mandatory.", emptyValue), badRequestException.getMessage());
//        }
//    }
//
//    private void andCreateUserIsCalledWithException() {
//        try {
//            result = userController.createUser(userDTO);
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
