TRUNCATE product RESTART IDENTITY ;


INSERT INTO product (brand, sku, name, description, short_description , image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES
    ('VictoriasSecret',
     '123455655',
     'Logo Cotton Hiphugger Panty',
     'Posey Lace Waist Cotton Hiphugger Panty',
     'Posey Lace Waist Cotton Hiphugger Panty',
     'https://www.victoriassecret.com/p/760x1013/tif/zz/23/05/25/23/1121920765H8_OM_B.jpg',
     true,
     100,
     320,
     1,
     CURRENT_TIMESTAMP),


    ('VictoriasSecret',
     '123455655',
     'Lace-Trim Cheeky Panty',
     'Posey Lace Waist Cotton Hiphugger Panty',
     'Posey Lace Waist Cotton Hiphugger Panty',
     'https://www.victoriassecret.com/p/760x1013/tif/zz/23/06/15/02/1122691854A2_OM_F.jpg',
     true,
     100,
     310,
     1,
     CURRENT_TIMESTAMP),

    ('VictoriasSecret',
     '123455655',
     'Fine Fragrance Mist',
     'Float in a cloud of your favorite fragrance.',
     'Float in a cloud of your favorite fragrance. ',
     'https://www.victoriassecret.com/p/760x1013/tif/79/79/797934d8b40d41f59db310c40bbaf973/111673905170_OF_F.jpg',
     true,
     100,
     450,
     2,
     CURRENT_TIMESTAMP),

    ('VictoriasSecret',
     '123455655',
     'Limited Edition Splash Fragrance Mist',
     'Your favorite scents make a splash',
     'Your favorite scents make a splash',
     'https://www.victoriassecret.com/p/760x1013/tif/63/97/639747b1013a462dab4d483a86439ba3/112164724518_OM_F.jpg',
     true,
     100,
     390,
     2,
     CURRENT_TIMESTAMP),


  ('VictoriasSecret',
    '123455655',
    'Fragrance Lotion',
    'Celebrate the collection that celebrates you',
    'Your favorite scents make a splash with',
    'https://www.victoriassecret.com/p/760x1013/tif/a9/a7/a9a744c553234ac7b35c7632db34e987/112047181340_OF_F.jpg',
    true,
    100,
    220,
    2,
    CURRENT_TIMESTAMP),
--
--
('VictoriasSecret',
    '123455655',
    'The Victoria Medium Shoulder Bag',
    'Structured and chic, this style wears two ways',
    'Structured and chic, this style wears two ways.',
    'https://www.victoriassecret.com/p/760x1013/tif/08/30/0830fd2c53a8462d9c31d05468776ac6/11175324001C_OM_F.jpg',
    true,
    100,
    880,
    3,
    CURRENT_TIMESTAMP),


('VictoriasSecret',
    '123455655',
    'The Victoria Mini Shoulder Bag',
    'Our best-selling shoulder bag, in',
    'Our best-selling shoulder bag, in ',
    'https://www.victoriassecret.com/p/760x1013/tif/zz/23/05/09/01/11175327054R_OM_F.jpg',
    true,
    100,
    550,
    3,
    CURRENT_TIMESTAMP),

    ('VictoriasSecret',
     '123455655',
     'Express Train Case',
     'Travel to see all the beauty in life',
     'Travel to see all the beauty in life ',
     'https://www.victoriassecret.com/p/760x1013/tif/47/55/4755658a388148c09a19aff10cf825b4/11197860696P_OM_F.jpg',
     true,
     100,
     550,
     3,
     CURRENT_TIMESTAMP),

('BathAndBody',
    '87879789798',
    'At The Beach',
    'Ultimate Hydration Body Cream',
    'Travel to see all the beauty in life ',
    'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/dw/image/v2/BBDL_PRD/on/demandware.static/-/Sites-master-catalog/default/dw3a5afdff/hires/026681681.jpg?sh=471&yocs=o_s_',
    true,
    100,
    340,
    4,
    CURRENT_TIMESTAMP),

    ('BathAndBody',
     '87879789798',
     'At The Beach',
     'Ultimate Hydration Body Cream',
     'Travel to see all the beauty in life ',
     'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/dw/image/v2/BBDL_PRD/on/demandware.static/-/Sites-master-catalog/default/dw408ab577/hires/026661539.jpg?sh=471&yocs=o_s_',
     true,
     100,
     340,
     4,
     CURRENT_TIMESTAMP),

('BathAndBody',
    '87879789798',
    'At The Beach',
    'Ultimate Hydration Body Cream',
    'Travel to see all the beauty in life ',
    'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/dw/image/v2/BBDL_PRD/on/demandware.static/-/Sites-master-catalog/default/dwc0796147/crop/026661310_crop.jpg?sw=500&sh=600&sm=fit&q=75&yocs=o_s_',
    true,
    100,
    340,
    5,
    CURRENT_TIMESTAMP),

    ('BathAndBody',
     '87879789798',
     'Wild Sand',
     'Ultimate Hydration Body Cream',
     'Travel to see all the beauty in life ',
     'https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/dw/image/v2/BBDL_PRD/on/demandware.static/-/Sites-master-catalog/default/dw456a79ec/hires/026674491.jpg?sh=471&yocs=o_s_',
     true,
     100,
     340,
     5,
     CURRENT_TIMESTAMP)

















