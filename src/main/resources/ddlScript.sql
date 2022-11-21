-- CREATE DATABASE epicenter
--     WITH ENCODING = 'UTF8'
--     OWNER = postgres
--     CONNECTION LIMIT 15;
DROP TABLE  schema_epicenter.tb_shops;
DROP TABLE  schema_epicenter.tb_products;
DROP TABLE  schema_epicenter.tb_categories;
DROP TABLE  schema_epicenter.tb_result;

DROP SCHEMA schema_epicenter;

CREATE SCHEMA schema_epicenter;

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_shops
(
    shop_id       SERIAL UNIQUE NOT NULL PRIMARY KEY,
    shop_name     varchar(100),
    shop_city     varchar(20),
    shop_location varchar(200)
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_categories
(
    category_id   SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_name varchar(100)
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_products
(
    product_id    SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_id int,
    product_name  varchar(100),
    product_price int
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_result
(
    shop_id int,
    products_id int,
    amount_it float/*,
    sum_price double precision*/
);