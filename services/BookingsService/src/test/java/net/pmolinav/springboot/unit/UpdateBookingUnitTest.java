//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.BookingStatus;
//import net.pmolinav.springboot.dto.BookingUpdateDTO;
//import net.pmolinav.springboot.exception.BadRequestException;
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
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class UpdateBookingUnitTest extends BaseUnitTest {
//
//    long bookingId = 1L;
//    BookingUpdateDTO bookingUpdateDTO;
//    Booking expectedResult;
//    ResponseEntity<Booking> result;
//
//    @Test
//    void updateBookingHappyPath() {
//        givenValidBookingDTOForRequest(new Date(), new Date(System.currentTimeMillis() + 50000000), BookingStatus.OPEN);
//        whenFindBookingByIdReturnedAValidBooking();
//        whenSaveBookingReturnedAValidBooking();
//        andUpdateBookingIsCalledInController();
//        thenReceivedStatusIsOK();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void updateBookingServerError() {
//        givenValidBookingDTOForRequest(new Date(), new Date(System.currentTimeMillis() + 50000000), BookingStatus.FINISHED);
//        whenFindBookingByIdReturnedAValidBooking();
//        whenSaveBookingReturnedAnException();
//        andUpdateBookingIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateBookingNoBodyBadRequest() {
//        andUpdateBookingIsCalledWithBadRequestException("Body is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateBookingNoStartTimeBadRequest() {
//        givenValidBookingDTOForRequest(null, new Date(System.currentTimeMillis() + 50000000), BookingStatus.OPEN);
//        andUpdateBookingIsCalledWithBadRequestException("Start time and end time are mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateBookingNoEndTimeBadRequest() {
//        givenValidBookingDTOForRequest(new Date(), null, BookingStatus.CANCELLED);
//        andUpdateBookingIsCalledWithBadRequestException("Start time and end time are mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void updateBookingNoValidPeriodBadRequest() {
//        givenValidBookingDTOForRequest(new Date(), new Date(System.currentTimeMillis() - 50000000), BookingStatus.OPEN);
//        andUpdateBookingIsCalledWithBadRequestException("End time must be greater than start time.");
//        thenResultIsNull();
//    }
//
//    private void givenValidBookingDTOForRequest(Date startTime, Date endTime, BookingStatus status) {
//        bookingUpdateDTO = new BookingUpdateDTO(startTime, endTime, status);
//    }
//
//    private void whenFindBookingByIdReturnedAValidBooking() {
//        expectedResult = new Booking(1L, 2L, 3L, bookingUpdateDTO.getStartTime(),
//                bookingUpdateDTO.getEndTime(), bookingUpdateDTO.getStatus().name());
//
//        when(bookingRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(expectedResult));
//    }
//
//    private void whenSaveBookingReturnedAValidBooking() {
//        when(bookingRepositoryMock.save(any())).thenReturn(expectedResult);
//    }
//
//    private void whenSaveBookingReturnedAnException() {
//        when(bookingRepositoryMock.save(any())).thenThrow(new IllegalArgumentException());
//    }
//
//    private void andUpdateBookingIsCalledInController() {
//        result = bookingController.updateBooking(bookingId, bookingUpdateDTO);
//    }
//
//    private void andUpdateBookingIsCalledWithBadRequestException(String expectedMessage) {
//        try {
//            result = bookingController.updateBooking(bookingId, bookingUpdateDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(expectedMessage, badRequestException.getMessage());
//        }
//    }
//
//    private void andUpdateBookingIsCalledWithException() {
//        try {
//            result = bookingController.updateBooking(bookingId, bookingUpdateDTO);
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
