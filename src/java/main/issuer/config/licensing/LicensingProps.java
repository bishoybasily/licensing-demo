package com.gmail.bishoybasily.licensing.issuer.config.licensing;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Data
@ConfigurationProperties(prefix = "licensing")
public class LicensingProps {

    private Resource privateKeyPath;

}
