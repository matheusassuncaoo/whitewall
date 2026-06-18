package br.com.whitewall.domain.entity;

import br.com.whitewall.domain.enums.RiskLevel;
import br.com.whitewall.domain.enums.ToolCategory;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "external_tools",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_external_tools_name", columnNames = "name")
        }
)
public class ExternalTool {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ToolCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 30)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected ExternalTool() {
        // Construtor exigido pelo JPA
    }

    public ExternalTool(
            String name,
            String description,
            String url,
            ToolCategory category,
            RiskLevel riskLevel
    ) {
        this.name = normalizeName(name);
        this.description = description;
        this.url = url;
        this.category = category;
        this.riskLevel = riskLevel;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.name != null) {
            this.name = normalizeName(this.name);
        }

        if (this.category == null) {
            this.category = ToolCategory.OTHER;
        }

        if (this.riskLevel == null) {
            this.riskLevel = RiskLevel.MEDIUM;
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

    public void updateInfo(
            String name,
            String description,
            String url,
            ToolCategory category,
            RiskLevel riskLevel
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome da ferramenta externa não pode ser vazio.");
        }

        if (category == null) {
            throw new IllegalArgumentException("A categoria da ferramenta externa não pode ser nula.");
        }

        if (riskLevel == null) {
            throw new IllegalArgumentException("O nível de risco da ferramenta externa não pode ser nulo.");
        }

        this.name = normalizeName(name);
        this.description = description;
        this.url = url;
        this.category = category;
        this.riskLevel = riskLevel;
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

    public String getUrl() {
        return url;
    }

    public ToolCategory getCategory() {
        return category;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
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
