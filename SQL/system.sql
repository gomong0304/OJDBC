create user minipro identified by minipro;
grant resource, connect to minipro;

alter user minipro default tablespace users;
alter user minipro temporary tablespace temp;