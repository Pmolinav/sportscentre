package net.pmolinav.bookings;

import net.pmolinav.bookingslib.dto.*;
import net.pmolinav.bookingslib.mapper.ActivityMapper;
import net.pmolinav.bookingslib.mapper.BookingMapper;
import net.pmolinav.bookingslib.mapper.UserMapper;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.bookingslib.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappersTests {

    private final ActivityMapper activityMapper = ActivityMapper.INSTANCE;
    private final BookingMapper bookingMapper = BookingMapper.INSTANCE;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    void activityDTOToActivityEntityTest() {
        ActivityDTO activityDTO = new ActivityDTO(ActivityType.POOL, "Pool",
                "Pool activity", BigDecimal.valueOf(25), new Date(1L), new Date(3L));

        Activity expectedActivity = new Activity(null, ActivityType.POOL.name(), "Pool",
                "Pool activity", BigDecimal.valueOf(25), new Date(1L), new Date(3L));

        Activity activity = activityMapper.activityDTOToActivityEntity(activityDTO);

        assertEquals(expectedActivity, activity);
    }

    @Test
    void activityEntityTOActivityDTOTest() {
        Activity activity = new Activity(null, ActivityType.POOL.name(), "Pool",
                "Pool activity", BigDecimal.valueOf(25), new Date(1L), new Date(3L));

        ActivityDTO expectedActivityDTO = new ActivityDTO(ActivityType.POOL, "Pool",
                "Pool activity", BigDecimal.valueOf(25), new Date(1L), new Date(3L));

        ActivityDTO activityDTO = activityMapper.activityEntityTOActivityDTO(activity);

        assertEquals(expectedActivityDTO, activityDTO);
    }

    @Test
    void bookingDTOToBookingEntityTest() {
        BookingDTO bookingDTO = new BookingDTO(22L, 333L, new Date(1),
                new Date(1), BookingStatus.CANCELLED, new Date(1), null);

        Booking expectedBooking = new Booking(null, 22L, 333L,
                new Date(1), new Date(1), BookingStatus.CANCELLED.name(), new Date(1), null);


        Booking booking = bookingMapper.bookingDTOToBookingEntity(bookingDTO);

        assertEquals(expectedBooking, booking);
    }

    @Test
    void bookingEntityTOBookingDTOTest() {
        Booking booking = new Booking(1L, 22L, 333L,
                new Date(1), new Date(1), BookingStatus.CANCELLED.name(), new Date(1), null);

        BookingDTO expectedBookingDTO = new BookingDTO(22L, 333L, new Date(1),
                new Date(1), BookingStatus.CANCELLED, new Date(1), null);

        BookingDTO bookingDTO = bookingMapper.bookingEntityTOBookingDTO(booking);

        assertEquals(expectedBookingDTO, bookingDTO);
    }

    @Test
    void userDTOToUserEntityTest() {
        UserDTO userDTO = new UserDTO("someUser", "somePassword", "someName",
                "some@email.com", Role.USER, new Date(1L), null);

        User expectedUser = new User(null, "someUser", "somePassword", "someName",
                "some@email.com", Role.USER.name(), new Date(1L), null);

        User user = userMapper.userDTOToUserEntity(userDTO);

        assertEquals(expectedUser, user);
    }

    @Test
    void userEntityToUserDTOTest() {
        User user = new User(1L, "someUser", "somePassword", "someName",
                "some@email.com", Role.USER.name(), new Date(1L), null);

        UserDTO expectedUserDTO = new UserDTO("someUser", "somePassword", "someName",
                "some@email.com", Role.USER, new Date(1L), null);

        UserDTO userDTO = userMapper.userEntityToUserDTO(user);

        assertEquals(expectedUserDTO, userDTO);
    }

}
