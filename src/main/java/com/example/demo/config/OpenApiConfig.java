package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Skill Gap Analyzer API")
                        .version("1.0")
                        .description("API for analyzing student skill gaps and generating recommendations"))
                .servers(List.of(
                        new Server().url("https://9005.408procr.amypo.ai/")
                ));
    }
}