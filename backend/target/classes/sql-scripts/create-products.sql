INSERT INTO product_category(category_name) VALUES ('BOOKS');

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES ('JavaScript - The Fun Parts', 'Learn JavaScript',
        'assets/images/products/placeholder.png', true, 100, 19.99, 1, CURRENT_TIMESTAMP);

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES ('Spring Framework Tutorial', 'Learn Spring',
        'assets/images/products/placeholder.png', true, 100, 29.99, 1, CURRENT_TIMESTAMP);

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES ('Kubernetes - Deploying Containers', 'Learn Kubernetes',
        'assets/images/products/placeholder.png', true, 100, 24.99, 1, CURRENT_TIMESTAMP);

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES ('Internet of Things (IoT) - Getting Started', 'Learn IoT',
        'assets/images/products/placeholder.png', true, 100, 29.99, 1, CURRENT_TIMESTAMP);

INSERT INTO product (name, description, image_url, active, units_in_stock,
                     unit_price, category_id, date_created)
VALUES ('The Go Programming Language: A to Z', 'Learn Go',
        'assets/images/products/placeholder.png', true, 100, 24.99, 1, CURRENT_TIMESTAMP);
