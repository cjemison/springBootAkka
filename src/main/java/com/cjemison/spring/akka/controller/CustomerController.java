package com.cjemison.spring.akka.controller;

import com.cjemison.spring.akka.controller.model.CustomerVO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by cjemison on 4/4/17.
 */
public interface CustomerController {

  DeferredResult<ResponseEntity<?>> save(@RequestBody final CustomerVO customerVO);
}
