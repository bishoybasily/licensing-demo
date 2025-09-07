package com.gmail.bishoybasily.licensing.issuer.model.dto;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class DtoCreateLicensePayload {

    @Nonnull
    private String fingerprint;
    @Nonnull
    private String customer;
    @Nonnull
    private Integer cpuQuota;
    @Nonnull
    private Integer memoryQuota;
    @Nonnull
    private OffsetDateTime expiry;

}
