package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();

    Category findById(Long category);
}