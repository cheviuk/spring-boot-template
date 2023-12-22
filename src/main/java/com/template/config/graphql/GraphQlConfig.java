package com.template.config.graphql;

import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
@RequiredArgsConstructor
public class GraphQlConfig {
    private final Environment environment;
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType longFromGraphQLLongAliasedScalarType = ExtendedScalars
                .newAliasedScalar("Long")
                .aliasedScalar(ExtendedScalars.GraphQLLong)
                .build();
        return wiringBuilder -> wiringBuilder.scalar(longFromGraphQLLongAliasedScalarType);
    }

    @Bean
    public MaxQueryDepthInstrumentation maxQueryDepthInstrumentation() {
        String maxQueryDepth = environment.getProperty("graphql.maxQueryDepth", "10");
        return new MaxQueryDepthInstrumentation(Integer.parseInt(maxQueryDepth));
    }

    @Bean
    public MaxQueryComplexityInstrumentation maxQueryComplexityInstrumentation() {
        String maxQueryComplexity = environment.getProperty("graphql.maxQueryComplexity", "15");
        return new MaxQueryComplexityInstrumentation(Integer.parseInt(maxQueryComplexity));
    }
}
