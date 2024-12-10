package net.pmolinav.configuration.security;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    UserClient userClient;

    public UserDetailsServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Cacheable
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userClient.findUserByUsername(username);
            return new UserDetailsImpl(user);
        } catch (FeignException e) {
            if (e.status() == NOT_FOUND.value()) {
                log.error("User with username {} not found.", username, e);
                throw new NotFoundException("User " + username + " not found");
            } else {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            }
        }

    }
}
