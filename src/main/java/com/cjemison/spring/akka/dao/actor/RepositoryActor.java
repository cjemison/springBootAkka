package com.cjemison.spring.akka.dao.actor;

import com.cjemison.spring.akka.dao.eo.CustomerEO;
import com.cjemison.spring.akka.dao.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.UntypedActor;

/**
 * Created by cjemison on 4/7/17.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RepositoryActor extends UntypedActor {
  private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryActor.class);
  private final CustomerRepository repository;

  @Autowired
  public RepositoryActor(final CustomerRepository repository) {
    this.repository = repository;
  }

  @Override
  public void onReceive(final Object message) throws Throwable {
    if (message instanceof FindObject) {
      final FindObject findObjectRequest = (FindObject) message;
      LOGGER.info("Id: {}", findObjectRequest.getId());
      getSender().tell(repository.findOne(findObjectRequest.id), getSelf());
    } else if (message instanceof InsertObject) {
      final InsertObject insertObject = (InsertObject) message;
      LOGGER.info("New Customer: {}", insertObject);
      final CustomerEO customerEO = new CustomerEO(insertObject.getFirstName(), insertObject
            .getLastName());
      getSender().tell(repository.save(customerEO), getSelf());
    } else {
      unhandled(message);
    }
  }

  public static class FindObject {
    private final Long id;

    public FindObject(final Long id) {
      this.id = id;
    }

    public Long getId() {
      return id;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof FindObject)) return false;

      final FindObject that = (FindObject) o;

      return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
      return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
      return "FindObjectRequest{" +
            "id=" + id +
            '}';
    }
  }

  public static class InsertObject {
    private final String firstName;
    private final String lastName;

    public InsertObject(final String firstName,
                        final String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }


    public String getLastName() {
      return lastName;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof InsertObject)) return false;

      final InsertObject that = (InsertObject) o;

      if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that
            .getFirstName() != null)
        return false;
      return getLastName() != null ? getLastName().equals(that.getLastName()) : that.getLastName
            () == null;
    }

    @Override
    public int hashCode() {
      int result = getFirstName() != null ? getFirstName().hashCode() : 0;
      result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "InsertObject{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
  }
}
