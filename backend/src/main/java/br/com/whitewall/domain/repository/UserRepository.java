package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.User;
import br.com.whitewall.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>

{
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<User> findAllByActiveTrue();

    List<User> findAllByRole(UserRole role);

    List<User> findAllByRoleAndActiveTrue(UserRole role);

    List<User> findAllBySectorId(UUID sectorId);

    List<User> findAllBySectorIdAndActiveTrue(UUID sectorId);


}
