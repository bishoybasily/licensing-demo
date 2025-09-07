package com.gmail.bishoybasily.licensing.issuer.config.licensing;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({LicensingProps.class})
public class LicensingConfig {

    @Bean
    LicensingTemplate licensingTemplate(LicensingProps props) {
        return new LicensingTemplate(props);
    }

}
