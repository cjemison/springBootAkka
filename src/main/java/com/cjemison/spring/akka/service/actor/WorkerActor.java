package com.cjemison.spring.akka.service.actor;

import com.cjemison.spring.akka.controller.model.CustomerVO;
import com.cjemison.spring.akka.dao.actor.RepositoryActor;
import com.cjemison.spring.akka.dao.eo.CustomerEO;
import com.cjemison.spring.akka.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import static com.cjemison.spring.akka.util.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

/**
 * Created by cjemison on 4/4/17.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkerActor extends UntypedActor {
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerActor.class);
  private final ActorSystem actorSystem;
  private final CustomerService customerService;

  @Autowired
  public WorkerActor(final ActorSystem actorSystem, final CustomerService customerService) {
    this.actorSystem = actorSystem;
    this.customerService = customerService;
  }

  @Override
  public void onReceive(final Object message) throws Throwable {
    if (message instanceof CustomerVO) {
      final CustomerVO customerVO = (CustomerVO) message;
      LOGGER.info("CustomerEO: {}, name: {]", customerVO, getSender());
      final ActorRef repositoryActor = actorSystem.actorOf(SPRING_EXTENSION_PROVIDER.get
            (actorSystem)
            .props("repositoryActor"), UUID.randomUUID().toString());

      final Optional<RepositoryActor.InsertObject> optional = customerService
            .validateInsertCustomer(customerVO);
      if (optional.isPresent()) {

        final FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
        final Future<Object> awaitable = Patterns.ask(repositoryActor, optional.get(), Timeout
              .durationToTimeout(duration));

        CustomerEO customer = (CustomerEO) Await.result(awaitable, duration);
        final CustomerVO vo = new CustomerVO();
        vo.setId(customer.getId());
        vo.setFirstName(customer.getFirstName());
        vo.setLastName(customer.getLastName());
        getSender().tell(vo, getSender());
      } else {
        getSender().tell(new InsertFailureObject("couldn't insert.", customerVO), getSender());
      }
    } else {
      unhandled(message);
    }
  }

  public static class InsertFailureObject {
    private final String message;
    private final CustomerVO customerVO;

    public InsertFailureObject(final String message,
                               final CustomerVO customerVO) {
      this.message = message;
      this.customerVO = customerVO;
    }

    public String getMessage() {
      return message;
    }

    public CustomerVO getCustomerVO() {
      return customerVO;
    }

    @Override
    public String toString() {
      return "InsertFailureObject{" +
            "message='" + message + '\'' +
            '}';
    }
  }
}
