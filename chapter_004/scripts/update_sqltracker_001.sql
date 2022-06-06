CREATE TABLE type
(
    "id"   serial NOT NULL PRIMARY KEY,
    "name" VARCHAR(50)
);
set lc_monetary to 'C';
CREATE TABLE product
(
    "id"           serial      NOT NULL PRIMARY KEY,
    "name"         VARCHAR(40) NOT NULL,
    "type_id"      INTEGER references type (id),
    "expired_date" TIMESTAMP,
    "price"        NUMERIC(6, 4)
);