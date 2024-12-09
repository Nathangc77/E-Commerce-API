package com.moreira.dscommerce.services;

import com.moreira.dscommerce.dto.CategoryDTO;
import com.moreira.dscommerce.entities.Category;
import com.moreira.dscommerce.repositories.CategotyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategotyRepository categotyRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categotyRepository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).toList();
    }
}
