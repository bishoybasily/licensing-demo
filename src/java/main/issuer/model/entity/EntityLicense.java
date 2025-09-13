package com.gmail.bishoybasily.licensing.issuer.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@Entity(name = "licenses")
public class EntityLicense {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "customer", column = @Column(name = "specs_customer")),
            @AttributeOverride(name = "expiry", column = @Column(name = "specs_expiry")),

            @AttributeOverride(name = "quota.cpu", column = @Column(name = "specs_quota_cpu")),
            @AttributeOverride(name = "quota.memory", column = @Column(name = "specs_quota_memory")),

    })
    private Specs specs;
    @Lob
    private String specsSignature;

    @Embeddable
    @Data
    @Accessors(chain = true)
    public static class Specs {

        private String fingerprint;
        private String customer;
        private OffsetDateTime expiry;
        @Embedded
        private Quota quota;

        @Embeddable
        @Data
        @Accessors(chain = true)
        public static class Quota {
            private int cpu;
            private int memory;
        }
    }

}
