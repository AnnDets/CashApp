CREATE TABLE user_categories
(
    category_id UUID NOT NULL,
    user_id     UUID NOT NULL,
    CONSTRAINT pk_user_categories PRIMARY KEY (category_id, user_id)
);

CREATE TABLE user_places
(
    place_id UUID NOT NULL,
    user_id  UUID NOT NULL,
    CONSTRAINT pk_user_places PRIMARY KEY (place_id, user_id)
);

ALTER TABLE user_categories
    ADD CONSTRAINT fk_usecat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE user_categories
    ADD CONSTRAINT fk_usecat_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_places
    ADD CONSTRAINT fk_usepla_on_place FOREIGN KEY (place_id) REFERENCES places (id);

ALTER TABLE user_places
    ADD CONSTRAINT fk_usepla_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE categories
    DROP COLUMN author_id CASCADE;

ALTER TABLE places
    DROP COLUMN author_id CASCADE;

ALTER TABLE categories ADD COLUMN is_default BOOLEAN DEFAULT FALSE;
ALTER TABLE places ADD COLUMN is_default BOOLEAN DEFAULT FALSE;