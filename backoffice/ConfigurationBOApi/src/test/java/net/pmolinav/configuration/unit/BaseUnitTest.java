package net.pmolinav.configuration.unit;

import net.pmolinav.configuration.controller.ActivityBOController;
import net.pmolinav.configuration.controller.BookingBOController;
import net.pmolinav.configuration.controller.UserBOController;
import net.pmolinav.configuration.service.ActivityBOService;
import net.pmolinav.configuration.service.BookingBOService;
import net.pmolinav.configuration.service.UserBOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class BaseUnitTest {
    @Mock
    UserBOService userBOServiceMock;
    @InjectMocks
    UserBOController userController;
    @Mock
    ActivityBOService activityBOServiceMock;
    @InjectMocks
    ActivityBOController activityController;
    @Mock
    BookingBOService bookingBOServiceMock;
    @InjectMocks
    BookingBOController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
