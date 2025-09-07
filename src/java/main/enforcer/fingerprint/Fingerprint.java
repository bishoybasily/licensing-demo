package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(toBuilder = true)
public class Fingerprint {

    private Map<String, String> properties;

}
