package com.gmail.bishoybasily.licensing.issuer.config.graphql;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import static graphql.scalars.ExtendedScalars.*;


@Configuration
public class GraphqlConfig {

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(Json).scalar(DateTime).scalar(Date).scalar(Url);
    }

}
