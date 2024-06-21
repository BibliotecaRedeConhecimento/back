package com.t2m.library.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${prop.swagger.dev-url}")
	private String devUrl;

	@Bean
	OpenAPI myOpenAPI() {
		Server server = new Server();
		
		server.setUrl(devUrl);
		server.setDescription("Server URL - ambiente de desenvolvimento");


		Info info = new Info().title("Documentação API").version("1.0.0")
				.description("API com endpoints da Biblioteca");

		return new OpenAPI().info(info).servers(List.of(server));
	}
}
