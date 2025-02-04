package uws.edu.ii.springlaby2.controllers.advices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uws.edu.ii.springlaby2.exceptions.RecipeNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    public String handleRecipeNotFound(RecipeNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "errors/recipeNotFound"; // 404
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("message", "Wystąpił nieoczekiwany błąd: " + ex.getMessage());
        return "errors/genericError"; // 500
    }
}