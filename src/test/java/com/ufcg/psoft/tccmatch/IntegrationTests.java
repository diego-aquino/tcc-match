package com.ufcg.psoft.tccmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class IntegrationTests {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  protected String toJSON(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  protected <Response> Response fromJSON(String value, Class<Response> valueType)
    throws JsonProcessingException {
    return objectMapper.readValue(value, valueType);
  }
}
