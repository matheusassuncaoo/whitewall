CREATE TABLE sectors (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    sector_id UUID,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT fk_users_sector FOREIGN KEY (sector_id) REFERENCES sectors(id)
);

CREATE TABLE external_tools (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    url VARCHAR(255),
    category VARCHAR(40) NOT NULL,
    risk_level VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_external_tools_name UNIQUE (name)
);

CREATE TABLE access_rules (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    sector_id UUID,
    external_tool_id UUID,
    user_role VARCHAR(30),
    decision VARCHAR(30) NOT NULL,
    risk_level VARCHAR(30) NOT NULL,
    priority INTEGER NOT NULL DEFAULT 100,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_access_rules_sector FOREIGN KEY (sector_id) REFERENCES sectors(id),
    CONSTRAINT fk_access_rules_external_tool FOREIGN KEY (external_tool_id) REFERENCES external_tools(id)
);

CREATE TABLE document_inspections (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    external_tool_id UUID NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_extension VARCHAR(20) NOT NULL,
    content_type VARCHAR(120),
    file_size BIGINT NOT NULL,
    file_hash VARCHAR(128) NOT NULL,
    purpose VARCHAR(120) NOT NULL,
    justification VARCHAR(1000),
    decision VARCHAR(30) NOT NULL,
    risk_level VARCHAR(30) NOT NULL,
    decision_reason VARCHAR(1000),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_document_inspections_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_document_inspections_external_tool FOREIGN KEY (external_tool_id) REFERENCES external_tools(id)
);

CREATE TABLE document_findings (
    id UUID PRIMARY KEY,
    document_inspection_id UUID NOT NULL,
    type VARCHAR(40) NOT NULL,
    severity VARCHAR(30) NOT NULL,
    description VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_document_findings_inspection FOREIGN KEY (document_inspection_id) REFERENCES document_inspections(id)
);

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    user_id UUID,
    action VARCHAR(50) NOT NULL,
    entity_name VARCHAR(80) NOT NULL,
    entity_id VARCHAR(80),
    description VARCHAR(1000) NOT NULL,
    metadata TEXT,
    ip_address VARCHAR(80),
    user_agent VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_audit_logs_user FOREIGN KEY (user_id) REFERENCES users(id)
);