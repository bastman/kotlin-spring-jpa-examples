CREATE TABLE Broker (
  id         UUID PRIMARY KEY,
  version    INT       NOT NULL,
  created_at TIMESTAMP NOT NULL,
  modified_at TIMESTAMP NOT NULL,

  comment      varchar(4096) NOT NULL,
  company_name varchar(255) NOT NULL,
  first_name   varchar(255) NOT NULL,
  last_name    varchar(255) NOT NULL,
  email        varchar(255) NOT NULL,
  phone_number varchar(255) NOT NULL,

  address_country   varchar(255) NOT NULL,
  address_state     varchar(255) NOT NULL,
  address_city      varchar(255) NOT NULL,
  address_street    varchar(255) NOT NULL,
  address_number    varchar(255) NOT NULL

);

