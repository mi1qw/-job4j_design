package ru.job4j.sqltracker;

/**
 * The type Table.
 * Описывает существующую SQL таблицу
 * Содержит вспомогательную информацию
 * для работы с конкретной таблицей в базе SQL.
 * Таблица SQL имеет реальное имя, назначенное ей в базе.
 * Правильно оформить команду SQL с нужным колличеством и типом пргументов,
 * для добавления/изменения строк в нужной нам таблице
 */
class Table {
    /**
     * The Name имя таблицы в SQL.
     */
    private String name;
    /**
     * The Column значимые имена столбцов таблицы.
     * Пример "name", "type_id", "expired_date", "price"
     */
    private String column;
    /**
     * The St ссылка на метод, интерфейса {@link Statements}.
     * подготавливает PreparedStatement значения в соответствии
     * с типом данных всех столбцов конкретной таблицы
     */
    private Statements st;
    /**
     * The Args аргументы подстановки в команду SQL.
     * в виденужного кол-ва знаков "?, ..."
     */
    private String args;

    Table(final String name, final String column, final Statements st) {
        this.name = name;
        this.column = column;
        this.st = st;
        this.args = "?".concat(", ?".repeat(column.split(",").length - 1));
    }

    public String getColumn() {
        return column;
    }

    public String getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

    public Statements getSt() {
        return st;
    }
}
