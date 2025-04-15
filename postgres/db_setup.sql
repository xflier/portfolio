# login with admin to create a regular user01 for data write and read


drop schema if exists data01;
revoke all on database postgres from user01;
drop user if exists user01;

create user user01 with encrypted password '123456';
grant connect on database postgres to user01;
create schema data01 authorization user01;
alter role user01 login;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO user01;

grant pg_monitor to user01;
grant pg_read_all_data to user01;
GRANT user01 TO pg_read_all_data;