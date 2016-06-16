-- scr/galliforme/endpoint/users.sql
-- The users

-- :name all-users :? :*
-- :doc Get all users
select * from users;

-- :name user-by-email :? :1
-- :doc retrieve user given its email
select * from users where email = :email;

-- :name register-user :<!
-- :doc insert a user and returns its id
insert into users (first_name, last_name, email, password)
values (:first-name, :last-name, :email, :password)
returning id;
