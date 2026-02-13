-- 1. Заполняем справочник категорий
INSERT INTO categories (id, name, icon_id, color_id, for_income, for_outcome, mandatory_outcome, is_default)
SELECT gen_random_uuid(), d.name, d.icon_uuid, c.name, d.inc, d.out, d.mand, TRUE
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
     ) AS d(name, icon_uuid, color_name, inc, out, mand)
         JOIN colors c ON c.name = d.color_name;

-- 2. Заполняем справочник мест
INSERT INTO places (id, description, is_default)
SELECT gen_random_uuid(), d.descr, TRUE
FROM (VALUES
          ('Евроопт'), ('Корона'), ('Санта'), ('Белмаркет'), ('Соседи'), ('Гиппо'),
          ('Яндекс Go'), ('Минский Метрополитен'), ('Белоруснефть'), ('А-100'), ('Лукойл'),
          ('Мила'), ('Остров чистоты'), ('Wildberries'), ('Ozon'), ('21vek.by'), ('Е-доставка'),
          ('Планета Здоровья'), ('Остров здоровья'), ('Аптека Групп'),
          ('Burger King'), ('KFC'), ('Mak.by'), ('Додо Пицца'),
          ('МТС'), ('А1'), ('Life:)'), ('Белтелеком'), ('Булочки')
     ) AS d(descr);