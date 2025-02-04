package uws.edu.ii.springlaby2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import uws.edu.ii.springlaby2.models.Recipe;
import uws.edu.ii.springlaby2.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Secured("ROLE_ADMIN")
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return recipeRepository.existsById(id);
    }
}