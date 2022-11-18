CREATE TABLE files (
    file_id serial PRIMARY KEY,
    title varchar(200) NOT NULL,
    text TEXT NOT NULL
);

INSERT INTO files (title,text)
VALUES ('python','dummy text for python file');

