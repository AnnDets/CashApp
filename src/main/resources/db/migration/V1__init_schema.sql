CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE accounts
(
    id           UUID NOT NULL DEFAULT uuid_generate_v4(),
    user_id                  UUID         NOT NULL,
    name                     VARCHAR(255) NOT NULL,
    type                     SMALLINT     NOT NULL,
    currency_id              VARCHAR(3)   NOT NULL,
    credit_limit             DECIMAL,
    current_balance          DECIMAL      NOT NULL,
    include_in_total_balance BOOLEAN      NOT NULL,
    default_account          BOOLEAN      NOT NULL,
    bank_id                  UUID,
    card_number1 VARCHAR(100),
    card_number2 VARCHAR(100),
    card_number3 VARCHAR(100),
    card_number4 VARCHAR(100),
    savings_account          BOOLEAN      NOT NULL,
    archive_account          BOOLEAN      NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE banks
(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    country      VARCHAR(30)    NOT NULL,
    display_name VARCHAR(30)    NOT NULL,
    icon         VARCHAR(10000) NOT NULL,
    CONSTRAINT pk_banks PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id                UUID        NOT NULL DEFAULT uuid_generate_v4(),
    name              VARCHAR(30) NOT NULL,
    author_id         UUID        NOT NULL,
    icon_id           UUID        NOT NULL,
    color_id          UUID        NOT NULL,
    for_income        BOOLEAN     NOT NULL,
    for_outcome       BOOLEAN     NOT NULL,
    mandatory_outcome BOOLEAN     NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE colors
(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    red   SMALLINT NOT NULL,
    green SMALLINT NOT NULL,
    blue  SMALLINT NOT NULL,
    CONSTRAINT pk_colors PRIMARY KEY (id)
);

CREATE TABLE currencies
(
    id           VARCHAR(3)  NOT NULL,
    display_name VARCHAR(30) NOT NULL,
    symbol VARCHAR(2),
    CONSTRAINT pk_currencies PRIMARY KEY (id)
);

CREATE TABLE icons
(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    data VARCHAR(5000) NOT NULL,
    CONSTRAINT pk_icons PRIMARY KEY (id)
);

CREATE TABLE operations
(
    id             UUID     NOT NULL DEFAULT uuid_generate_v4(),
    external_system_operation_id VARCHAR(255)                NOT NULL,
    category_id                  UUID,
    account_outcome_id           UUID,
    account_income_id            UUID,
    operation_type SMALLINT NOT NULL,
    operation_date               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description                  VARCHAR(255),
    place_id                     UUID,
    income                       DECIMAL,
    outcome                      DECIMAL,
    deleted                      BOOLEAN                     NOT NULL,
    is_processed                 BOOLEAN                     NOT NULL,
    created                      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated                      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_operations PRIMARY KEY (id)
);

CREATE TABLE places
(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    description VARCHAR(255) NOT NULL,
    author_id   UUID         NOT NULL,
    CONSTRAINT pk_places PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       UUID         NOT NULL DEFAULT uuid_generate_v4(),
    email    VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_BANK FOREIGN KEY (bank_id) REFERENCES banks (id);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_CURRENCY FOREIGN KEY (currency_id) REFERENCES currencies (id);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_ICON FOREIGN KEY (icon_id) REFERENCES icons (id);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_COLOR FOREIGN KEY (color_id) REFERENCES colors (id);

ALTER TABLE operations
    ADD CONSTRAINT FK_OPERATIONS_ON_ACCOUNT_INCOME FOREIGN KEY (account_income_id) REFERENCES accounts (id);

ALTER TABLE operations
    ADD CONSTRAINT FK_OPERATIONS_ON_ACCOUNT_OUTCOME FOREIGN KEY (account_outcome_id) REFERENCES accounts (id);

ALTER TABLE operations
    ADD CONSTRAINT FK_OPERATIONS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE operations
    ADD CONSTRAINT FK_OPERATIONS_ON_PLACE FOREIGN KEY (place_id) REFERENCES places (id);

ALTER TABLE places
    ADD CONSTRAINT FK_PLACES_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);