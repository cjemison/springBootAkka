package com.cjemison.spring.akkaExample.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import akka.actor.ActorSystem;

import static com.cjemison.spring.akkaExample.config.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

/**
 * Created by cjemison on 4/4/17.
 */
@Configuration
@ComponentScan(basePackages = "com.cjemison.spring.akkaExample")
public class WebConfig extends WebMvcConfigurerAdapter implements InitializingBean {

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  public ActorSystem actorSystem() {
    final ActorSystem system = ActorSystem.create("akka-spring-demo");
    SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
    return system;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}
