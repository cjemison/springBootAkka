package com.cjemison.spring.akka.service;

import com.cjemison.spring.akka.controller.model.CustomerVO;
import com.cjemison.spring.akka.dao.actor.RepositoryActor;

import java.util.Optional;

/**
 * Created by cjemison on 4/7/17.
 */
public interface CustomerService {

  Optional<RepositoryActor.InsertObject> validateInsertCustomer(final CustomerVO customerVO);
}
