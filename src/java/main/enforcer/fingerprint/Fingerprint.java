package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
public class Fingerprint {

    private List<Object> properties;

}
