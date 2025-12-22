package com.techsolution.gestion_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsolution.gestion_app.domain.entities.StockAlert;
import com.techsolution.gestion_app.service.StockAlertService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlertController.class)
class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockAlertService stockAlertService;

    @Test
    void getAlertsReturnsList() throws Exception {
        StockAlert a = new StockAlert();
        a.setProductName("P");
        when(stockAlertService.getAll()).thenReturn(List.of(a));

        mockMvc.perform(get("/alerts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("P"));
    }
}
