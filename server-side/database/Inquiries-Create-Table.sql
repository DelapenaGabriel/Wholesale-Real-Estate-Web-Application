CREATE TABLE inquiries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150)NOT NULL,
    phone VARCHAR(20)NOT NULL,
    address VARCHAR(255)NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),

    -- property details
    property_type VARCHAR(50),
    bedrooms INT,
    bathrooms INT,
    sqft INT,
    condition VARCHAR(50),
    timeline VARCHAR(50),

    -- extra info
    additional_details TEXT,
    status VARCHAR(50) DEFAULT 'New',  -- e.g., 'Contacted', 'Pending', etc.
    role VARCHAR(20),  -- e.g., 'Seller' or 'Buyer'

    -- buyer interest field (optional)
    interest TEXT,  -- e.g., "Investment Opportunity - Downtown"

    -- timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);