package com.cjemison.spring.akkaExample.dao.repository;

import com.cjemison.spring.akkaExample.dao.eo.CustomerEO;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by cjemison on 4/4/17.
 */
public interface CustomerRepository extends CrudRepository<CustomerEO, Long> {

  List<CustomerEO> findByLastName(String lastName);
}