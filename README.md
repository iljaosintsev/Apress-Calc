# Apress-Calculator

Приложение представлет собой простейший калькулятор и работает на версиях Android 2.2 и выше

# Логика работы

В текстовое поле вводится выражение
> 1+2*5 - 5/22 + (45 +34)*-3

Калькулятор вычисляет значение этого выражения.
> -226.227

Калькулятор понимает следующие операции + - * / () "унарный минус" и работает с десятичными цифрами.
Для вычисления выражения используется обратная польская нотация. Сконвертировнная строка не выводится, вычисления производятся на ходу.
Unit-тестами покрыта логика вычисления результата.

![Демонстрация](/art/main-screen.jpg)