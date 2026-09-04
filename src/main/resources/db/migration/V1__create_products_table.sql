CREATE TABLE products (
                          id UUID PRIMARY KEY,
                          sku VARCHAR(100) NOT NULL UNIQUE,
                          name VARCHAR(200) NOT NULL,
                          description TEXT,
                          price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
                          currency VARCHAR(3) NOT NULL DEFAULT 'INR',
                          status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
                          created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_status ON products(status);