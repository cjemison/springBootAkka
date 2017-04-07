package com.cjemison.spring.akka.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PreDestroy;

import akka.actor.ActorSystem;

import static com.cjemison.spring.akka.util.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

/**
 * Created by cjemison on 4/4/17.
 */
@Configuration
@ComponentScan(basePackages = "com.cjemison.spring.akka")
public class WebConfig extends WebMvcConfigurerAdapter implements InitializingBean {
  private ActorSystem system;

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  public ActorSystem actorSystem() {
    system = ActorSystem.create("akka-spring-demo");
    SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
    return system;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }

  @PreDestroy
  public void shutdown() throws Exception {
    system.shutdown();
  }
}
