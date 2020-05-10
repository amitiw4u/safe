package com.sf.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket documentation() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("sfs").apiInfo(metadata()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sf")).paths(PathSelectors.any()).build()
				.globalOperationParameters(getGlobalHeaders());
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("Safe Optimization service Documentation")
				.description("Safes Optimization service Documentation with Swagger")
				.version("1.0").build();
	}

	private List<Parameter> getGlobalHeaders() {
		List<Parameter> parametersList = new LinkedList<>();
		parametersList
				.add(getParameter(Constants.AUTH_TOKEN_HEADER_PARAM, "Valid Session Token", "string", "header", false));
		return parametersList;
	}

	private Parameter getParameter(String paramName, String paramDescription, String paramDataType, String paramType,
			boolean isRequired) {
		return new ParameterBuilder().name(paramName).description(paramDescription)
				.modelRef(new ModelRef(paramDataType)).parameterType(paramType).required(isRequired).build();
	}
}
