package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.RequiredArgsConstructor;

import static com.gmail.bishoybasily.licensing.commons.utils.HashingUtils.SHA1;
import static com.gmail.bishoybasily.licensing.commons.utils.HashingUtils.generateHash;
import static com.gmail.bishoybasily.licensing.commons.utils.SerializationUtils.generateCanonicalString;

@RequiredArgsConstructor
public class FingerprintHasher {

    private final FingerprintGenerator fingerprintGenerator;

    public String hash() {
        final var fingerprint = fingerprintGenerator.generate();
        final var fingerPrintCanonicalString = generateCanonicalString(fingerprint);
        return generateHash(fingerPrintCanonicalString, SHA1);
    }

}
