package com.moreira.dscommerce.services;

import com.moreira.dscommerce.dto.CategoryDTO;
import com.moreira.dscommerce.entities.Category;
import com.moreira.dscommerce.repositories.CategotyRepository;
import com.moreira.dscommerce.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategotyRepository repository;

    private Category category;
    private List<Category> list;

    @BeforeEach
    void setUp() throws Exception {
        category = CategoryFactory.createCategory();

        list = new ArrayList<>();
        list.add(category);

        Mockito.when(repository.findAll()).thenReturn(list);
    }

    @Test
    public void findAllShouldReturnListCategoryDTO() {

        List<CategoryDTO> result = service.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1L, result.getFirst().getId());
        Assertions.assertEquals("Games", result.getFirst().getName());
    }
}
