package com.bigdata.uno.api;

import com.bigdata.uno.service.ServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
@ImportResource({ "classpath*:appcontext-database.xml"})
@Import(value = {ServiceContext.class})
@EnableSwagger2
public class UnoApi {ll

    public static void main(String[] args) {
        SpringApplication.run(UnoApi.class, args);
    }
}

