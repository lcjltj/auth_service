package spring.cloud.auth.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerUtil {

private String version;
	
    private String title;
    
	@Bean
	public Docket api_version_1() {
		version = "1.0";
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.groupName(version)
				.produces(getProduceContentTypes())
				.consumes(getConsumeContentTypes())
				.select()
				.apis(RequestHandlerSelectors.basePackage("spring.cloud.auth.cont"))
				.paths(PathSelectors.ant("/user/**"))
				.build()
				.securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo apiInfo() {
		title = "사용자 인증서비스";
		
		return new ApiInfoBuilder()
				.title(title)
				.description(new StringBuilder()
						.append("사용자 서비스 API\n\n")
						.append("1.로그인 통한  토큰 발급\n")
						.append("2.tokenType + accessKey 입력\n")
						.append("ex) Bearer eyJhbGciOiJIU..")
						.toString())
				.build();
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();

		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	private ApiKey apiKey() {
		return new ApiKey("AUTHORIZATION", "AUTHORIZATION","header");
	}
	
	private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**/auth/**"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("AUTHORIZATION", authorizationScopes));
    }
}