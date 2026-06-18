package br.com.whitewall.domain.entity;

import br.com.whitewall.domain.enums.AccessDecision;
import br.com.whitewall.domain.enums.DocumentStatus;
import br.com.whitewall.domain.enums.RiskLevel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document_inspections")
public class DocumentInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "external_tool_id", nullable = false)
    private ExternalTool externalTool;

    @Column(name = "original_file_name", nullable = false, length = 255)
    private String originalFileName;

    @Column(name = "file_extension", nullable = false, length = 20)
    private String fileExtension;

    @Column(name = "content_type", length = 120)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_hash", nullable = false, length = 128)
    private String fileHash;

    @Column(nullable = false, length = 120)
    private String purpose;

    @Column(length = 1000)
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccessDecision decision;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 30)
    private RiskLevel riskLevel;

    @Column(name = "decision_reason", length = 1000)
    private String decisionReason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DocumentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected DocumentInspection() {
        // Construtor exigido pelo JPA
    }

    public DocumentInspection(
            User user,
            ExternalTool externalTool,
            String originalFileName,
            String fileExtension,
            String contentType,
            Long fileSize,
            String fileHash,
            String purpose,
            String justification
    ) {
        this.user = user;
        this.externalTool = externalTool;
        this.originalFileName = normalizeText(originalFileName);
        this.fileExtension = normalizeExtension(fileExtension);
        this.contentType = normalizeText(contentType);
        this.fileSize = fileSize;
        this.fileHash = normalizeText(fileHash);
        this.purpose = normalizeText(purpose);
        this.justification = normalizeText(justification);
        this.decision = AccessDecision.PENDING_APPROVAL;
        this.riskLevel = RiskLevel.MEDIUM;
        this.status = DocumentStatus.WAITING_APPROVAL;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.decision == null) {
            this.decision = AccessDecision.PENDING_APPROVAL;
        }

        if (this.riskLevel == null) {
            this.riskLevel = RiskLevel.MEDIUM;
        }

        if (this.status == null) {
            this.status = DocumentStatus.WAITING_APPROVAL;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsAllowed(String reason) {
        this.decision = AccessDecision.ALLOWED;
        this.riskLevel = RiskLevel.LOW;
        this.status = DocumentStatus.ANALYZED;
        this.decisionReason = reason;
    }

    public void markAsBlocked(RiskLevel riskLevel, String reason) {
        this.decision = AccessDecision.BLOCKED;
        this.riskLevel = riskLevel == null ? RiskLevel.HIGH : riskLevel;
        this.status = DocumentStatus.ANALYZED;
        this.decisionReason = reason;
    }

    public void markAsPendingApproval(RiskLevel riskLevel, String reason) {
        this.decision = AccessDecision.PENDING_APPROVAL;
        this.riskLevel = riskLevel == null ? RiskLevel.MEDIUM : riskLevel;
        this.status = DocumentStatus.WAITING_APPROVAL;
        this.decisionReason = reason;
    }

    public void approve(String reason) {
        this.decision = AccessDecision.ALLOWED;
        this.status = DocumentStatus.APPROVED;
        this.decisionReason = reason;
    }

    public void reject(String reason) {
        this.decision = AccessDecision.BLOCKED;
        this.status = DocumentStatus.REJECTED;
        this.decisionReason = reason;
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        return value.trim();
    }

    private String normalizeExtension(String extension) {
        if (extension == null) {
            return null;
        }

        return extension.trim().toLowerCase();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public ExternalTool getExternalTool() {
        return externalTool;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getJustification() {
        return justification;
    }

    public AccessDecision getDecision() {
        return decision;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public String getDecisionReason() {
        return decisionReason;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}