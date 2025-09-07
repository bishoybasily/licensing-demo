package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FingerprintGenerator {

    private final FingerprintPropertiesProvider fingerprintPropertiesProvider;

    public Fingerprint generate() {
        return Fingerprint.builder()
                .properties(fingerprintPropertiesProvider.properties())
                .build();
    }

}
