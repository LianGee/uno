package com.bigdata.uno.api;

import com.bigdata.uno.service.ServiceContext;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ImportResource({ "classpath*:appcontext-database.xml"})
@Import(value = {ServiceContext.class})
@EnableSwagger2
public class UnoApi extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UnoApi.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}

