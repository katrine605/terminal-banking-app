DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user"(
	id integer primary key autoincrement,
	username text(30),
	password text(30)
);

INSERT INTO "user"(username,password) VALUES('admin',12345),('John Smith',12345);

CREATE TABLE account(
	id integer primary key autoincrement,
	accountName text(30),
	balance double DEFAULT(0),
	accountHolderId integer NOT NULL REFERENCES "user"(id)
);

INSERT INTO account(accountName,accountHolderId) Values("checking",1),("checking",2);
INSERT INTO account(accountName,balance,accountHolderId) Values("savings",1050.60,1),("savings",1050.60,2);

SELECT * FROM account a JOIN "user" u ON a.id = u.id;