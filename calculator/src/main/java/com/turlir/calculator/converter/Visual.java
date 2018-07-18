package com.turlir.calculator.converter;

/**
 * Возможность представления токенов через интерфейс {@link Printer}
 */
public interface Visual {

    /**
     * Посетитель для представления текущего токена
     * @param chain интерфейс вывода на текущем шаге
     */
    void print(Printer chain);

}
