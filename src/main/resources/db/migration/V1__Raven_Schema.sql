-- Raven schema

CREATE TABLE users
(
	id INTEGER GENERATED BY DEFAULT AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	public_key_pem VARCHAR(4096) NOT NULL,
	private_key_pem VARCHAR(4096) NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uk_users_name UNIQUE (name);

CREATE TABLE notes
(
	id INTEGER GENERATED BY DEFAULT AS IDENTITY,
	content VARCHAR(255) NOT NULL,
	user_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE notes ADD CONSTRAINT fk_notes_users FOREIGN KEY (user_id) REFERENCES users;
