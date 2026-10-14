package et.etherplatforms.finance.core.audit;

import java.time.LocalDateTime;

public final class AuditMetadata {

    private final String createdBy;
    private final LocalDateTime createdAt;
    private final String description;

    public AuditMetadata(String createdBy, String description) {
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }
}
