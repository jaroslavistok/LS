CREATE TABLE in_medicament_category
(
  medicament_id INTEGER NOT NULL,
  medicament_category_id INTEGER NOT NULL,
  CONSTRAINT in_medicament_category_pkey PRIMARY KEY (medicament_id, medicament_category_id),
  CONSTRAINT fk_in_medicament_category_medicament_id FOREIGN KEY (medicament_id) REFERENCES medicaments (medicament_id),
  CONSTRAINT fk_in_medicament_category_medicament_category_id FOREIGN KEY (medicament_category_id) REFERENCES medicaments_categories (medicament_category_id)
);
CREATE TABLE medicament_information
(
  medicament_information_id INTEGER PRIMARY KEY NOT NULL,
  added DATE,
  expiration DATE,
  sold DATE
);
CREATE TABLE medicaments
(
  medicament_id INTEGER PRIMARY KEY NOT NULL,
  batch VARCHAR(255),
  code VARCHAR(255),
  title VARCHAR(255),
  sale_category_id INTEGER,
  medicament_information_id INTEGER,
  price_id INTEGER,
  state_id INTEGER,
  CONSTRAINT fk_medicaments_sale_category_id FOREIGN KEY (sale_category_id) REFERENCES sale_categories (sale_category_id),
  CONSTRAINT fk_medicaments_medicament_information_id FOREIGN KEY (medicament_information_id) REFERENCES medicament_information (medicament_information_id),
  CONSTRAINT fk_medicaments_price_id FOREIGN KEY (price_id) REFERENCES prices (price_id),
  CONSTRAINT fk_medicaments_state_id FOREIGN KEY (state_id) REFERENCES states (state_id)
);
CREATE TABLE medicaments_categories
(
  medicament_category_id INTEGER PRIMARY KEY NOT NULL,
  additional_information VARCHAR(255),
  title VARCHAR(255)
);
CREATE TABLE prices
(
  price_id INTEGER PRIMARY KEY NOT NULL,
  buyout_price NUMERIC(38),
  discount DOUBLE PRECISION,
  dph DOUBLE PRECISION,
  insurance_company NUMERIC(38),
  patient NUMERIC(38),
  pharmacy_profit DOUBLE PRECISION,
  purchase_price NUMERIC(38),
  selling_price NUMERIC(38)
);
CREATE TABLE recipes
(
  recipe_id INTEGER PRIMARY KEY NOT NULL,
  cash_register_number INTEGER,
  date DATE,
  number INTEGER,
  type VARCHAR(255),
  recipe_batch_id INTEGER,
  medicament_id INTEGER,
  CONSTRAINT fk_recipes_recipe_batch_id FOREIGN KEY (recipe_batch_id) REFERENCES recipes_batches (recipe_batch_id),
  CONSTRAINT fk_recipes_medicament_id FOREIGN KEY (medicament_id) REFERENCES medicaments (medicament_id)
);
CREATE TABLE recipes_batches
(
  recipe_batch_id INTEGER PRIMARY KEY NOT NULL,
  abbreviation VARCHAR(255),
  number INTEGER,
  title VARCHAR(255)
);
CREATE TABLE sale_categories
(
  sale_category_id INTEGER PRIMARY KEY NOT NULL,
  additional_information VARCHAR(255),
  title VARCHAR(255)
);
CREATE TABLE sale_categories_medicaments
(
  salecategory_sale_category_id INTEGER NOT NULL,
  medicaments_medicament_id INTEGER NOT NULL,
  CONSTRAINT sale_categories_medicaments_pkey PRIMARY KEY (salecategory_sale_category_id, medicaments_medicament_id),
  CONSTRAINT fk_sale_categories_medicaments_salecategory_sale_category_id FOREIGN KEY (salecategory_sale_category_id) REFERENCES sale_categories (sale_category_id),
  CONSTRAINT fk_sale_categories_medicaments_medicaments_medicament_id FOREIGN KEY (medicaments_medicament_id) REFERENCES medicaments (medicament_id)
);
CREATE TABLE sequence
(
  seq_name VARCHAR(50) PRIMARY KEY NOT NULL,
  seq_count NUMERIC(38)
);
CREATE TABLE states
(
  state_id INTEGER PRIMARY KEY NOT NULL,
  title VARCHAR(255)
);
