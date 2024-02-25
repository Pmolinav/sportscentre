package net.pmolinav.configuration.functional;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.pmolinav.bookingslib.dto.Role;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import net.pmolinav.configuration.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public abstract class AbstractContainerBaseTest {
    protected static final String token = "someToken";
    protected static String username = "someUser";
    protected static final String password = "$2a$10$pn85ACcwW6v74Kkt3pnPau7A4lv8N2d.fvwXuLsYanv07PzlXTu9S";

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

