package com.ufcg.psoft.tccmatch.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class SampleControllerTests extends IntegrationTests {

  @Test
  void sampleResponse() throws Exception {
    mvc
      .perform(get("/api").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().string(is("OK")));
  }
}
