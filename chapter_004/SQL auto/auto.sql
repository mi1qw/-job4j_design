CREATE DATABASE auto ENCODING 'UTF8';

CREATE TABLE body (
	"id" serial NOT NULL PRIMARY KEY,
	"vin" VARCHAR (30) NOT NULL
);

CREATE TABLE engine (
	"id" serial NOT NULL PRIMARY KEY,
	"engine" VARCHAR(40) NOT NULL
);
CREATE TABLE transmission (
	"id" serial NOT NULL PRIMARY KEY,
	"transmission" VARCHAR ( 20 )
);
CREATE TABLE car (
	"id" serial NOT NULL PRIMARY KEY,
	"model" VARCHAR(30),
	"body_id" INT REFERENCES body(id),
	"engine_id"  INT REFERENCES engine(id),
	"transmission_id" INT REFERENCES transmission(id)
);

INSERT INTO body(vin) VALUES
	('ZFA22300005556111'),
	('ZFA22300005556222'),
	('00022300005556888'),
	('00022300005556999'),
	('ZFA22300005556333');

INSERT INTO engine("engine") VALUES
	('карбюратор 1.6л. 21060-100026001'),
	('карбюратор 1.5л. 21083-100026001'),
	('карбюратор 2.0л. 21111-100026111'),
	('инжектор 2.0л. 21222-10002622'),
	('инжектор 1.7л. 21214-100026032');

INSERT INTO transmission("transmission") VALUES
	('КПП 143E2-50001'),
	('КПП 213E2-21301'),
	('КПП 215E2-21501'),
	('КПП 173E2-54201'),
	('КПП 333E2-54233');

INSERT INTO car("model", body_id, engine_id, transmission_id) VALUES
	('ВАЗ-2108',5,1,2 ),
	('ВАЗ-2106',2,2,4 ),
	('ВАЗ-2109',1,5,3 );

-- Применяемые узлы на авто
SELECT c."id", model, vin, engine, transmission FROM car c
	JOIN body  b ON b."id"= c."body_id"
	JOIN engine e ON e."id"= c."engine_id"
	JOIN transmission t ON t."id"= c."transmission_id";

-- следующие КПП не используются
SELECT t."id", t.transmission FROM transmission t LEFT JOIN car c ON t."id"= c."transmission_id" WHERE c."transmission_id" IS NULL;
		-- или
SELECT * FROM transmission WHERE id IN (
	SELECT  id FROM transmission t
	EXCEPT
	SELECT  transmission_id FROM car c) ORDER BY id;

-- следующие двигателя не используются
SELECT e."id", e.engine FROM engine e LEFT JOIN car c ON e."id"= c."engine_id" WHERE c."engine_id" IS NULL;

-- следующие кузов не использовался
SELECT b."id", b.vin FROM body b LEFT JOIN car c ON b."id"= c."body_id" WHERE c."body_id" IS NULL;