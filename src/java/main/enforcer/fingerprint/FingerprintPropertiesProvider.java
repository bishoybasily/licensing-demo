package com.gmail.bishoybasily.licensing.enforcer.fingerprint;

import java.util.Map;

public interface FingerprintPropertiesProvider {

    Map<String, String> properties();

//    String serviceAccountIssuer();
//    String kubeSystemNamespaceId();
//    String installationNamespaceName();
//    String apiServerCertificateFingerprint();

}
