TRUNCATE product_category RESTART IDENTITY CASCADE ;


INSERT INTO product_category(brand, category_name, description, image_url)
VALUES ('VictoriasSecret',
        'PANTIES',
        'Discover a wide range of women’s underwear at Victoria’s Secret.',
        'assets/demo-images/vs-groups/p1.jpg'),

       ('VictoriasSecret',
        'Beauty',
        'Discover a wide range of women’s underwear at Victoria’s Secret.',
        'assets/demo-images/vs-groups/b1.jpg'),


       ('VictoriasSecret',
        'Bags & Accessories',
        'Discover a wide range of women’s underwear at Victoria’s Secret.',
        'assets/demo-images/vs-groups/all1.jpg'),

       ('BathAndBody',
        'Shop Men''s',
        'Now open: The Men’s Shop for all things head-to-toe you.',
        'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/on/demandware.static/-/Sites-BathAndBodyWorks-Library/default/dw6157884b/images/Summer2023/xcat_mensshop_su1_hps.jpg?yocs=o_s_'),

       ('BathAndBody',
        'Shop Wellness',
        'Center your best you with a little something new.',
        'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/on/demandware.static/-/Sites-BathAndBodyWorks-Library/default/dw5b7b79c7/images/Spring2023/xcat_wellness_sp3_hps.jpg?yocs=o_s_')


;