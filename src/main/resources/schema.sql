CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    quantity INT NOT NULL CHECK (quantity >= 0),
    min_quantity INT NOT NULL DEFAULT 0,
    category_id INT NOT NULL REFERENCES categories(id)
);
