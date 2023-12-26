
CREATE TABLE IF NOT EXISTS location_master (
    id  SERIAL,
    location_name VARCHAR(255),
	status VARCHAR(255) ,
	location_type VARCHAR(255) ,
	location_code_type VARCHAR(255),
	location_id VARCHAR(255),
	latitude VARCHAR(255),
    longitude VARCHAR(255),
    country json,
    primary key(location_id)
);

--CREATE TABLE IF NOT EXISTS record(
--    id  SERIAL,
--    record json default '{}',
--    PRIMARY KEY (id)
--);