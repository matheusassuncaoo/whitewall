package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.AccessRule;
import br.com.whitewall.domain.enums.AccessDecision;
import br.com.whitewall.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccessRuleRepository extends JpaRepository<AccessRule, UUID> {

    List<AccessRule> findAllByActiveTrueOrderByPriorityAsc();

    List<AccessRule> findAllByDecisionAndActiveTrueOrderByPriorityAsc(AccessDecision decision);

    List<AccessRule> findAllByUserRoleAndActiveTrueOrderByPriorityAsc(UserRole userRole);

    List<AccessRule> findAllBySectorIdAndActiveTrueOrderByPriorityAsc(UUID sectorId);

    List<AccessRule> findAllByExternalToolIdAndActiveTrueOrderByPriorityAsc(UUID externalToolId);

    List<AccessRule> findAllBySectorIdAndExternalToolIdAndActiveTrueOrderByPriorityAsc(
            UUID sectorId,
            UUID externalToolId
    );

    List<AccessRule> findAllBySectorIdAndExternalToolIdAndUserRoleAndActiveTrueOrderByPriorityAsc(
            UUID sectorId,
            UUID externalToolId,
            UserRole userRole
    );
}