package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.RequiredArgsConstructor;

import static com.gmail.bishoybasily.licensing.commons.utils.HashingUtils.SHA1;
import static com.gmail.bishoybasily.licensing.commons.utils.HashingUtils.generateHash;
import static com.gmail.bishoybasily.licensing.commons.utils.SerializationUtils.generateCanonicalString;

@RequiredArgsConstructor
public class FingerprintResolver {

    private final FingerprintGenerator fingerprintGenerator;
    private final FingerprintRepository fingerprintRepository;

    public String resolve() {

        return fingerprintRepository.read()
                .orElseGet(() -> {

                    final var token = fingerprintGenerator.generate();
                    final var tokenCanonicalString = generateCanonicalString(token);
                    final var tokenHash = generateHash(tokenCanonicalString, SHA1);
                    fingerprintRepository.write(tokenHash);
                    return tokenHash;

                });
    }

}
