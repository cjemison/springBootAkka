package com.cjemison.spring.akka.service.impl;

import com.cjemison.spring.akka.controller.model.CustomerVO;
import com.cjemison.spring.akka.dao.actor.RepositoryActor;
import com.cjemison.spring.akka.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by cjemison on 4/7/17.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

  @Override
  public Optional<RepositoryActor.InsertObject> validateInsertCustomer(final CustomerVO
                                                                             customerVO) {
    final RepositoryActor.InsertObject insertObject = new RepositoryActor.InsertObject
          (customerVO.getFirstName(), customerVO.getLastName());
    return Optional.of(insertObject);
  }
}
