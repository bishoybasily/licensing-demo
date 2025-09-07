package com.gmail.bishoybasily.licensing.issuer.model.service;

import com.gmail.bishoybasily.licensing.issuer.config.licensing.LicensingTemplate;
import com.gmail.bishoybasily.licensing.issuer.model.dto.DtoCreateLicensePayload;
import com.gmail.bishoybasily.licensing.issuer.model.entity.EntityLicense;
import com.gmail.bishoybasily.licensing.issuer.model.repository.RepositoryLicenses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ServiceLicenses {

    private static final Scheduler SCHEDULER = Schedulers.fromExecutor(Executors.newVirtualThreadPerTaskExecutor());

    private final LicensingTemplate licensingTemplate;
    private final RepositoryLicenses repositoryLicenses;

    public Flux<EntityLicense> list() {
        return Mono.fromCallable(repositoryLicenses::findAll)
                .flatMapIterable(Function.identity())
                .subscribeOn(SCHEDULER);
    }

    public Mono<EntityLicense> create(DtoCreateLicensePayload dto) {
        return Mono.fromCallable(() -> {

                    final var specs = new EntityLicense.Specs()
                            .setToken(dto.getToken())
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
                .subscribeOn(SCHEDULER);
    }

    public Mono<EntityLicense> delete(String id) {
        return Mono.fromCallable(() -> {
                    return repositoryLicenses.findById(id).map(entity -> {
                        repositoryLicenses.delete(entity);
                        return entity;
                    }).orElseThrow();
                })
                .subscribeOn(SCHEDULER);
    }


}
