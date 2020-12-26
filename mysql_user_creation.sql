create user 'newyahoo'@'localhost';
alter user 'newyahoo'@'localhost' identified by '123456';
--set password for 'newyahoo'@'localhost' = PASSWORD('123456');
grant all on newyahoo.* to 'newyahoo'@'localhost';
flush privileges;