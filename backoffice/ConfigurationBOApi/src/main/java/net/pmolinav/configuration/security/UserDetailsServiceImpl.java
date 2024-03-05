package net.pmolinav.configuration.security;

import feign.FeignException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import net.pmolinav.configuration.service.ActivityBOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    UserClient userClient;

    @Cacheable
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
//            User user = userClient.findUserByUsername(username);
            User user = new User(1L,"Admin","$2a$10$BR4vfDp5RSnfqeSOR/w7ZekLd208dxsbHRANN.0eAClU0udvROyBa","ADMIN","ADMIN@Admin.com","ADMIN", new Date(), null);
            return new UserDetailsImpl(user);
        } catch (FeignException e) {
            if (e.status() == NOT_FOUND.value()) {
                logger.error("User with username " + username + " not found", e);
                throw new NotFoundException("User " + username + " not found");
            } else {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            }
        }

    }
}
