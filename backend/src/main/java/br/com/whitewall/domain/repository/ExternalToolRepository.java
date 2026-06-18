package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.ExternalTool;
import br.com.whitewall.domain.enums.RiskLevel;
import br.com.whitewall.domain.enums.ToolCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExternalToolRepository extends JpaRepository<ExternalTool, UUID> {

    Optional<ExternalTool> findByName(String name);

    Optional<ExternalTool> findByNameAndActiveTrue(String name);

    boolean existsByName(String name);

    List<ExternalTool> findAllByActiveTrue();

    List<ExternalTool> findAllByCategory(ToolCategory category);

    List<ExternalTool> findAllByCategoryAndActiveTrue(ToolCategory category);

    List<ExternalTool> findAllByRiskLevel(RiskLevel riskLevel);

    List<ExternalTool> findAllByRiskLevelAndActiveTrue(RiskLevel riskLevel);
}