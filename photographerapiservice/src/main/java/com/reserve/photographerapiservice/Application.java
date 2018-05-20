package com.reserve.photographerapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
//        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
//        factory.addDeploymentInfoCustomizers((UndertowDeploymentInfoCustomizer) (DeploymentInfo deploymentInfo) -> {
//            deploymentInfo.setAllowNonStandardWrappers(true);
//        });
//        factory.addBuilderCustomizers(builder -> {
//            builder.setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, true)
//                    .build();
//        });
//        return factory;
//    }
}
