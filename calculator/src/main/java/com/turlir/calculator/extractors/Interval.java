package com.turlir.calculator.extractors;


/**
 * Подстрока матетимаческого выражения, соответствующая или оператору или операнду (числу)
 */
public class Interval {

    public final String value;
    private final boolean type;

    Interval(boolean operand,String value) {
        this.value = value;
        this.type = operand;
    }

    /**
     * Выделение из строки {@param origin} начиная с позиции {@param index} следующего куска <br>
     * от оператора - до операнда и наоборот
     * @param origin математическое выражение
     * @param index начальная позиция
     */
    Interval(String origin, int index) {
        boolean l;
        char c = origin.charAt(index);
        boolean f = digit(c);
        if (!f && operator(c)) {
            type = false;
            value = origin.substring(index, index + 1);
            return;
        }
        int offset = 0;
        do {
            offset++;
            if (index + offset < origin.length()) {
                c = origin.charAt(index + offset);
                l = digit(c);
            } else {
                break;
            }
        } while (f == l);
        type = f;
        value = origin.substring(index, index + offset);
    }

    /**
     *
     * @return является ли этот участок выражения операндом
     */
    public boolean operand() {
        return type;
    }

    private static boolean digit(char c) {
        return Character.isDigit(c) || c == '.' /*|| c == ','*/;
    }

    private static boolean operator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        if (type != interval.type) return false;
        return value.equals(interval.value);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 139 * result + (type ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Interval{" +
               "value='" + value + '\'' +
               ", type=" + type +
               '}';
    }
}
