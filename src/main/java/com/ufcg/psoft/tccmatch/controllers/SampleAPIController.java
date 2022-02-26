package com.ufcg.psoft.tccmatch.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SampleAPIController {

  @GetMapping("")
  public ResponseEntity<String> getSampleResponse() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
}
