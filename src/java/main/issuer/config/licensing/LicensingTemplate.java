package com.gmail.bishoybasily.licensing.issuer.config.licensing;

import lombok.RequiredArgsConstructor;

import java.util.Base64;

import static com.gmail.bishoybasily.licensing.commons.utils.RsaUtils.generateRsaSignature;
import static com.gmail.bishoybasily.licensing.commons.utils.RsaUtils.loadRsaPrivateKey;
import static com.gmail.bishoybasily.licensing.commons.utils.SerializationUtils.generateCanonicalString;

@RequiredArgsConstructor
public class LicensingTemplate {

    private final LicensingProps props;

    public String generateEncodedSignature(Object object) throws Exception {
        final var privateKey = loadRsaPrivateKey(props.getPrivateKeyPath().getInputStream());
        final var objectCanonicalString = generateCanonicalString(object);
        final var signature = generateRsaSignature(privateKey, objectCanonicalString.getBytes());
        return Base64.getEncoder().encodeToString(signature);
    }

}
