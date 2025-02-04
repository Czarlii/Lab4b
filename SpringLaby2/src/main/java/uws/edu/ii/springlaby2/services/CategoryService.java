package uws.edu.ii.springlaby2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import uws.edu.ii.springlaby2.models.Category;
import uws.edu.ii.springlaby2.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Secured("ROLE_ADMIN")
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii o ID: " + id));
    }
}