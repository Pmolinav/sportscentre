package net.pmolinav.springboot.security;

import net.pmolinav.bookings.model.User;
import net.pmolinav.springboot.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userClient
                .getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " does not exist"));

        return new UserDetailsImpl(user);

    }
}
