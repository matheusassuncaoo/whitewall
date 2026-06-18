package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.DocumentFinding;
import br.com.whitewall.domain.enums.FindingType;
import br.com.whitewall.domain.enums.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentFindingRepository extends JpaRepository<DocumentFinding, UUID> {

    List<DocumentFinding> findAllByDocumentInspectionId(UUID documentInspectionId);

    List<DocumentFinding> findAllByType(FindingType type);

    List<DocumentFinding> findAllBySeverity(RiskLevel severity);

    List<DocumentFinding> findAllByDocumentInspectionIdOrderByCreatedAtAsc(UUID documentInspectionId);
}