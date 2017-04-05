package com.cjemison.spring.akkaExample.service;

import com.cjemison.spring.akkaExample.controller.model.CustomerVO;

/**
 * Created by cjemison on 4/4/17.
 */
public interface CustomerService {

  CustomerVO createCustomerEO(final CustomerVO customerVO);
}
