UPDATE users set phone_number = '+12345678901' WHERE id = 1;

CREATE TABLE thread (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  buyer_id  BIGINT NOT NULL,
  seller_id BIGINT NOT NULL,
  post_id   BIGINT NOT NULL,
  sid VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT buyer_id_fk FOREIGN KEY (buyer_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT seller_id_fk FOREIGN KEY (seller_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT post_id_fk FOREIGN KEY (post_id) REFERENCES post(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX thread_users_uq ON thread (buyer_id, seller_id, post_id);

CREATE UNIQUE INDEX users_phone_number_uq ON users (phone_number);
