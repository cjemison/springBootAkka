package com.cjemison.spring.akkaExample.actor;

import com.cjemison.spring.akkaExample.controller.model.CustomerVO;
import com.cjemison.spring.akkaExample.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.UntypedActor;

/**
 * Created by cjemison on 4/4/17.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkerActor extends UntypedActor {
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerActor.class);

  private final CustomerService customerService;

  @Autowired
  public WorkerActor(@Qualifier("defaultCustomerService") final CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void onReceive(final Object message) throws Throwable {
    if (message instanceof CustomerVO) {
      final CustomerVO customerVO = (CustomerVO) message;
      LOGGER.info("CustomerEO: {}", customerVO);
      getSender().tell(customerService.createCustomerEO(customerVO), getSender());
    } else {
      unhandled(message);
    }
  }
}
