package net.pmolinav.configuration.unit;

import net.pmolinav.bookings.controller.ActivityController;
import net.pmolinav.bookings.controller.BookingController;
import net.pmolinav.bookings.controller.UserController;
import net.pmolinav.bookings.service.ActivityService;
import net.pmolinav.bookings.service.BookingService;
import net.pmolinav.bookings.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class BaseUnitTest {
    @Mock
    UserService userServiceMock;
    @InjectMocks
    UserController userController;
    @Mock
    ActivityService activityServiceMock;
    @InjectMocks
    ActivityController activityController;
    @Mock
    BookingService bookingServiceMock;
    @InjectMocks
    BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
