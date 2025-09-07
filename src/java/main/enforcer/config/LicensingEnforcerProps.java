package com.gmail.bishoybasily.licensing.enforcer.config;

import com.gmail.bishoybasily.licensing.enforcer.license.License;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Data
@ConfigurationProperties(prefix = "licensing.enforcer")
public class LicensingEnforcerProps {

    private License license;
    private Resource publicKeyPath;

}
