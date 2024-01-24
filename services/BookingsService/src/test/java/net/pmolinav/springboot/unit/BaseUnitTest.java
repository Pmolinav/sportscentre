//package net.pmolinav.springboot.unit;
//TODO: Arreglar tests
//import net.pmolinav.springboot.controller.ActivityController;
//import net.pmolinav.springboot.controller.BookingController;
//import net.pmolinav.springboot.controller.UserController;
//import net.pmolinav.springboot.mapper.ActivityMapper;
//import net.pmolinav.springboot.mapper.BookingMapper;
//import net.pmolinav.springboot.mapper.UserMapper;
//import net.pmolinav.springboot.repository.ActivityRepository;
//import net.pmolinav.springboot.repository.BookingRepository;
//import net.pmolinav.springboot.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@RunWith(MockitoJUnitRunner.class)
//class BaseUnitTest {
//    @Mock
//    UserRepository userRepositoryMock;
//    @InjectMocks
//    UserController userController;
//    @Mock
//    UserMapper userMapperMock;
//    @Mock
//    ActivityRepository activityRepositoryMock;
//    @InjectMocks
//    ActivityController activityController;
//    @Mock
//    ActivityMapper activityMapperMock;
//    @Mock
//    BookingRepository bookingRepositoryMock;
//    @InjectMocks
//    BookingController bookingController;
//    @Mock
//    BookingMapper bookingMapperMock;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//}
