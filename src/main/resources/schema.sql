DROP SCHEMA IF EXISTS public cascade;
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS items
(
    item_id     BIGINT NOT NULL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR,
    img_path    VARCHAR(255),
    price       BIGINT NOT NULL,
    count       INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id BIGINT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id BIGSERIAL PRIMARY KEY NOT NULL,
    order_id BIGINT NOT NULL,
    item_id  BIGINT NOT NULL,
    amount   INTEGER,
    CONSTRAINT orderitem_unq UNIQUE (order_id, item_id)
);

CREATE SEQUENCE orders_seq INCREMENT 50 START 50;
CREATE SEQUENCE items_seq INCREMENT 50 START 50;

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (item_id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES "orders" (order_id);