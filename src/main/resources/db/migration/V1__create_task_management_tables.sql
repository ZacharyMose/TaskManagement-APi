CREATE TYPE task_status AS ENUM ('TO_DO', 'IN_PROGRESS', 'COMPLETED','CANCELED');
CREATE TABLE tasks(
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    due_date TIMESTAMPTZ NOT NULL,
    task_status task_status NOT NULL,
    completed BOOLEAN NOT NULL
);