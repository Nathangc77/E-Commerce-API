package com.moreira.dscommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moreira.dscommerce.dto.ProductDTO;
import com.moreira.dscommerce.entities.Category;
import com.moreira.dscommerce.entities.Product;
import com.moreira.dscommerce.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminUsername, clientUsername, adminPassword, clientPassword;
    private String productName;
    private String adminToken, clientToken, invalidToken;
    private Long existingProductId, nonExistingProductId, dependentProductId;

    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    void setUp() throws Exception {
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        invalidToken = adminToken + "xpto";

        productName = "Macbook";

        category = new Category(2L, "Eletro");
        product = new Product(null, "Console Playstation 5", "Lorem ipsum, dolor sit amet consectetur adipisicing elit.", 3999.90, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");
        product.getCategories().add(category);
        productDTO = new ProductDTO(product);

        existingProductId = 24L;
        nonExistingProductId = 100L;
        dependentProductId = 3L;
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.content[0].price").value(90.5));
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products?name={productName}", productName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").value(3L))
                .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.content[0].price").value(1250.0));
    }

    @Test
    public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(26L))
                .andExpect(jsonPath("$.name").value("Console Playstation 5"))
                .andExpect(jsonPath("$.description").value("Lorem ipsum, dolor sit amet consectetur adipisicing elit."))
                .andExpect(jsonPath("$.price").value(3999.90))
                .andExpect(jsonPath("$.imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"))
                .andExpect(jsonPath("$.categories[0].id").value(2L));
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception {
        product.setName("ab");
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
        product.setDescription("ab");
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenAdminLoggedAndNegativePrice() throws Exception {
        product.setPrice(-50.0);
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
        product.setPrice(0.0);
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenAdminLoggedAndHasNotCategory() throws Exception {
        product.getCategories().clear();
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsForbiddenWhenClientLogged() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + clientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdIsValidAndAdminLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldThrowsNotFoundWhenIdDoesNotExistsAndAdminLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", nonExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteShouldThrowsBadRequestWhenDependentIdAndAdminLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", dependentProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldThrowsForbiddenWhenClientLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
