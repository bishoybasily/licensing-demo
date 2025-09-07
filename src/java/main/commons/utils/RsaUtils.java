package com.gmail.bishoybasily.licensing.commons.utils;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption;

public class RsaUtils {

    public final static String RSA_ALGORITHM = "SHA256withRSA";

    public static byte[] generateRsaSignature(PrivateKey privateKey, byte[] data) throws Exception {
        final var sig = Signature.getInstance(RSA_ALGORITHM);
        sig.initSign(privateKey);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verifyRsaSignature(PublicKey publicKey, byte[] data, byte[] signature) throws Exception {
        final var sig = Signature.getInstance(RSA_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }

    public static PublicKey loadRsaPublicKey(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        final var kf = KeyFactory.getInstance("RSA");
        try (final var pemParser = new PEMParser(new InputStreamReader(inputStream))) {
            SubjectPublicKeyInfo pubInfo = (SubjectPublicKeyInfo) pemParser.readObject();
            final var encoded = pubInfo.getEncoded();
            return kf.generatePublic(new X509EncodedKeySpec(encoded));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey loadRsaPrivateKey(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        final var kf = KeyFactory.getInstance("RSA");
        try (final var pemParser = new PEMParser(new InputStreamReader(inputStream))) {
            final var obj = pemParser.readObject();
            PrivateKeyInfo pkInfo;
            if (obj instanceof RSAPrivateKey rsa) {
                // Convert PKCS#1 -> PKCS#8
                pkInfo = new PrivateKeyInfo(new AlgorithmIdentifier(rsaEncryption), rsa);
            } else {
                pkInfo = (PrivateKeyInfo) obj;
            }
            final var encoded = pkInfo.getEncoded();
            return kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
