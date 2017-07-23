#db.local.up:
#	docker-compose -f setup/docker-compose.local.yml up -d
#db.local.down:
#	docker-compose -f setup/docker-compose.local.yml down

db.local.create:
	psql -p 5432 -h 127.0.0.1 -U postgres -f setup/db_create.sql
db.local.drop:
	psql -p 5432 -h 127.0.0.1 -U postgres -f setup/db_drop.sql


