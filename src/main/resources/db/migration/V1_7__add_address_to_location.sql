ALTER TABLE location ADD COLUMN address VARCHAR(200) NOT NULL DEFAULT '<EMPTY>';
ALTER TABLE location ALTER COLUMN address DROP DEFAULT;

CREATE INDEX location_address_idx ON location (address);
