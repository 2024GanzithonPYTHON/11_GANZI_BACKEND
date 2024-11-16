package com.ganzithon.Hexfarming.domain.user.util;

import com.ganzithon.Hexfarming.domain.user.User;
import com.ganzithon.Hexfarming.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "존재하지 않는 아이디입니다.");
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUserId(int userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "존재하지 않는 아이디입니다.");
        }
        return new CustomUserDetails(user);
    }
}