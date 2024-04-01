package com.justdo.plug.blog.domain.category.service;

import com.justdo.plug.blog.domain.category.Category;
import com.justdo.plug.blog.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void save(Category category){

        categoryRepository.save(category);
    }
}
