package com.cjemison.spring.akkaExample.controller.impl;

import com.cjemison.spring.akkaExample.controller.CustomerController;
import com.cjemison.spring.akkaExample.controller.model.CustomerVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import static com.cjemison.spring.akkaExample.config.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

/**
 * Created by cjemison on 4/4/17.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DefaultCustomerControllerImpl implements CustomerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCustomerControllerImpl.class);
  private final ActorSystem actorSystem;

  @Autowired
  public DefaultCustomerControllerImpl(final ActorSystem actorSystem) {
    this.actorSystem = actorSystem;
  }

  @Override
  @RequestMapping(value = "/v1/customer", method = RequestMethod.POST, consumes = MediaType
        .APPLICATION_JSON_UTF8_VALUE)
  public DeferredResult<ResponseEntity<?>> save(@RequestBody final CustomerVO customerVO) {
    LOGGER.info("CustomerVO: {} ", customerVO);
    final ActorRef workerActor = actorSystem.actorOf(SPRING_EXTENSION_PROVIDER.get(actorSystem)
          .props("workerActor"), UUID.randomUUID().toString());
    final DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
    final FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
    final Future<Object> awaitable = Patterns.ask(workerActor, customerVO, Timeout
          .durationToTimeout(duration));
    try {
      CustomerVO customer = (CustomerVO) Await.result(awaitable, duration);
      deferredResult.setResult(ResponseEntity.created(new URI("/v1/customer")).body(customer));
    } catch (Exception e) {
      LOGGER.error("", e);
    }
    return deferredResult;
  }
}
