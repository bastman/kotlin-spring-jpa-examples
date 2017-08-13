CREATE TABLE PropertyLinks (
  id           UUID PRIMARY KEY,
  version      INT NOT NULL,
  created_at   TIMESTAMP NOT NULL,
  modified_at  TIMESTAMP NOT NULL,

  fk_from       UUID NOT NULL REFERENCES Property,
  fk_to       UUID NOT NULL REFERENCES Property
);

ALTER TABLE PropertyLinks ADD CONSTRAINT from_and_to_unique UNIQUE (fk_from, fk_to);



