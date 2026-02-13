-- 1. Обновляем таблицу категорий, чтобы она ссылалась на имена вместо UUID
-- Сначала меняем тип колонки в categories (нужно временно снять FK)
ALTER TABLE categories
    DROP CONSTRAINT FK_CATEGORIES_ON_COLOR;
ALTER TABLE categories
    ALTER COLUMN color_id TYPE VARCHAR(50) USING NULL;

-- 2. Добавляем колонку для новых ID
ALTER TABLE colors
    ADD COLUMN name VARCHAR(50);
ALTER TABLE colors
    DROP CONSTRAINT pk_colors;
ALTER TABLE colors
    DROP COLUMN id;

-- 3. Удаляем старые цвета.
TRUNCATE TABLE colors CASCADE;

-- 4. Заполняем английскими названиями
INSERT INTO colors (name, red, green, blue)
VALUES
-- Основные системные цвета
('Emerald Green', 46, 204, 113),   -- Продукты, Доходы
('Deep Blue', 41, 128, 185),       -- Счета, Переводы
('Vivid Red', 231, 76, 60),        -- Штрафы, Долги
('Amber Orange', 243, 156, 18),    -- Рестораны, Еда вне дома
('Amethyst Purple', 155, 89, 182), -- Развлечения, Подписки

-- Мягкие/Пастельные для бытовых нужд
('Soft Mint', 162, 217, 206),      -- ЖКХ, Хозтовары
('Sky Blue', 133, 193, 233),       -- Связь, Интернет
('Rose Pink', 241, 148, 131),      -- Красота, Уход
('Light Coral', 241, 148, 131),    -- Здоровье, Аптеки
('Sand Yellow', 247, 220, 111),    -- Транспорт, Такси

-- Темные и нейтральные
('Midnight Navy', 44, 62, 80),     -- Инвестиции, Накопления
('Slate Gray', 127, 140, 141),     -- Прочее
('Warm Brown', 160, 64, 0),        -- Ремонт, Дом
('Turquoise', 26, 188, 156),       -- Подарки, Благотворительность
('Graphite', 52, 73, 94);          -- Техника, Гаджеты

-- 5. Возвращаем связь в таблицу категорий
ALTER TABLE colors
    ADD CONSTRAINT pk_colors PRIMARY KEY (name);
ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_COLOR
        FOREIGN KEY (color_id) REFERENCES colors (name) ON UPDATE CASCADE;
