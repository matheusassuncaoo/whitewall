package br.com.whitewall.domain.entity;

import br.com.whitewall.domain.enums.FindingType;
import br.com.whitewall.domain.enums.RiskLevel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document_findings")
public class DocumentFinding {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_inspection_id", nullable = false)
    private DocumentInspection documentInspection;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private FindingType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RiskLevel severity;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected DocumentFinding() {
        // Construtor exigido pelo JPA
    }

    public DocumentFinding(
            DocumentInspection documentInspection,
            FindingType type,
            RiskLevel severity,
            String description
    ) {
        this.documentInspection = documentInspection;
        this.type = type == null ? FindingType.UNKNOWN : type;
        this.severity = severity == null ? RiskLevel.MEDIUM : severity;
        this.description = normalizeText(description);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.type == null) {
            this.type = FindingType.UNKNOWN;
        }

        if (this.severity == null) {
            this.severity = RiskLevel.MEDIUM;
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

    public DocumentInspection getDocumentInspection() {
        return documentInspection;
    }

    public FindingType getType() {
        return type;
    }

    public RiskLevel getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}