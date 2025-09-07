package com.gmail.bishoybasily.licensing.enforcer.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@RequiredArgsConstructor
@EnableConfigurationProperties(LicensingEnforcerProps.class)
public class LicensingEnforcerAutoConfig {

    private final LicensingEnforcerProps props;

}
