package net.pmolinav.configuration.functionals;


import net.pmolinav.configuration.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class AbstractBaseTest {
    protected static String username = "someUser";
    protected static final String password = "$2a$10$pn85ACcwW6v74Kkt3pnPau7A4lv8N2d.fvwXuLsYanv07PzlXTu9S";
    protected static final String requestUid = "someRequestUid";
    protected static final String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTcxNTcxNDU1NiwiZXhwIjoxNzE1NzI2ODkzLCJuYW1lIjoiQWRtaW4ifQ.pE5_PhbF2Lm1vy3yj_nf26gOW9Na8_hXTBqTc3zF6aY";
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void mockLoginSuccessfully() {


//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        Assertions.assertEquals(username, userDetails.getUsername());
//        Assertions.assertEquals(password, userDetails.getPassword());
    }

}

