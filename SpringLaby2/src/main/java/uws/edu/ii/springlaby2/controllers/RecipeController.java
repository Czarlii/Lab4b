package uws.edu.ii.springlaby2.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uws.edu.ii.springlaby2.exceptions.RecipeNotFoundException;
import uws.edu.ii.springlaby2.models.Category;
import uws.edu.ii.springlaby2.models.Recipe;
import uws.edu.ii.springlaby2.repositories.CategoryRepository;
import uws.edu.ii.springlaby2.services.RecipeService;

import java.util.List;

@Controller
@RequestMapping("/recipe")
@SessionAttributes({"recipe", "categories"})
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute("categories")
    public List<Category> loadCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/showList")
    public String showList(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipeList";
    }

    @GetMapping({"/add", "/edit"})
    public String showForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Recipe recipe;
        if (id != null) {
            recipe = recipeService.findById(id)
                    .orElseThrow(() -> new RecipeNotFoundException(id));
        } else {
            recipe = new Recipe();
        }

        if (recipe.getCategoryDetail() == null) {
            recipe.setCategoryDetail(new Category());
        }
        model.addAttribute("recipe", recipe);
        return "recipeForm";
    }

    @PostMapping("/save")
    public String processForm(@Valid Recipe recipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "recipeForm";
        }

        try {
            if (recipe.getCategoryDetail() != null && recipe.getCategoryDetail().getId() != null) {
                Category category = categoryRepository.findById(recipe.getCategoryDetail().getId())
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii o ID: " + recipe.getCategoryDetail().getId()));
                recipe.setCategoryDetail(category);
            } else {
                result.rejectValue("categoryDetail", "error.category", "Proszę wybrać kategorię");
                model.addAttribute("categories", categoryRepository.findAll());
                return "recipeForm";
            }

            recipeService.save(recipe);
            model.addAttribute("message", "Przepis został zapisany!");
            return "successFormView";
        } catch (Exception e) {
            result.rejectValue("categoryDetail", "error.category", "Wystąpił błąd podczas zapisywania");
            model.addAttribute("categories", categoryRepository.findAll());
            return "recipeForm";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        if (!recipeService.existsById(id)) {
            throw new RecipeNotFoundException(id);
        }
        recipeService.deleteById(id);
        return "redirect:/recipe/showList";
    }
}