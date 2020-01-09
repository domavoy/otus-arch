create database finance;
create user finance;
alter user finance with encrypted password 'finance';
grant all privileges on database finance to finance;