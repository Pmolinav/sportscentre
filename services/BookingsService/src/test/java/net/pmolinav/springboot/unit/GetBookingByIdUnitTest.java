//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.BookingStatus;
//import net.pmolinav.springboot.exception.NotFoundException;
//import net.pmolinav.springboot.model.Booking;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class GetBookingByIdUnitTest extends BaseUnitTest {
//
//    long bookingId = 1L;
//    Booking expectedResult;
//    ResponseEntity<Booking> result;
//
//    @Test
//    void getBookingByIdHappyPath() {
//        whenFindBookingByIdReturnedAValidBooking();
//        andGetAllActivitiesIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void getBookingByIdNotFound() {
//        whenFindBookingByIdReturnedNoBooking();
//        andGetBookingByIdIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void getBookingByIdServerError() {
//        whenFindBookingByIdReturnedAnException();
//        andGetBookingByIdIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindBookingByIdReturnedAValidBooking() {
//        expectedResult = new Booking(bookingId, 1L, 2L, new Date(),
//                new Date(System.currentTimeMillis() + 500000), BookingStatus.OPEN.name());
//
//        when(bookingRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedResult));
//    }
//
//    private void whenFindBookingByIdReturnedNoBooking() {
//        when(bookingRepositoryMock.findById(bookingId)).thenReturn(Optional.empty());
//    }
//
//    private void whenFindBookingByIdReturnedAnException() {
//        when(bookingRepositoryMock.findById(bookingId)).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andGetAllActivitiesIsCalledInController() {
//        result = bookingController.getBookingById(bookingId);
//    }
//
//    private void andGetBookingByIdIsCalledWithNotFoundException() {
//        try {
//            result = bookingController.getBookingById(bookingId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("Booking with id %s does not exist.", bookingId), notFoundException.getMessage());
//        }
//    }
//
//    private void andGetBookingByIdIsCalledWithException() {
//        try {
//            result = bookingController.getBookingById(bookingId);
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
