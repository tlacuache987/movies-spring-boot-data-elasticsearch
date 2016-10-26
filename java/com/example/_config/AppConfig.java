package com.example._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class AppConfig {

	@Configuration
	@EnableSwagger2
	class SwaggerConfig {
		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select()
					.paths(PathSelectors.regex("/api/.*")).build();
		}

		private ApiInfo apiInfo() {
			@SuppressWarnings("deprecation")
			ApiInfo apiInfo = new ApiInfoBuilder().title("API definition").description("API REST")
					.contact("Dinsey Theatrical").build();
			return apiInfo;
		}
	}

}
