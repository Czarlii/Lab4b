package uws.edu.ii.springlaby2.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import uws.edu.ii.springlaby2.models.User;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}