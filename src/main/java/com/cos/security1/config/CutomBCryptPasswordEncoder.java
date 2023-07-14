package com.cos.security1.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CutomBCryptPasswordEncoder extends BCryptPasswordEncoder {
}
