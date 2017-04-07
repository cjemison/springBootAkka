package com.cjemison.spring.akka.service;

import com.cjemison.spring.akka.controller.model.CustomerVO;

/**
 * Created by cjemison on 4/4/17.
 */
public interface CustomerService {

  CustomerVO createCustomerEO(final CustomerVO customerVO);
}
