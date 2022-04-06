package com.ufcg.psoft.tccmatch.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDocumentation() {
    return new Docket(DocumentationType.SWAGGER_2)
      .securityContexts(getSecurityContexts())
      .securitySchemes(getSecuritySchemes())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.ufcg.psoft.tccmatch"))
      .build();
  }

  private List<ApiKey> getSecuritySchemes() {
    return List.of(new ApiKey("JWT", "Authorization", "header"));
  }

  private List<SecurityContext> getSecurityContexts() {
    return List.of(SecurityContext.builder().securityReferences(getSecurityReferences()).build());
  }

  private List<SecurityReference> getSecurityReferences() {
    AuthorizationScope[] scopes = { new AuthorizationScope("global", "accessEverything") };
    SecurityReference securityReference = new SecurityReference("JWT", scopes);
    return List.of(securityReference);
  }
}
