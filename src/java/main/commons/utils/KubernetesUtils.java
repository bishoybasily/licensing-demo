package com.gmail.bishoybasily.licensing.commons.utils;

import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

@Slf4j
public class KubernetesUtils {

    public static String getNamespace() throws IOException {

        final var namespaceFile = new File("/var/run/secrets/kubernetes.io/serviceaccount/namespace");
        if (namespaceFile.exists()) {
            // we're inside a pod
            return new String(Files.readAllBytes(namespaceFile.toPath()));
        }

        final var kubeConfigFile = new File("%s/.kube/config".formatted(System.getProperty("user.home")));
        if (kubeConfigFile.exists()) {
            // we're running locally
            final var kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(new FileInputStream(kubeConfigFile)));
            final var currentContext = kubeConfig.getCurrentContext();
            if (currentContext != null) {
//                kubeConfig.getContexts().stream().filter(it-> it.)
            } else {
                log.info(".kube/config file found without an active current context with an explicit namespace set, using default");
                return "default";
            }
        }

        throw new IllegalStateException("No namespace file found nor .kube/config ... Where are we?");
    }

}
