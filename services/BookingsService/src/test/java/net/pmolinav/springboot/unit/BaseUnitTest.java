package net.pmolinav.springboot.unit;

import net.pmolinav.bookings.mapper.ActivityMapper;
import net.pmolinav.bookings.mapper.BookingMapper;
import net.pmolinav.springboot.controller.ActivityController;
import net.pmolinav.springboot.controller.BookingController;
import net.pmolinav.springboot.controller.UserController;
import net.pmolinav.springboot.repository.BookingRepository;
import net.pmolinav.springboot.repository.UserRepository;
import net.pmolinav.springboot.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class BaseUnitTest {
    @Mock
    UserRepository userRepositoryMock;
    @InjectMocks
    UserController userController;
    @Mock
    ActivityService activityServiceMock;
    @InjectMocks
    ActivityController activityController;
    @Mock
    public ActivityMapper activityMapperMock;
    @Mock
    BookingRepository bookingRepositoryMock;
    @InjectMocks
    BookingController bookingController;
    @Mock
    BookingMapper bookingMapperMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
