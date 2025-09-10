package com.gmail.bishoybasily.licensing.application.config.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreApi;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class KubernetesConfig {

    @Bean
    ApiClient apiClient() throws IOException {
        final var kubeConfigPath = "%s/.kube/config".formatted(System.getProperty("user.home"));
        final var kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(new FileInputStream(kubeConfigPath)));
        return ClientBuilder.kubeconfig(kubeConfig).build();
    }

    @Bean
    CoreApi coreApi(ApiClient apiClient) {
        return new CoreApi(apiClient);
    }

}
