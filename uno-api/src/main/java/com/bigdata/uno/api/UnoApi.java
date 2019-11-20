package com.bigdata.uno.api;

import com.bigdata.uno.service.ServiceContext;
import lombok.extern.slf4j.Slf4j;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
@ImportResource({ "classpath*:appcontext-database.xml"})
@Import(value = {ServiceContext.class})
@EnableSwagger2
@EnableCasClient
public class UnoApi implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    public static void main(String[] args) {
        SpringApplication.run(UnoApi.class, args);
    }
}

