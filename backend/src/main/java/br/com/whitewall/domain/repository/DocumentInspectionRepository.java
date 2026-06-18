package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.DocumentInspection;
import br.com.whitewall.domain.enums.AccessDecision;
import br.com.whitewall.domain.enums.DocumentStatus;
import br.com.whitewall.domain.enums.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentInspectionRepository extends JpaRepository<DocumentInspection, UUID> {

    List<DocumentInspection> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    List<DocumentInspection> findAllByExternalToolIdOrderByCreatedAtDesc(UUID externalToolId);

    List<DocumentInspection> findAllByDecisionOrderByCreatedAtDesc(AccessDecision decision);

    List<DocumentInspection> findAllByStatusOrderByCreatedAtDesc(DocumentStatus status);

    List<DocumentInspection> findAllByRiskLevelOrderByCreatedAtDesc(RiskLevel riskLevel);

    List<DocumentInspection> findAllByUserIdAndDecisionOrderByCreatedAtDesc(
            UUID userId,
            AccessDecision decision
    );

    List<DocumentInspection> findTop20ByOrderByCreatedAtDesc();
}