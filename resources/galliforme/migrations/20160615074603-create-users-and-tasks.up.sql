create sequence users_id_seq
  start with 1
  increment by 1
  no minvalue
  no maxvalue
  cache 1;

create table users (
  id integer default nextval('users_id_seq') primary key,
  created_at timestamp with time zone,
  updated_at timestamp with time zone,
  first_name character varying(255) not null,
  last_name character varying(255) not null,
  email character varying(255) not null unique,
  -- scrypt output is theorically 82 char max
  password character varying(82) not null,
  avatar_url character varying(255)
);

-- dummy user
-- FIXME: put this into a db populate task

insert into users (
  first_name,
  last_name,
  email,
  password,
  created_at,
  updated_at
) values (
  'George',
  'Abidbol',
  'ga@gall.com',
  -- (scrypt/encrypt "password")
  '$s0$f0801$uG7cMnuz2ozmtAyxv0B/7g==$9bqpO74sogx0x2wfM0oAd913Twv5UKTQZMKZ9si0JI4=',
  (select now()),
  (select now())
);
