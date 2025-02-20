package com.kim.voiturespring.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter

public class VoitureConfig {
    @Value("${example.config.value}")
    private String value;
}
