package com.bigdata.uno.api;

import com.bigdata.uno.service.ServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@SpringBootApplication
@ImportResource({ "classpath*:appcontext-database.xml"})
@Import(value = {ServiceContext.class})
@EnableSwagger2
public class UnoApi {

    @Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				log.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				log.info("ServletContext destroyed");
			}

		};
	}


    public static void main(String[] args) {
        SpringApplication.run(UnoApi.class, args);
    }
}

