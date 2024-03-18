CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS revocation_list (
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    credential_urn varchar(256),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS issued_list (
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    credential_urn varchar(256),
    issuer_did varchar(256),
    created_at TIMESTAMP NOT NULL
);

