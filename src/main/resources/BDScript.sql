CREATE DATABASE epicenter
WITH ENCODING ='UTF8'
OWNER=postgres
CONNECTION LIMIT 15;

CREATE TABLE IF NOT EXISTS shop(
    shop_id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    shop_name varchar(100),
    shop_location varchar(200)
);

CREATE TABLE IF NOT EXISTS products(
    products_id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    products_category varchar(100),
    products_name varchar(100),
    products_price int
)