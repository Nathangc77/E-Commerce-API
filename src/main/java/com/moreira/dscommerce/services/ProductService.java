package com.moreira.dscommerce.services;

import com.moreira.dscommerce.dto.ProductDTO;
import com.moreira.dscommerce.entities.Product;
import com.moreira.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> result = this.productRepository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product);
        return dto;
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getImgUrl());
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

}
