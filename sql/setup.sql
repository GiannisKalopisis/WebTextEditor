CREATE USER texteditormanager WITH PASSWORD 'root';

CREATE DATABASE texteditordb WITH OWNER=texteditormanager;

ALTER ROLE texteditormanager WITH SUPERUSER;