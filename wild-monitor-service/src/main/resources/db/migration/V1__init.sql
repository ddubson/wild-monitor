CREATE TABLE projects_meta
(
    db_id       SERIAL PRIMARY KEY,
    id          VARCHAR(20) NOT NULL,
    project_key VARCHAR(30) NOT NULL,
    created_on  DATETIME
);

CREATE TABLE projects_record
(
    db_id        SERIAL PRIMARY KEY,
    meta_db_id   INTEGER REFERENCES projects_meta (db_id),
    project_name VARCHAR(40)
);

CREATE TABLE jobs_meta
(
    db_id       SERIAL PRIMARY KEY,
    job_id      VARCHAR(20) NOT NULL,
    created_on  DATETIME    NOT NULL,
    expires_on  DATETIME    NOT NULL,
    project_key VARCHAR(30) REFERENCES projects_meta (project_key)
);

CREATE TYPE JOB_STATUS
    as ENUM ('PENDING', 'STARTED', 'FAILED', 'SUCCEEDED', 'EXPIRED');

CREATE TABLE jobs_record
(
    db_id      SERIAL PRIMARY KEY,
    meta_db_id INTEGER REFERENCES jobs_meta (db_id),
    status     JOB_STATUS NOT NULL
);