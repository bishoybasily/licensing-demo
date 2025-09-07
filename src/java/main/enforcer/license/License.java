package com.gmail.bishoybasily.licensing.enforcer.license;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
public class License {

    private String id;
    private Specs specs;
    private String specsSignature;

    @Data
    @Builder(toBuilder = true)
    public static class Specs {

        private String token;
        private String customer;
        private OffsetDateTime expiry;
        private Quota quota;

        @Data
        @Builder(toBuilder = true)
        public static class Quota {

            private int cpu;
            private int memory;

        }
    }

}
