CREATE TABLE IF NOT EXISTS owner (
      id  INTEGER NOT NULL AUTO_INCREMENT,
      name VARCHAR(128) NOT NULL,
      age INTEGER NOT NULL,
      PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS pet (
     id  INTEGER NOT NULL AUTO_INCREMENT,
     name VARCHAR(128) NOT NULL,
     description VARCHAR(256) NOT NULL,
     PRIMARY KEY (id),
     owner_id int,
     FOREIGN KEY (owner_id) REFERENCES owner(id)
);