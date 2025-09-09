package com.gmail.bishoybasily.licensing.issuer.model.service;

import com.gmail.bishoybasily.licensing.issuer.config.concurrency.SchedulersProvider;
import com.gmail.bishoybasily.licensing.issuer.config.licensing.LicensingTemplate;
import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoCreateLicensePayload;
import com.gmail.bishoybasily.licensing.issuer.model.entity.EntityLicense;
import com.gmail.bishoybasily.licensing.issuer.model.repository.RepositoryLicenses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.function.Function.identity;
import static reactor.core.publisher.Mono.fromCallable;

@Service
@RequiredArgsConstructor
public class ServiceLicenses {

    private final LicensingTemplate licensingTemplate;
    private final RepositoryLicenses repositoryLicenses;
    private final SchedulersProvider schedulersProvider;

    public Flux<EntityLicense> list() {
        return fromCallable(repositoryLicenses::findAll)
                .flatMapIterable(identity())
                .subscribeOn(schedulersProvider.subscribeOn());
    }

    public Mono<EntityLicense> create(DtoCreateLicensePayload dto) {
        return fromCallable(() -> {

                    final var specs = new EntityLicense.Specs()
                            .setFingerprint(dto.getFingerprint())
                            .setCustomer(dto.getCustomer())
                            .setExpiry(dto.getExpiry())
                            .setQuota(
                                    new EntityLicense.Specs.Quota()
                                            .setCpu(dto.getCpuQuota())
                                            .setMemory(dto.getMemoryQuota())
                            );

                    final var specsSignature = licensingTemplate.generateEncodedSignature(specs);

                    final var entityLicense = new EntityLicense()
                            .setSpecs(specs)
                            .setSpecsSignature(specsSignature);

                    return repositoryLicenses.save(entityLicense);
                })
                .subscribeOn(schedulersProvider.subscribeOn());
    }

    public Mono<EntityLicense> delete(String id) {
        return fromCallable(() -> {
                    return repositoryLicenses.findById(id)
                            .map(entity -> {
                                repositoryLicenses.delete(entity);
                                return entity;
                            })
                            .orElseThrow();
                })
                .subscribeOn(schedulersProvider.subscribeOn());
    }


}
