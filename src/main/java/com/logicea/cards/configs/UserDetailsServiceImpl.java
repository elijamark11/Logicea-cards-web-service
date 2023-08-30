package com.logicea.cards.configs;

import com.logicea.cards.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user present with username: " + email));

        GrantedAuthority roleUserGroup = new SimpleGrantedAuthority("ROLE_" + user.getUserType().getName().replace(" ", "_"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(roleUserGroup);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }
}