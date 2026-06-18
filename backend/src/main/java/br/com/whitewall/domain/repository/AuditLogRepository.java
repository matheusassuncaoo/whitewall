package br.com.whitewall.domain.repository;

import br.com.whitewall.domain.entity.AuditLog;
import br.com.whitewall.domain.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findTop50ByOrderByCreatedAtDesc();

    List<AuditLog> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    List<AuditLog> findAllByActionOrderByCreatedAtDesc(AuditAction action);

    List<AuditLog> findAllByEntityNameOrderByCreatedAtDesc(String entityName);

    List<AuditLog> findAllByEntityNameAndEntityIdOrderByCreatedAtDesc(
            String entityName,
            String entityId
    );

    List<AuditLog> findAllByCreatedAtBetweenOrderByCreatedAtDesc(
            LocalDateTime start,
            LocalDateTime end
    );
}