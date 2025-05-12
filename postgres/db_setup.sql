-- This script sets up a PostgreSQL database user and schema for data access.

-- Connect to the database as an admin user before running this script.

-- Drop the schema and user if they already exist to ensure a clean setup.
DROP SCHEMA IF EXISTS data01 CASCADE;
DROP USER IF EXISTS user01;

-- Create a new user with a secure password.
CREATE USER user01 WITH ENCRYPTED PASSWORD '123456';

-- Grant the user the ability to connect to the database.
GRANT CONNECT ON DATABASE postgres TO user01;

-- Create a new schema and assign ownership to the new user.
CREATE SCHEMA data01 AUTHORIZATION user01;

-- Allow the user to log in.
ALTER ROLE user01 LOGIN;

-- Grant the user read access to all tables in the public schema.
GRANT SELECT ON ALL TABLES IN SCHEMA public TO user01;

-- Grant monitoring and read-all-data privileges to the user.
GRANT pg_monitor TO user01;
GRANT pg_read_all_data TO user01;
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