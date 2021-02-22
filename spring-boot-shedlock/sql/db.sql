DROP TABLE IF EXISTS shedlock;

CREATE TABLE shedlock (
  name varchar(64) NOT NULL,
  lock_until timestamp(3) NULL DEFAULT NULL,
  locked_at timestamp(3) NULL DEFAULT NULL,
  locked_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (name)
);