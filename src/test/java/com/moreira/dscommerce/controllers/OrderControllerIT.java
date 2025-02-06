package com.moreira.dscommerce.controllers;

import com.moreira.dscommerce.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    private String adminUsername, clientUsername, adminPassword, clientPassword;
    private String adminToken, clientToken, invalidToken;
    private Long existingOrderId, nonExistingOrderId;

    @BeforeEach
    void setUp() throws Exception {
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        invalidToken = adminToken + "xpto";

        existingOrderId = 1L;
        nonExistingOrderId = 100L;
    }


    @Test
    public void findByIdShouldReturnOrderDTOWhenAdminLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", existingOrderId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingOrderId))
                .andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.client").exists())
                .andExpect(jsonPath("$.client.name").value("Maria Brown"))
                .andExpect(jsonPath("$.payment").exists())
                .andExpect(jsonPath("$.items[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenOrderIsFromClientLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", existingOrderId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(existingOrderId))
                .andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.client").exists())
                .andExpect(jsonPath("$.client.name").value("Maria Brown"))
                .andExpect(jsonPath("$.payment").exists())
                .andExpect(jsonPath("$.items[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void findByIdShouldThrowsForbiddenWhenOrderIsNotFromClientLogged() throws Exception {
        existingOrderId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", existingOrderId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldThrowsNotFoundWhenOrderDoesNotExistAndAdminLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", nonExistingOrderId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldThrowsNotFoundWhenOrderDoesNotExistAndClientLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", nonExistingOrderId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldThrowsUnauthorizedWhenOrderIdExistAndInvalidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", existingOrderId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
