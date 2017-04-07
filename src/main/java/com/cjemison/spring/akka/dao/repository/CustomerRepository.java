package com.cjemison.spring.akka.dao.repository;

import com.cjemison.spring.akka.dao.eo.CustomerEO;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by cjemison on 4/4/17.
 */
public interface CustomerRepository extends CrudRepository<CustomerEO, Long> {

  List<CustomerEO> findByLastName(String lastName);
}