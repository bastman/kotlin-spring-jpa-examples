-- create example db.
CREATE ROLE example WITH LOGIN PASSWORD 'example';

CREATE DATABASE example OWNER example;
CREATE DATABASE example_test OWNER example;