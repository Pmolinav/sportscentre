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
//class DeleteBookingUnitTest extends BaseUnitTest {
//
//    long bookingId = 1L;
//    Booking expectedBooking;
//    ResponseEntity<HttpStatus> result;
//
//    @Test
//    void deleteBookingHappyPath() {
//        whenFindBookingByIdReturnedAValidBooking();
//        andDeleteBookingIsCalledInController();
//        thenReceivedStatusIsOK();
//    }
//
//    @Test
//    void deleteBookingNotFound() {
//        whenFindBookingByIdReturnedNoBooking();
//        andDeleteBookingIsCalledWithNotFoundException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void deleteBookingServerError() {
//        whenFindBookingByIdReturnedAnException();
//        andDeleteBookingIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    private void whenFindBookingByIdReturnedAValidBooking() {
//        expectedBooking = new Booking(bookingId, 1L, 2L, new Date(),
//                new Date(System.currentTimeMillis() + 500000), BookingStatus.OPEN.name());
//
//        when(bookingRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedBooking));
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
//    private void andDeleteBookingIsCalledInController() {
//        result = bookingController.deleteBooking(bookingId);
//    }
//
//    private void andDeleteBookingIsCalledWithNotFoundException() {
//        try {
//            result = bookingController.deleteBooking(bookingId);
//        } catch (NotFoundException notFoundException) {
//            assertEquals(String.format("Booking with id %s does not exist.", bookingId), notFoundException.getMessage());
//        }
//    }
//
//    private void andDeleteBookingIsCalledWithException() {
//        try {
//            result = bookingController.deleteBooking(bookingId);
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
