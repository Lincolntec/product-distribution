package com.ubs.teste.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import junit.framework.Assert;

class CalculationControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void calculationController() {

        MvcResult mvcResult;

        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/calculation"))
                    .andDo(System.err::print).andReturn();

            Assert.assertEquals(HttpStatus.OK, mvcResult.getResponse());

        } catch (Exception e) {

        }
    }
}
