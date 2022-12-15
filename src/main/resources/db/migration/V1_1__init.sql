DROP TABLE IF EXISTS post_user;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS location;

DROP SEQUENCE IF EXISTS location_id_seq;
DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS post_id_seq;
DROP SEQUENCE IF EXISTS image_id_seq;


CREATE TABLE location (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  latitude VARCHAR(20) NOT NULL,
  longitude VARCHAR(20) NOT NULL,
  zip_code VARCHAR(10),
  city VARCHAR(30),
  state VARCHAR(30),
  country VARCHAR(30),
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE users (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  email VARCHAR(50) NOT NULL,
  location_id BIGINT NOT NULL,
  profile_image_id BIGINT NOT NULL,
  twitter VARCHAR(50),
  facebook VARCHAR(50),
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT location_fk FOREIGN KEY (location_id) REFERENCES location(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE post (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  location_id BIGINT NOT NULL,
  category VARCHAR NOT NULL,
  condition VARCHAR NOT NULL,
  state VARCHAR NOT NULL,
  claimed_user_id BIGINT,
  swipe_left_count BIGINT DEFAULT 0,
  swipe_right_count BIGINT DEFAULT 0,
  view_count BIGINT DEFAULT 0,
  share_count BIGINT DEFAULT 0,
  message_count BIGINT DEFAULT 0,
  report_count BIGINT DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  renew_at TIMESTAMP NOT NULL DEFAULT now(),
  deleted_at TIMESTAMP,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT claimed_user_fk FOREIGN KEY (claimed_user_id) REFERENCES users(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT location_fk FOREIGN KEY (location_id) REFERENCES location(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE image (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  post_id BIGINT NOT NULL,
  url VARCHAR(50) NOT NULL,
  validated_at TIMESTAMP,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post(id)
    ON DELETE SET NULL
    ON UPDATE RESTRICT
);

CREATE TABLE post_user (
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  type VARCHAR(20) NOT NULL,
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX location_latitude_idx ON location (latitude);
CREATE INDEX location_longitude_idx ON location (longitude);
CREATE INDEX location_zip_code_idx ON location (zip_code);

CREATE INDEX user_first_name_idx ON users (first_name);
CREATE INDEX user_phone_number_idx ON users (phone_number);
CREATE INDEX user_email_idx ON users (email);

CREATE INDEX post_title_idx ON post (title);
CREATE INDEX post_state_idx ON post (state);

CREATE INDEX image_url_idx ON image (url);
CREATE UNIQUE INDEX post_user_uq ON post_user (post_id, user_id);

INSERT INTO location (latitude, longitude, zip_code, city, state, country)
VALUES ('39.639538','-112.502582','344000','Salt Lake City','Utah','USA');

INSERT INTO users (first_name, phone_number, email, location_id, profile_image_id)
VALUES ('Admin','+1-234-567-89-01','admin@email.com',1,1);
