DROP TABLE IF EXISTS tb_result;
DROP TABLE IF EXISTS tb_products;
DROP TABLE IF EXISTS tb_shops;
DROP TABLE IF EXISTS tb_categories;

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
    product_price int,
    FOREIGN KEY (category_id) REFERENCES tb_categories(category_id)
);

CREATE TABLE IF NOT EXISTS tb_result
(
    shop_id     int,
    products_id int,
    amount_id   float,
   CONSTRAINT fk_products FOREIGN KEY (products_id) REFERENCES tb_products(product_id),
   CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES tb_shops(shop_id)
);