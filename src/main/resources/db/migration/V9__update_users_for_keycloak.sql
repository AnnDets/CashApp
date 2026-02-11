-- Remove password column (auth is now handled by Keycloak)
ALTER TABLE users DROP COLUMN IF EXISTS password;

-- Remove the auto-generation default on id so we can set it explicitly from Keycloak sub
-- (PostgreSQL UUID columns don't need special changes for explicit assignment, 
--  but we ensure no default gen_random_uuid() is set)
ALTER TABLE users
    ALTER COLUMN id DROP DEFAULT;
