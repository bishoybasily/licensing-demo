package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class Fingerprint {

    private List<Object> properties;

}
