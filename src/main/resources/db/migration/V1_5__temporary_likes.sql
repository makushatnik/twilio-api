DROP TABLE IF EXISTS post_like;
DROP TABLE IF EXISTS post_dislike;
DROP SEQUENCE IF EXISTS post_like_id_seq;
DROP SEQUENCE IF EXISTS post_dislike_id_seq;

CREATE TABLE temp_like (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  drive VARCHAR(50) NOT NULL,
  post_id BIGINT NOT NULL,
  rate VARCHAR(10) NOT NULL DEFAULT 'LIKE',
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX temp_like_uq ON temp_like (post_id, drive);

CREATE TABLE post_like (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  rate VARCHAR(10) NOT NULL DEFAULT 'LIKE',
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX post_like_uq ON post_like (post_id, user_id);
