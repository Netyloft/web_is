package com.example.api.configuration;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ServerConfiguration {

    @Bean
    @ConditionalOnClass(Tomcat.class)
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(
            connector -> connector.setProperty("relaxedQueryChars", "|{}[]")
        );
        return factory;
    }

    @Bean
    public Clock provideClock() {
        return Clock.systemDefaultZone();
    }

}
