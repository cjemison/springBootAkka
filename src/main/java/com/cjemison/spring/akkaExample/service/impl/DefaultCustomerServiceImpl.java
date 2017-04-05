package com.cjemison.spring.akkaExample.service.impl;

import com.cjemison.spring.akkaExample.controller.model.CustomerVO;
import com.cjemison.spring.akkaExample.dao.eo.CustomerEO;
import com.cjemison.spring.akkaExample.dao.repository.CustomerRepository;
import com.cjemison.spring.akkaExample.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cjemison on 4/4/17.
 */
@Service("defaultCustomerService")
public class DefaultCustomerServiceImpl implements CustomerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCustomerServiceImpl.class);
  private final CustomerRepository customerRepository;

  @Autowired
  public DefaultCustomerServiceImpl(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CustomerVO createCustomerEO(final CustomerVO customerVO) {
    LOGGER.info("CustomerVO: {}", customerVO);
    final CustomerEO customerEO = customerRepository.save(new CustomerEO(customerVO.getFirstName
          (), customerVO
          .getLastName()));
    final CustomerVO vo = new CustomerVO();
    vo.setId(customerEO.getId());
    vo.setFirstName(customerEO.getFirstName());
    vo.setLastName(customerEO.getLastName());
    return vo;
  }
}
