# WebTextEditor
This is a Web-based text editor with auto-save capabilities. As a user edits text in the text editor, that text is automatically saved every five times user types in. Typing also contain characters like Backspace, Delete, or Ctrl-V. Text is automatically saved in a PostgreSQL database. If the web page is reloaded or the service is restarted, the text is recovered so that the user can continue editing. Also, files can be opened only in one tab.  If the file is open, it can not be opened by another user, neither the same browser nor any other browser. With this, we avoid simultaneous processing and achieve file consistency.

## Implementation/Capabilities of user
The user has the following capabilities:

- view all the files of the web text editor (that exist in database)
- open a file and edit it
- autosave of changes after 5 times he/she edits the file
- informed if file is saved, if something went wrong with saving, or how many times he/she has to type to autosave the file.
- create a new file
- delete a file when after he/she opens it
- notify him/her if something went wrong with the creation, the deletion or the saving process of a file 


## Assumptions
Some assumptions of this project are:

- A file can be opened only in one tab. An error message occurs if someone tries to open it in another tab or browser.
- After 5 times the user edits the file, it is automatically saved.


## Database
To store files we use PostgreSQL relational database. 

### Installation
To install it follow the instructions:
```
sudo apt update
sudo apt install postgresql postgresql-contrib

```

### Set-up database
To set up the database you have to follow the instructions of file *sql/setup.sql*. Create a user, then create a database with owner the user you just created and then alter the role of user to *superuser*, to have the appropriate permissions.
```
CREATE USER texteditormanager WITH PASSWORD 'root';
CREATE DATABASE texteditordb WITH OWNER=texteditormanager;
ALTER ROLE texteditormanager WITH SUPERUSER;
```

After that you have to create a table into the database to store the files. You can also find the instructions in the file *sql/createTables.sql*.
```
CREATE TABLE files (
    file_id serial PRIMARY KEY,
    title varchar(200) NOT NULL,
    text TEXT NOT NULL
);
```

If you want to insert rows into the database (from the terminal) you have to type:
```
INSERT INTO files (title,text)
VALUES ('new_file_name','Your new text...');
```

After the setup of the database you have to run the code of file *models/CodeGen.scala* to autogenerate the data model of database to Scala code, by using Slick library. To run it open a sbt shell and run the command:
```
runMain models.CodeGen
```
It will create a new model named Tables.scala with autogenerated code.


## Frameworks and Tools
The following frameworks and tools were used for the implementation of the project:

### Scala
Scala 2.13 to build backend of the assignment.

### Sbt
Sbt 1.7.0 version to build Scala project.

### Play
Play framework 2.8.18 version (running Java 11.0.17). Play Framework is an open-source web application framework which follows the model–view–controller architectural pattern. It is written in Scala and usable from other programming languages that are compiled to JVM bytecode.

### Slick
Slick 5.1.0 version. Slick is a database access library for Scala.

### Intellij IDEA
IntelliJ IDEA was used for the development of the project.


## License

MIT
