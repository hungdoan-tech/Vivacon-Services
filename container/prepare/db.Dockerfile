FROM postgres:14

ENV POSTGRES_DB=vivacon

ENV POSTGRES_USER=postgres

ENV POSTGRES_PASSWORD=Admin123*

ENV PGDATA=/var/lib/postgresql/data

ENV TZ='UTC'

ENV PGTZ='UTC'

COPY ./sql/*.sql /docker-entrypoint-initdb.d/

