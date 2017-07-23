CREATE TABLE Author (
  id           UUID PRIMARY KEY,
  version      INT NOT NULL,
  created_at   TIMESTAMP NOT NULL,
  modified_at  TIMESTAMP NOT NULL,

  first_name   TEXT NOT NULL,
  last_name    TEXT NOT NULL,
  email        TEXT NOT NULL
);

CREATE TABLE Tweet
(
  id                         UUID PRIMARY KEY,
  version                    INT NOT NULL,
  created_at                 TIMESTAMP NOT NULL,
  modified_at                TIMESTAMP NOT NULL,

  author_id                  UUID NOT NULL REFERENCES Author,
  message                    TEXT NOT NULL
);

