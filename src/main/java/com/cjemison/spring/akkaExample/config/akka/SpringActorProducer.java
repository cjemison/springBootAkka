package com.cjemison.spring.akkaExample.config.akka;

import org.springframework.context.ApplicationContext;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;

/**
 * Created by cjemison on 4/4/17.
 */
public class SpringActorProducer implements IndirectActorProducer {

  private ApplicationContext applicationContext;

  private String beanActorName;

  public SpringActorProducer(ApplicationContext applicationContext,
                             String beanActorName) {
    this.applicationContext = applicationContext;
    this.beanActorName = beanActorName;
  }

  @Override
  public Actor produce() {
    return (Actor) applicationContext.getBean(beanActorName);
  }

  @Override
  public Class<? extends Actor> actorClass() {
    return (Class<? extends Actor>) applicationContext
          .getType(beanActorName);
  }
}
