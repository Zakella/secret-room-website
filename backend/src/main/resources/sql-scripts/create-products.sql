DELETE from product;

INSERT INTO product_category(category_name) VALUES ('Panties');

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES
    ('Lace Waist Cotton Hiphugger Panty', 'Super-soft stretch cotton meets cool comfort in a full-coverage hiphugger perfect for wearing everywhere, everyday. Complete with a delicate lace waistband',
        'assets/products-images-vs/IMG_1957.jpg', true, 100, 320, 1, CURRENT_TIMESTAMP),

    ('Lace Waist Cotton Hiphugger Panty', 'Super-soft stretch cotton meets cool comfort in a full-coverage hiphugger perfect for wearing everywhere, everyday. Complete with a delicate lace waistband. ',
     'assets/products-images-vs/IMG_1957.jpg', true, 100, 290, 1, CURRENT_TIMESTAMP),


    ('Ribbed Cotton Bikini Panty', 'Our everyday bikini comes in a cotton-modal blend that''s softer than ever, in a collectible mix of colors and prints. ',
     'assets/products-images-vs/IMG_1957.jpg', true, 100, 280, 1, CURRENT_TIMESTAMP);




INSERT INTO product_category(category_name) VALUES ('Beauty');

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created, short_description)
VALUES
    ('Bombshell Isle Eau de Parfum', 'Super-soft stretch cotton meets cool comfort in a full-coverage hiphugger perfect for wearing everywhere, everyday. Complete with a delicate lace waistband',
     'assets/products-images-vs/IMG_2404.jpg', true, 100, 450, 2, CURRENT_TIMESTAMP, 'Ocean Air, Pearl Peony and Island Coconut'),

    ('Fine Fragrance Mist', 'Super-soft stretch cotton meets cool comfort in a full-coverage hiphugger perfect for wearing everywhere, everyday. Complete with a delicate lace waistband. ',
     'assets/products-images-vs/IMG_2404.jpg', true, 100, 250, 2, CURRENT_TIMESTAMP, 'Lush Cherry, Red Peony and Sultry Vanilla'),


    ('Ribbed Cotton Bikini Panty', 'Our everyday bikini comes in a cotton-modal blend that''s softer than ever, in a collectible mix of colors and prints. ',
     'assets/products-images-vs/IMG_2404.jpg', true, 100, 580, 2, CURRENT_TIMESTAMP, 'Juniper, Apricot Blush and Boyfriend Tee')



