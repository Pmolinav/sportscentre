//package net.pmolinav.springboot.unit;
//
//import net.pmolinav.springboot.dto.BookingDTO;
//import net.pmolinav.springboot.dto.BookingStatus;
//import net.pmolinav.springboot.exception.BadRequestException;
//import net.pmolinav.springboot.model.Booking;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class CreateBookingUnitTest extends BaseUnitTest {
//
//    BookingDTO bookingDTO;
//    Booking expectedResult;
//    ResponseEntity<Booking> result;
//
//    @Test
//    void createBookingHappyPath() {
//        givenValidBookingDTOForRequest(1L, 2L, new Date(),
//                new Date(System.currentTimeMillis() + 50000000), BookingStatus.OPEN);
//        whenMapperWorkedAsExpected();
//        whenSaveBookingReturnedAValidBooking();
//        andCreateBookingIsCalledInController();
//        thenReceivedStatusIsCreated();
//        thenReceivedResponseAsExpected();
//    }
//
//    @Test
//    void createBookingServerError() {
//        givenValidBookingDTOForRequest(1L, 2L, new Date(),
//                new Date(System.currentTimeMillis() + 50000000), BookingStatus.FINISHED);
//        whenSaveBookingReturnedAnException();
//        andCreateBookingIsCalledWithException();
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoBodyBadRequest() {
//        andCreateBookingIsCalledWithBadRequestException("Body is mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoValidUserIdBadRequest() {
//        givenValidBookingDTOForRequest(0L, 1L, new Date(),
//                new Date(System.currentTimeMillis() + 50000000), BookingStatus.ACCEPTED);
//        andCreateBookingIsCalledWithBadRequestException("User and activity ids are mandatory to associate the booking correctly.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoValidActivityIdBadRequest() {
//        givenValidBookingDTOForRequest(2L, 0L, new Date(),
//                new Date(System.currentTimeMillis() + 50000000), BookingStatus.CANCELLED);
//        andCreateBookingIsCalledWithBadRequestException("User and activity ids are mandatory to associate the booking correctly.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoStartTimeBadRequest() {
//        givenValidBookingDTOForRequest(1L, 2L, null,
//                new Date(System.currentTimeMillis() + 50000000), BookingStatus.CANCELLED);
//        andCreateBookingIsCalledWithBadRequestException("Start time and end time are mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoEndTimeBadRequest() {
//        givenValidBookingDTOForRequest(1L, 2L, new Date(), null, BookingStatus.CANCELLED);
//        andCreateBookingIsCalledWithBadRequestException("Start time and end time are mandatory.");
//        thenResultIsNull();
//    }
//
//    @Test
//    void createBookingNoValidPeriodBadRequest() {
//        givenValidBookingDTOForRequest(1L, 2L, new Date(),
//                new Date(System.currentTimeMillis() - 50000000), BookingStatus.CANCELLED);
//        andCreateBookingIsCalledWithBadRequestException("End time must be greater than start time.");
//        thenResultIsNull();
//    }
//
//    private void givenValidBookingDTOForRequest(long userId, long activityId, Date startTime, Date endTime, BookingStatus status) {
//        bookingDTO = new BookingDTO(userId, activityId, startTime, endTime, status);
//    }
//
//    private void whenMapperWorkedAsExpected() {
//        expectedResult = new Booking(1L, bookingDTO.getUserId(), bookingDTO.getActivityId(),
//                bookingDTO.getStartTime(), bookingDTO.getEndTime(), bookingDTO.getStatus().name());
//
//        when(bookingMapperMock.bookingDTOToBookingEntity(any())).thenReturn(expectedResult);
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
//    private void andCreateBookingIsCalledInController() {
//        result = bookingController.createBooking(bookingDTO);
//    }
//
//    private void andCreateBookingIsCalledWithBadRequestException(String expectedMessage) {
//        try {
//            result = bookingController.createBooking(bookingDTO);
//        } catch (BadRequestException badRequestException) {
//            assertEquals(expectedMessage, badRequestException.getMessage());
//        }
//    }
//
//    private void andCreateBookingIsCalledWithException() {
//        try {
//            result = bookingController.createBooking(bookingDTO);
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
