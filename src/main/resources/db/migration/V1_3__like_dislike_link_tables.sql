CREATE TABLE post_like (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX post_like_uq ON post_like (post_id, user_id);

CREATE TABLE post_dislike (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT post_fk FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX post_dislike_uq ON post_dislike (post_id, user_id);
