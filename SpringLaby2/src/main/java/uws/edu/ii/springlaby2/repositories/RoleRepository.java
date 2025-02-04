package uws.edu.ii.springlaby2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uws.edu.ii.springlaby2.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}