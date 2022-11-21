-- CREATE DATABASE epicenter
--     WITH ENCODING = 'UTF8'
--     OWNER = postgres
--     CONNECTION LIMIT 15;
DROP TABLE  schema_epicenter.tb_shop;
DROP TABLE  schema_epicenter.tb_products;
DROP TABLE  schema_epicenter.tb_category;
DROP TABLE  schema_epicenter.tb_result;

DROP SCHEMA schema_epicenter;

CREATE SCHEMA schema_epicenter;

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_shop
(
    shop_id       SERIAL UNIQUE NOT NULL PRIMARY KEY,
    shop_name     varchar(100),
    shop_city     varchar(20),
    shop_location varchar(200)
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_category
(
    category_id   SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_name varchar(100)
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_products
(
    products_id    SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_id int,
    products_name  varchar(100),
    products_price int
);

CREATE TABLE IF NOT EXISTS schema_epicenter.tb_result
(
    result_it int
);