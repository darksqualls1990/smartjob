CREATE TABLE tbl_user (
    id UUID NOT NULL,
    created TIMESTAMP(6) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    last_login TIMESTAMP(6),
    modified TIMESTAMP(6),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tbl_phone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL
);


CREATE TABLE tbl_user_phones (
    user_id UUID NOT NULL,
    phone_id INT NOT NULL,
    PRIMARY KEY (user_id, phone_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (phone_id) REFERENCES tbl_phone(id) ON DELETE CASCADE
);