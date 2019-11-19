package com.framework.admin.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author :Sun.Han
 * @since :2017年04月27日
 */
@Configuration
public class WebConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return container -> {
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/admin/common/404.html");
//            container.addErrorPages(error404Page);
        };
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
        }

    }
}
