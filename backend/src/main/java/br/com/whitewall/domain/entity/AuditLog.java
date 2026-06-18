package br.com.whitewall.domain.entity;

import br.com.whitewall.domain.enums.AuditAction;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AuditAction action;

    @Column(name = "entity_name", nullable = false, length = 80)
    private String entityName;

    @Column(name = "entity_id", length = 80)
    private String entityId;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "ip_address", length = 80)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected AuditLog() {
        // Construtor exigido pelo JPA
    }

    public AuditLog(
            User user,
            AuditAction action,
            String entityName,
            String entityId,
            String description,
            String metadata,
            String ipAddress,
            String userAgent
    ) {
        this.user = user;
        this.action = action;
        this.entityName = normalizeText(entityName);
        this.entityId = normalizeText(entityId);
        this.description = normalizeText(description);
        this.metadata = normalizeText(metadata);
        this.ipAddress = normalizeText(ipAddress);
        this.userAgent = normalizeText(userAgent);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.action == null) {
            throw new IllegalStateException("A ação de auditoria não pode ser nula.");
        }

        if (this.entityName == null || this.entityName.isBlank()) {
            throw new IllegalStateException("O nome da entidade auditada não pode ser vazio.");
        }

        if (this.description == null || this.description.isBlank()) {
            throw new IllegalStateException("A descrição da auditoria não pode ser vazia.");
        }
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        return value.trim();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public AuditAction getAction() {
        return action;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getDescription() {
        return description;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}