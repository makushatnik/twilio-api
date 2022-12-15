DROP MATERIALIZED VIEW IF EXISTS pick_view;

ALTER TABLE users DROP COLUMN location_id;
ALTER TABLE post ADD COLUMN thread_count BIGINT DEFAULT 0;
ALTER TABLE post_like ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT now();

CREATE MATERIALIZED VIEW pick_view AS
  SELECT pl.id,
         pl.post_id,
         pl.user_id who_liked_id,
         ul.first_name who_liked_name,
         pl.created_at like_created_at,
         p.title,
         p.state,
         p.user_id owner_id,
         uo.first_name owner_name,
         p.thread_count thread_count,
         p.swipe_right_count swipe_right_count,
         p.claimed_user_id received_user_id,
         t.id thread_id,
         t.created_at thread_created_at,
         t.seller_id,
         t.buyer_id
  FROM post_like pl
    LEFT JOIN post p ON p.id = pl.post_id AND p.deleted_at IS NULL
    LEFT JOIN thread t ON pl.post_id = t.post_id
    LEFT JOIN users ul ON ul.id = pl.user_id
    LEFT JOIN users uo ON uo.id = p.user_id
  WHERE pl.rate = 'LIKE';

CREATE INDEX pick_view_id_idx ON public.pick_view USING btree (id);
