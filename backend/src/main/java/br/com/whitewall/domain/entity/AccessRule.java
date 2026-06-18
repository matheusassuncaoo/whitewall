package br.com.whitewall.domain.entity;

import br.com.whitewall.domain.enums.AccessDecision;
import br.com.whitewall.domain.enums.RiskLevel;
import br.com.whitewall.domain.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "access_rules")
public class AccessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_tool_id")
    private ExternalTool externalTool;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 30)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccessDecision decision;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 30)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private Integer priority = 100;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected AccessRule() {
        // Construtor exigido pelo JPA
    }

    public AccessRule(
            String name,
            String description,
            Sector sector,
            ExternalTool externalTool,
            UserRole userRole,
            AccessDecision decision,
            RiskLevel riskLevel,
            Integer priority
    ) {
        this.name = normalizeName(name);
        this.description = description;
        this.sector = sector;
        this.externalTool = externalTool;
        this.userRole = userRole;
        this.decision = decision;
        this.riskLevel = riskLevel;
        this.priority = priority == null ? 100 : priority;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.name != null) {
            this.name = normalizeName(this.name);
        }

        if (this.decision == null) {
            this.decision = AccessDecision.PENDING_APPROVAL;
        }

        if (this.riskLevel == null) {
            this.riskLevel = RiskLevel.MEDIUM;
        }

        if (this.priority == null) {
            this.priority = 100;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (this.name != null) {
            this.name = normalizeName(this.name);
        }
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateRule(
            String name,
            String description,
            Sector sector,
            ExternalTool externalTool,
            UserRole userRole,
            AccessDecision decision,
            RiskLevel riskLevel,
            Integer priority
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome da regra não pode ser vazio.");
        }

        if (decision == null) {
            throw new IllegalArgumentException("A decisão da regra não pode ser nula.");
        }

        if (riskLevel == null) {
            throw new IllegalArgumentException("O nível de risco da regra não pode ser nulo.");
        }

        this.name = normalizeName(name);
        this.description = description;
        this.sector = sector;
        this.externalTool = externalTool;
        this.userRole = userRole;
        this.decision = decision;
        this.riskLevel = riskLevel;
        this.priority = priority == null ? 100 : priority;
    }

    private String normalizeName(String name) {
        if (name == null) {
            return null;
        }

        return name.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Sector getSector() {
        return sector;
    }

    public ExternalTool getExternalTool() {
        return externalTool;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public AccessDecision getDecision() {
        return decision;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public Integer getPriority() {
        return priority;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
