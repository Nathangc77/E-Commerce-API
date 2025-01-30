package com.moreira.dscommerce.tests;

import com.moreira.dscommerce.dto.ProductDTO;
import com.moreira.dscommerce.entities.Category;
import com.moreira.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "Console PlayStation 5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 3999.0, "www.imagemps5.com");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }

    public static ProductDTO createProductDTO() {
        return new ProductDTO(createProduct());
    }
}
