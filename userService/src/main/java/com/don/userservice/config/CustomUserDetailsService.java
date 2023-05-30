package com.don.userservice.config;

import com.don.userservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return repository.findByUserName(username)
               .map(CustomUserDetails::new)
               .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }
}
