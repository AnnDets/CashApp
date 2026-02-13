CREATE OR REPLACE FUNCTION fn_initialize_user_data()
    RETURNS TRIGGER AS $$
BEGIN
    -- 1. Создаем дефолтный аккаунт (Cash)
    -- Валюта BYN берется напрямую, так как она гарантированно есть в базе
    INSERT INTO accounts (user_id, name, type, currency_id, current_balance,
                          include_in_total_balance, default_account, savings_account, archive_account)
    VALUES (NEW.id, 'Наличные', 0, 'BYN', 0, TRUE, TRUE, FALSE, FALSE);

    -- 2. Заполняем 15 категорий
    INSERT INTO categories (name, author_id, icon_id, color_id, for_income, for_outcome, mandatory_outcome)
    SELECT d.name, NEW.id, d.icon_uuid, d.color_name, d.inc, d.out, d.mand
    FROM (VALUES
              ('Транспорт',    '550e8400-e29b-41d4-a716-446655440001'::uuid, 'Sand Yellow',      FALSE, TRUE,  FALSE),
              ('Алкоголь',     '550e8400-e29b-41d4-a716-446655440002'::uuid, 'Amber Orange',     FALSE, TRUE,  FALSE),
              ('Путешествия',  '550e8400-e29b-41d4-a716-446655440003'::uuid, 'Deep Blue',        FALSE, TRUE,  FALSE),
              ('Возврат',      '550e8400-e29b-41d4-a716-446655440004'::uuid, 'Emerald Green',    TRUE,  FALSE, FALSE),
              ('Семья',        '550e8400-e29b-41d4-a716-446655440025'::uuid, 'Rose Pink',        FALSE, TRUE,  TRUE),
              ('Документы',    '550e8400-e29b-41d4-a716-446655440006'::uuid, 'Slate Gray',       FALSE, TRUE,  TRUE),
              ('Жилье',        '550e8400-e29b-41d4-a716-446655440007'::uuid, 'Midnight Navy',     FALSE, TRUE,  TRUE),
              ('Питомцы',      '550e8400-e29b-41d4-a716-446655440008'::uuid, 'Warm Brown',       FALSE, TRUE,  FALSE),
              ('Доставка',     '550e8400-e29b-41d4-a716-446655440009'::uuid, 'Sky Blue',         FALSE, TRUE,  FALSE),
              ('Фастфуд',      '550e8400-e29b-41d4-a716-446655440010'::uuid, 'Amber Orange',     FALSE, TRUE,  FALSE),
              ('Интернет',     '550e8400-e29b-41d4-a716-446655440011'::uuid, 'Deep Blue',        FALSE, TRUE,  TRUE),
              ('Кино',         '550e8400-e29b-41d4-a716-446655440012'::uuid, 'Amethyst Purple',   FALSE, TRUE,  FALSE),
              ('Здоровье',     '550e8400-e29b-41d4-a716-446655440026'::uuid, 'Vivid Red',        FALSE, TRUE,  TRUE),
              ('Зарплата',     '550e8400-e29b-41d4-a716-446655440020'::uuid, 'Emerald Green',    TRUE,  FALSE, FALSE),
              ('Прочее',       '550e8400-e29b-41d4-a716-446655440015'::uuid, 'Graphite',         TRUE,  TRUE,  FALSE)
         ) AS d(name, icon_uuid, color_name, inc, out, mand);

    -- 3. Заполняем 30 мест (Places) для Беларуси
    INSERT INTO places (description, author_id)
    SELECT d.descr, NEW.id
    FROM (VALUES
              ('Евроопт'), ('Корона'), ('Санта'), ('Белмаркет'), ('Соседи'), ('Гиппо'),
              ('Яндекс Go'), ('Uber'), ('Минский Метрополитен'), ('Белоруснефть'), ('А-100'), ('Лукойл'),
              ('Мила'), ('Остров чистоты'), ('Wildberries'), ('Ozon'), ('21vek.by'), ('Е-доставка'),
              ('Планета Здоровья'), ('Остров здоровья'), ('Аптека Групп'),
              ('Burger King'), ('KFC'), ('Mak.by'), ('Додо Пицца'),
              ('МТС (Связь)'), ('А1 (Связь)'), ('Life:)'), ('Белтелеком'), ('Банкомат')
         ) AS d(descr);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Пересоздание триггера
DROP TRIGGER IF EXISTS tr_after_user_registration ON users;
CREATE TRIGGER tr_after_user_registration
    AFTER INSERT ON users
    FOR EACH ROW
EXECUTE FUNCTION fn_initialize_user_data();