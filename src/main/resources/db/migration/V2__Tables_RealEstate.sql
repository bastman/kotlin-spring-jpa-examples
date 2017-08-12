CREATE TABLE Property (
  id           UUID PRIMARY KEY,
  version      INT NOT NULL,
  created_at   TIMESTAMP NOT NULL,
  modified_at  TIMESTAMP NOT NULL,

  name       varchar(255) NOT NULL,
  type       varchar(255) NOT NULL,

  address_country             varchar(255) NOT NULL,
  address_zip                 varchar(255) NOT NULL,
  address_city                varchar(255) NOT NULL,
  address_street              varchar(255) NOT NULL,
  address_number              varchar(255) NOT NULL,
  address_district            varchar(255) NOT NULL,
  address_neighborhood        varchar(255) NOT NULL
);


