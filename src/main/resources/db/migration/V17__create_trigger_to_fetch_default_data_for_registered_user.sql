CREATE OR REPLACE FUNCTION fn_initialize_user_data()
    RETURNS TRIGGER AS $$
BEGIN
    -- 1. Дефолтный счет
    INSERT INTO accounts (user_id, name, type, currency_id, current_balance,
                          include_in_total_balance, default_account, savings_account, archive_account)
    VALUES (NEW.id, 'Наличные', 0, 'BYN', 0, TRUE, TRUE, FALSE, FALSE);

    -- 2. Привязываем только те категории, где is_default = TRUE
    INSERT INTO user_categories (user_id, category_id)
    SELECT NEW.id, id FROM categories WHERE is_default = TRUE;

    -- 3. Привязываем только те места, где is_default = TRUE
    INSERT INTO user_places (user_id, place_id)
    SELECT NEW.id, id FROM places WHERE is_default = TRUE;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Пересоздание триггера
DROP TRIGGER IF EXISTS tr_after_user_registration ON users;
CREATE TRIGGER tr_after_user_registration
    AFTER INSERT ON users
    FOR EACH ROW
EXECUTE FUNCTION fn_initialize_user_data();