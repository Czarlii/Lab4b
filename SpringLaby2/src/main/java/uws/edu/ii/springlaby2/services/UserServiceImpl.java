package uws.edu.ii.springlaby2.services;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uws.edu.ii.springlaby2.config.ProfileNames;
import uws.edu.ii.springlaby2.models.Role;
import uws.edu.ii.springlaby2.repositories.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile(ProfileNames.USERS_IN_DATABASE)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika: " + username);
        }
        return toUserDetails(user);
    }

    @Override
    public uws.edu.ii.springlaby2.models.User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private UserDetails toUserDetails(uws.edu.ii.springlaby2.models.User user) {
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getType().name().replace("ROLE_", ""))
                .collect(Collectors.toSet());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .disabled(!user.isEnabled())
                .build();
    }

}