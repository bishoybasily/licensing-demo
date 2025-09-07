package com.gmail.bishoybasily.licensing.issuer.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class DtoSpecs {

    private String token;
    private String customer;
    private OffsetDateTime expiry;
    private DtoQuota quota;

}
