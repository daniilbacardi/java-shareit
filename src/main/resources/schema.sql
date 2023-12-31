DROP TABLE IF EXISTS users, items, bookings, requests, comments;

CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    email        varchar(255) NOT NULL,
    name         varchar(512) NOT NULL,
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description  varchar(1000) NOT NULL,
    requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS items
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar(512) NOT NULL,
    description  varchar(1000) NOT NULL,
    is_available boolean NOT NULL,
    owner_id     BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    request_id   BIGINT REFERENCES requests (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS comments
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text         varchar(2000) NOT NULL,
    item_id      BIGINT REFERENCES items (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    author_id    BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS bookings
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_date   TIMESTAMP WITHOUT TIME ZONE,
    end_date     TIMESTAMP WITHOUT TIME ZONE,
    item_id      BIGINT REFERENCES items (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    booker_id    BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    status       varchar(50) NOT NULL
);