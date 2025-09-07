package com.gmail.bishoybasily.licensing.issuer.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class DtoLicense {

    private String id;
    private DtoSpecs specs;
    private String specsSignature;

}
