package com.turlir.calculator.converter

/**
 * Возможность представления токенов через интерфейс [Printer]
 */
interface Visual {

    /**
     * Посетитель для представления текущего токена
     * @param chain интерфейс вывода на текущем шаге
     */
    fun print(chain: Printer)

}
