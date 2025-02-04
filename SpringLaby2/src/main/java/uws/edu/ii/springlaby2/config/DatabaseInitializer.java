package uws.edu.ii.springlaby2.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uws.edu.ii.springlaby2.models.Category;
import uws.edu.ii.springlaby2.models.Recipe;
import uws.edu.ii.springlaby2.models.Role;
import uws.edu.ii.springlaby2.models.User;
import uws.edu.ii.springlaby2.repositories.CategoryRepository;
import uws.edu.ii.springlaby2.repositories.RecipeRepository;
import uws.edu.ii.springlaby2.repositories.RoleRepository;
import uws.edu.ii.springlaby2.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class DatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public InitializingBean init(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        return () -> {
            if (categoryRepository.count() == 0) {
                Category danieGlowne = new Category();
                danieGlowne.setName("Danie główne");
                danieGlowne = categoryRepository.save(danieGlowne);

                Category deser = new Category();
                deser.setName("Deser");
                deser = categoryRepository.save(deser);

                Category przekaska = new Category();
                przekaska.setName("Przekąska");
                przekaska = categoryRepository.save(przekaska);

                Recipe spaghetti = new Recipe();
                spaghetti.setName("Spaghetti Carbonara");
                spaghetti.setAuthor("Marek");
                spaghetti.setDifficulty(5.00f);
                spaghetti.setReleaseDate(LocalDate.of(2022, 10, 10));
                spaghetti.setVegan(false);
                spaghetti.setCategoryDetail(danieGlowne);
                recipeRepository.save(spaghetti);

                Recipe szarlotka = new Recipe();
                szarlotka.setName("Szarlotka");
                szarlotka.setAuthor("Janina");
                szarlotka.setDifficulty(3.50f);
                szarlotka.setReleaseDate(LocalDate.of(2020, 12, 5));
                szarlotka.setVegan(true);
                szarlotka.setCategoryDetail(deser);
                recipeRepository.save(szarlotka);

                Recipe bit = new Recipe();
                bit.setName("3-bit");
                bit.setAuthor("Kasia");
                bit.setDifficulty(4.50f);
                bit.setReleaseDate(LocalDate.of(2024, 1, 1));
                bit.setVegan(true);
                bit.setCategoryDetail(deser);
                recipeRepository.save(bit);
            }

            if (roleRepository.count() == 0) {
                // Inicjalizacja ról
                Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                // Inicjalizacja użytkowników
                User user = new User("user", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder.encode("user"));

                User admin = new User("admin", true);
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder.encode("admin"));

                User useradmin = new User("useradmin", true);
                useradmin.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                useradmin.setPassword(passwordEncoder.encode("useradmin"));

                userRepository.save(user);
                userRepository.save(admin);
                userRepository.save(useradmin);
            }

        };
    }
}
