package com.ganzithon.Hexfarming.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component // 스프링 Bean에서 관리
public class PasswordEncoderManager {
    private BCryptPasswordEncoder passwordEncoder;

    public PasswordEncoderManager() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }
}
