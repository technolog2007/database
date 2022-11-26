DROP TABLE  tb_result;
DROP TABLE  tb_categories;
DROP TABLE  tb_shops;
DROP TABLE  tb_products;

CREATE TABLE IF NOT EXISTS tb_shops
(
    shop_id       SERIAL UNIQUE NOT NULL PRIMARY KEY,
    shop_name     varchar(100),
    shop_city     varchar(20),
    shop_location varchar(200)
);


CREATE TABLE IF NOT EXISTS tb_categories
(
    category_id   SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_name varchar(50)
);

CREATE TABLE IF NOT EXISTS tb_products
(
    product_id    SERIAL UNIQUE NOT NULL PRIMARY KEY,
    category_id   int,
    product_name  varchar(50),
    product_price int
);

CREATE TABLE IF NOT EXISTS tb_result
(
    shop_id     int,
    products_id int,
    amount_id   float
);