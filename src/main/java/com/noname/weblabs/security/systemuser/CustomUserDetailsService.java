package com.noname.weblabs.security.systemuser;

import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noname.weblabs.user.UserRepository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LogManager.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @NonNull
    @Override
    public CustomUserDetails loadUserByUsername(@NonNull final String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        var user = userRepository.findByUsername(username)
                                 .orElseThrow(() -> new UsernameNotFoundException("Invalid auth subject: " + username));

        var roles = user.getRoles().stream().map(SimpleGrantedAuthority::new).toList();

        LOG.debug("Loaded user " + user.getId() + " with roles: " + roles);
        
        return new CustomUserDetails(
                user.getId(), username, user.getPassword(), roles, user.isBanned()
        );
    }
}