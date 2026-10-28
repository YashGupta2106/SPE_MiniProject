package com.calculator;

import com.calculator.controller.CalculatorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAdd() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.result").value(15.0));
    }

    @Test
    void testSubtract() throws Exception {
        mockMvc.perform(get("/api/v1/subtract").param("a", "10").param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void testMultiply() throws Exception {
        mockMvc.perform(get("/api/v1/multiply").param("a", "4").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(12.0));
    }

    @Test
    void testDivide() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "10").param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void testDivideByZero() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "10").param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Division by zero is not allowed."));
    }
}