package com.gmail.bishoybasily.licensing.application.config.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreApi;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class KubernetesConfig {

    @Bean
    ApiClient apiClient() throws IOException {
        return ClientBuilder.standard().build();
    }

    @Bean
    CoreApi coreApi(ApiClient apiClient) {
        return new CoreApi(apiClient);
    }

}
