-- диаграмма классов системы заявок
CREATE DATABASE request ENCODING 'UTF8';

CREATE ROLE admin NOSUPERUSER CREATEROLE BYPASSRLS;
GRANT ALL PRIVILEGES ON DATABASE request TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO admin;
GRANT admin TO zed;



\c request



SET ROLE admin;
CREATE TABLE role (
	"id" serial NOT NULL PRIMARY KEY,
	"roles" VARCHAR (20)
);
CREATE TABLE users (
	"login" VARCHAR (20) NOT NULL PRIMARY KEY,
	"password" VARCHAR (20) NOT NULL,
	"create_date" TIMESTAMP,
	"role_id" INTEGER REFERENCES role(ID)
);
CREATE TABLE rules (
	"id" serial NOT NULL PRIMARY KEY,
	"rules" VARCHAR(20) NOT NULL
);
CREATE TABLE role_rules (
	"id" serial NOT NULL PRIMARY KEY,
	"role_id" INTEGER REFERENCES "role"(ID),
	"rules_id" INTEGER REFERENCES "rules"(ID)
);
CREATE TABLE "state" (
	"id" serial NOT NULL primary key,
	"state" bool
);
CREATE TABLE "category" (
	"id" serial NOT NULL primary key,
	"category" text
);
CREATE TABLE "items" (
	"id" serial NOT NULL primary key,
	"description" text,
	"create_date" TIMESTAMP DEFAULT now(),
	"category_id" integer references category(id),
	"state_id" integer references state(id),
	"login_id" VARCHAR references users(login)
);
CREATE TABLE "comments" (
	"id" serial NOT NULL primary key,
	"description" text,
	"item_id" integer references items(id)
);
CREATE TABLE "attachs" (
	"id" serial NOT NULL primary key,
	"paths" text,
	"item_id" integer references items(id)
);


CREATE USER ann WITH PASSWORD '123';
CREATE USER boris WITH PASSWORD '123';
CREATE USER zed WITH PASSWORD '123';
CREATE ROLE visitor WITH NOCREATEDB NOCREATEROLE;
GRANT CONNECT ON DATABASE request TO visitor;

GRANT SELECT ON TABLE
	PUBLIC.attachs,
	PUBLIC.category,
	PUBLIC."comments",
	PUBLIC.items,
	PUBLIC.state
	TO visitor;

GRANT UPDATE, DELETE, INSERT ON TABLE
	PUBLIC.attachs,
	PUBLIC."comments",
	PUBLIC.items
	TO visitor;

GRANT visitor TO ann, boris;

ALTER TABLE items ENABLE ROW LEVEL SECURITY;
CREATE POLICY visitor_select ON items FOR SELECT USING ( TRUE );
CREATE POLICY visitor_update ON items FOR UPDATE USING ( login_id = CURRENT_USER );
CREATE POLICY visitor_insert ON items FOR INSERT WITH CHECK ( login_id = CURRENT_USER );
CREATE POLICY visitor_delete ON items FOR DELETE USING ( login_id = CURRENT_USER );

ALTER TABLE attachs ENABLE ROW LEVEL SECURITY;
CREATE POLICY visitor_select ON attachs FOR SELECT USING ( TRUE );
CREATE POLICY visitor_update ON attachs FOR UPDATE USING (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );
CREATE POLICY visitor_insert ON attachs FOR INSERT WITH CHECK (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );
CREATE POLICY visitor_delete ON attachs FOR DELETE USING (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );

ALTER TABLE comments ENABLE ROW LEVEL SECURITY;
CREATE POLICY visitor_select ON comments FOR SELECT USING ( TRUE );
CREATE POLICY visitor_update ON comments FOR UPDATE USING (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );
CREATE POLICY visitor_insert ON comments FOR INSERT WITH CHECK (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );
CREATE POLICY visitor_delete ON comments FOR DELETE USING (
	CURRENT_USER = ( SELECT login_id FROM items WHERE ID = item_id ) );


INSERT INTO rules(rules) VALUES('SELECT'), ('INSERT'), ('UPDATE'), ('DELETE');
INSERT INTO role (id, roles )
VALUES
	( 1, 'admin' ),
	( 2, 'visitor' );

INSERT INTO role_rules (id, role_id, rules_id)
VALUES
	( 1, 1, 1 ),
	( 2, 1, 2 ),
	( 3, 1, 3 ),
	( 4, 1, 4 ),
	( 5, 2, 1 ),
	( 6, 2, 2 ),
	( 7, 2, 3 );

INSERT INTO users ( "login", "password", create_date, role_id )
VALUES
	( 'zed', '123', CURRENT_TIMESTAMP, 1 ),
	( 'ann', '123', CURRENT_TIMESTAMP, 2 ),
	( 'boris', '123', CURRENT_TIMESTAMP, 2 );

INSERT INTO "state" ( "state" )
VALUES
	( TRUE ),
	( FALSE );

INSERT INTO category ( category )
VALUES
	( 'problem' ),
	( 'error' ),
	( 'warning' );

INSERT INTO items ( ID, description, create_date, category_id, state_id, login_id )
VALUES
	( 1, 'some problems', CURRENT_TIMESTAMP, 1, 2, 'ann' ),
	( 2, 'some error', CURRENT_TIMESTAMP, 2, 2, 'boris' );

INSERT INTO comments ( description, item_id )
VALUES
	( 'solve the problem ', 1 ),
	( 'many errors ', 2 );

INSERT INTO attachs ( paths, item_id )
VALUES
	( '/home/problems', 1 ),
	( '/home/error', 2 );




