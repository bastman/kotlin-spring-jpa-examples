CREATE TABLE PropertyCluster (
  id         UUID PRIMARY KEY,
  version    INT       NOT NULL,

  created_at TIMESTAMP NOT NULL,
  modified_at TIMESTAMP NOT NULL
);

ALTER TABLE Property
  ADD fk_property_cluster_id UUID NULL DEFAULT NULL REFERENCES PropertyCluster;
