CREATE DATABASE product ENCODING 'UTF8';

CREATE TABLE type(
	"id" serial NOT NULL PRIMARY KEY,
	"name" VARCHAR(50)
);
set lc_monetary to 'C';

CREATE TABLE product (
	"id" serial NOT NULL PRIMARY KEY,
	"name" VARCHAR ( 40 ) NOT NULL,
	"type_id" INTEGER references type(id),
	"expired_date" TIMESTAMP,
	"price" NUMERIC ( 6, 4 )
);


INSERT INTO type(name) VALUES
	('Десерт'),
	('Сыр'),
	('Молоко'),
	('Яйца');

INSERT INTO product ( "name", "type_id", "expired_date", "price" )
VALUES
	( 'Мороженое Коровка', 1, '2020-06-25 10:23:54', 10.52 ),
	( 'Сыр плавленный Дружба', 2, '2020-07-15 10:23:54', 99.99 ),
	( 'Сыр плавленный Янтарь', 2, '2020-07-15 10:23:54', 80.10 ),
	( 'Молоко', 3, '2020-06-20 10:23:54', 21 ),
	( 'Яйца', 4, '2020-06-25 10:23:54', 17.50 ),
	( 'Мороженое геркулес ', 1, '2020-07-19 10:23:54', 17.50 ),
	( 'Творог', 3, '2020-06-20 10:23:54', 21 ),
	( 'кефир', 3, '2020-06-19 10:23:54', 21 );




-- 1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT "name", expired_date, price::money FROM product WHERE type_id = (SELECT id FROM "type" WHERE "name" = 'Сыр');
		-- или
SELECT p.name, p.expired_date, price::money FROM product p JOIN type t ON t."name" = 'Сыр' AND p.type_id = t."id";

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT "name", expired_date, price::money FROM product WHERE "name" LIKE '%Мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
		-- "-1" от лени, чтобы не смотреть какое послднее число месяца
SELECT "name", expired_date, price::money FROM product WHERE expired_date BETWEEN '2020-07-01' AND date '2020-08-01' - INTEGER '1';

-- 4. Написать запрос, который выводит самый дорогой продукт.
SELECT  "name", expired_date, price::money FROM product WHERE price  = (SELECT max(price) FROM product);

--	5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT  (SELECT name FROM type WHERE id = p.type_id) AS type ,count(p.type_id)
		FROM product p JOIN type t ON p.type_id = t."id" GROUP BY p.type_id;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT t."name" AS type, p."name", p.expired_date, price::money
		FROM product p JOIN type t ON  p.type_id = t."id" AND t.name IN ( 'Сыр','Молоко');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
		-- поставил меньше 3-х, чтобы не раздувать таблицу
SELECT  (SELECT name FROM type WHERE id = type_id) AS type, count(p.type_id)
		FROM product p JOIN type t ON p.type_id = t."id" GROUP BY p.type_id HAVING count(p.type_id) < 3 ;

--	8. Вывести все продукты и их тип.
  	SELECT p."id", p."name", t."name" AS type, p.expired_date, p.price::money FROM product p JOIN type t ON p.type_id = t."id";




