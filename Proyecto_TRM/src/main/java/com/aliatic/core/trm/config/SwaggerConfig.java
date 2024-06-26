package com.aliatic.core.trm.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import static com.aliatic.core.trm.config.Constants.*;

/**
 * Clase de configuración de Swagger
 *
 * @author Arenas Silva, Juan
 * @Empresa: Aliatic S.A.S.
 */
//@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Value("${trm.app.version}")
	private String version;
	
	@Value("${trm.app.nombre}")
	private String nombreApp;
	
	@Value("${trm.app.descripcion}")
	private String descripcionApp;
	
	@Value("${trm.app.pagina}")
	private String paginaWeb;

    @Bean
    Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(PATH_CONTROLLERS))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}
	
	private ApiInfo getApiInfo() {
				
		return new ApiInfo(
				nombreApp,
				descripcionApp,
				version,
				paginaWeb,
				new Contact("Arquitectura", paginaWeb, "juan.arenas@aliatic.com.co"),
				"LICENSE",
				paginaWeb,
				Collections.emptyList()
				);
	}
}