package com.gmail.bishoybasily.licensing.issuer.controller;


import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoCreateLicensePayload;
import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoLicense;
import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoQuota;
import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoSpecs;
import com.gmail.bishoybasily.licensing.issuer.model.entity.EntityLicense;
import com.gmail.bishoybasily.licensing.issuer.model.service.ServiceLicenses;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Controller
@RequiredArgsConstructor
public class ControllerLicences {

    private final Function<EntityLicense, DtoLicense> entityToDto = entity -> {

        final var specs = entity.getSpecs();
        final var quota = specs.getQuota();

        return DtoLicense.builder()
                .withId(entity.getId())
                .withSpecs(
                        DtoSpecs.builder()
                                .withFingerprint(specs.getFingerprint())
                                .withExpiry(specs.getExpiry())
                                .withCustomer(specs.getCustomer())
                                .withQuota(
                                        DtoQuota.builder()
                                                .withCpu(quota.getCpu())
                                                .withMemory(quota.getMemory())
                                                .build()
                                )
                                .build()
                )
                .withSpecsSignature(entity.getSpecsSignature())
                .build();
    };

    private final ServiceLicenses serviceLicenses;

    @SchemaMapping(typeName = "Query", field = "licenses")
    public Flux<DtoLicense> licences() {
        return serviceLicenses.list().map(entityToDto);
    }

    @SchemaMapping(typeName = "Mutation", field = "createLicense")
    public Mono<DtoLicense> createLicence(@Argument @Nonnull DtoCreateLicensePayload payload) {
        return serviceLicenses.create(payload).map(entityToDto);
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteLicense")
    public Mono<DtoLicense> deleteLicence(@Argument @Nonnull String id) {
        return serviceLicenses.delete(id).map(entityToDto);
    }

}
