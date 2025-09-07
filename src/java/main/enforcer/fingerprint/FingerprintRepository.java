package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import java.util.Optional;

public interface FingerprintRepository {

    static FingerprintRepository inMemory() {
        return new FingerprintRepository() {

            private static String TOKEN;

            @Override
            public Optional<String> read() {
                return Optional.ofNullable(TOKEN);
            }

            @Override
            public void write(String token) {
                TOKEN = token;
            }
        };
    }

    Optional<String> read();

    void write(String token);

}
