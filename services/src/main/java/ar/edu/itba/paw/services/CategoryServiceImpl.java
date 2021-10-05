package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> listCategories() {
        return categoryDao.listAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id).orElse(null);
    }
}
