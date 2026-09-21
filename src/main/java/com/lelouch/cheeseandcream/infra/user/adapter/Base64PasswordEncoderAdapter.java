package com.lelouch.cheeseandcream.infra.user.adapter;

import com.lelouch.cheeseandcream.application.user.command.PasswordEncoderPort;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class Base64PasswordEncoderAdapter implements PasswordEncoderPort {

    @Override
    public String encode(String rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
