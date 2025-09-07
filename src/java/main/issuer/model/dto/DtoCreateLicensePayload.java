package com.gmail.bishoybasily.licensing.issuer.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class DtoCreateLicensePayload {

    private String token;
    private String customer;
    private Integer cpuQuota;
    private Integer memoryQuota;
    private OffsetDateTime expiry;

}
