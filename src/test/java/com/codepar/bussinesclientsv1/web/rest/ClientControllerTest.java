package com.codepar.bussinesclientsv1.web.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codepar.bussinesclientsv1.helper.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("return createClientResponse when create client endpoint is invoked")
    void returnCreateClientResponseWhenCreateClientEndPointIsInvoked() throws Exception {
        String fileNameRequest = "src/test/resources/request/CreateClientRequest.json";
        String json = TestHelper.readJsonFile(fileNameRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/client/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andReturn();

        MvcResult asyncResult = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(HttpStatus.CREATED.value(),asyncResult.getResponse().getStatus());
    }

}