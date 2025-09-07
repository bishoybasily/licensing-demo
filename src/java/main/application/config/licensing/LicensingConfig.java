package com.gmail.bishoybasily.licensing.application.config.licensing;

import com.gmail.bishoybasily.licensing.enforcer.fingerprint.FingerprintGenerator;
import com.gmail.bishoybasily.licensing.enforcer.fingerprint.FingerprintHasher;
import com.gmail.bishoybasily.licensing.enforcer.fingerprint.FingerprintPropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LicensingConfig {

    @Bean
    FingerprintPropertiesProvider fingerprintPropertiesProvider() {
        return () -> {
            // list of unique and stable values, suggestions:
            //    String serviceAccountIssuer();
            //    String kubeSystemNamespaceId();
            //    String installationNamespaceName();
            //    String apiServerCertificateFingerprint();
            return List.of("val1", "val2");
        };
    }

    @Bean
    FingerprintGenerator fingerprintGenerator(FingerprintPropertiesProvider propertiesProvider) {
        return new FingerprintGenerator(propertiesProvider);
    }

    @Bean
    FingerprintHasher fingerprintResolver(FingerprintGenerator generator) {
        return new FingerprintHasher(generator);
    }
}
