CREATE TABLE IF NOT EXISTS decks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    author_id INT NOT NULL,
    hex_color VARCHAR(6) NOT NULL,
    visibility VARCHAR(10) NOT NULL,
    users_count INT DEFAULT 1,
    cards_count INT DEFAULT 0,
    likes_count INT DEFAULT 0,
    views_count INT DEFAULT 0,
    image_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP,

    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);