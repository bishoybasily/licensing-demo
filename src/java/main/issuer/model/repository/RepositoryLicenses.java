package com.gmail.bishoybasily.licensing.issuer.model.repository;

import com.gmail.bishoybasily.licensing.issuer.model.entity.EntityLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryLicenses extends JpaRepository<EntityLicense, String> {
}
