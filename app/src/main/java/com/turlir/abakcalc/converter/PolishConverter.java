package com.turlir.abakcalc.converter;

import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationConverter;
import com.turlir.abakcalc.converter.abs.Operator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Конвертер математическх варажений
 */
public class PolishConverter implements NotationConverter {

    private final Pattern mPattern;

    public PolishConverter() {
        mPattern = Pattern.compile("-?\\d+(.?)\\d*"); // только цифры
    }

    /**
     * Переводит инфиксную запись выражения в постфиксную
     * @param input математическое выражение
     * @return обратная польская нотация в прямом порядке
     * @exception RuntimeException в случае ошибки
     */
    @Override
    public Queue<Item> convert(String input) throws RuntimeException {
        Deque<Operator> operators = new ArrayDeque<>();
        Queue<Item> converted = new LinkedList<>();

        String[] tokens = input.split(" ");

        for (String token : tokens) {
            boolean isNumber = mPattern.matcher(token).matches();

            if (isNumber) { // операнд
                Operand operand = number(token);
                converted.add(operand);

            } else if (token.equals("(")) { // открыв. скобка
                operators.addLast(Operators.CS);

            } else if(token.equals(")")) { // закрывающаяся скобка - окончательный расчет
                closedBracket(operators.descendingIterator(), converted);

            } else { // оператор
                Operator current = Operators.find(token);
                if (current != null) {
                    operator(current, operators.descendingIterator(), converted);
                    operators.addLast(current);
                } else {
                    throw new ArithmeticException("Неверное выражение");
                }
            }
        }

        if (operators.contains(Operators.CS)) {
            throw new RuntimeException("Неправильно расставлены скобки");
        }

        while (!operators.isEmpty()) { // дописываем остатки операторов
            Operator op = operators.pollLast();
            converted.add(op);
        }

        return converted;
    }

    private void operator(Operator current, Iterator<Operator> iter, Queue<Item> converted) {
        while (iter.hasNext()) {
            Operator prev = iter.next();
            if (prev == Operators.CS) { // 4 * ( 1 + 2 )
                break;
            } else if (current.compareTo(prev) < 1) {
                // для всех операторов из стека, приоритет которых больше текущего оператора
                iter.remove();
                converted.add(prev);
            }
        }
    }

    private Operand number(String token) {
        try {
            double digit = Double.parseDouble(token);
            return new Operand(digit);
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Неверное выражение");
        }
    }

    private void closedBracket(Iterator<Operator> iter, Queue<Item> converted) {
        while (iter.hasNext()) {
            Operator tmp = iter.next();
            iter.remove(); // poolLast() analog
            if (tmp != Operators.CS) {
                converted.add(tmp);
            } else {
                break;
            }
        }
    }

}