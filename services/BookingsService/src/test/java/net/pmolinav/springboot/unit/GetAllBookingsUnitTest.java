//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.BookingStatus;
//import net.pmolinav.springboot.exception.NotFoundException;
//import net.pmolinav.springboot.model.Booking;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.when;
//
//class GetAllBookingsUnitTest extends BaseUnitTest {
//
//    List<Booking> expectedResult;
//    ResponseEntity<List<Booking>> result;
//
//    @Test
//    void getAllBookingsHappyPath() {
//        whenFindAllReturnedValidBookings();
//        andGetAllBookingsIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getAllBookingsNotFound() {
//        whenFindAllReturnedNoBookings();
//        andGetAllBookingsIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getAllUsersServerError() {
//        whenFindAllReturnedAnException();
//        andGetAllBookingsIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindAllReturnedValidBookings() {
//        Booking booking1 = new Booking(10L, 1L, 1L,
//                new Date(1000), new Date(50000), BookingStatus.OPEN.name());
//
//        Booking booking2 = new Booking(20L, 2L, 2L,
//                new Date(2000), new Date(60000), BookingStatus.FINISHED.name());
//
//        expectedResult = List.of(booking1, booking2);
//
//        when(bookingRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedNoBookings() {
//        expectedResult = Collections.emptyList();
//
//        when(bookingRepositoryMock.findAll()).thenReturn(expectedResult);
//    }
//
//    private void whenFindAllReturnedAnException() {
//        when(bookingRepositoryMock.findAll()).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andGetAllBookingsIsCalledInController() {
//        result = bookingController.getAllBookings();
//    }
//
//    private void andGetAllBookingsIsCalledWithNotFoundException() {
//        try {
//            result = bookingController.getAllBookings();
//        } catch (NotFoundException notFoundException) {
//            assertEquals("No bookings found.", notFoundException.getMessage());
//        }
//    }
//
//    private void andGetAllBookingsIsCalledWithException() {
//        try {
//            result = bookingController.getAllBookings();
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
