package com.turlir.abakcalc.converter;

import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationConverter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Конвертер математическх варажений
 */
public class PolishConverter implements NotationConverter {

    private final Pattern mPattern;
    private final Queue<Item> mConverted;

    public PolishConverter() {
        mPattern = Pattern.compile("-?\\d+(.?)\\d*"); // только цифры
        mConverted = new LinkedList<>();
    }

    /**
     * Переводит инфиксную запись выражения в постфиксную
     * @param input математическое выражение
     * @return обратная польская нотация в прямом порядке
     * @exception RuntimeException в случае ошибки
     */
    @Override
    public Queue<Item> convert(String input) throws RuntimeException {
        Stack<Operator> operators = new Stack<>();
        mConverted.clear();

        String[] tokens = input.split(" ");
        boolean isLastOperator = true; // флаг для определеня унарного минуса

        for (String token : tokens) {
            boolean isNumber = mPattern.matcher(token).matches();

            if (isNumber) { // операнд
                number(token);
                isLastOperator = false;

            } else if (token.equals("(")) { // открыв. скобка
                operators.push(Operator.CS);
                isLastOperator = true;

            } else if(token.equals(")")) { // закрывающаяся скобка - окончательный расчет
                closedBracket(operators);
                isLastOperator = false;

            } else { // оператор
                Operator current = Operator.find(token);
                if (current != null) {
                    operator(operators, isLastOperator, current);
                    isLastOperator = true;
                } else {
                    throw new ArithmeticException("Неверное выражение");
                }
            }
        }

        if (operators.contains(Operator.CS)) {
            throw new RuntimeException("Неправильно расставлены скобки");
        }

        while (!operators.isEmpty()) { // дописываем остатки операторов
            Operator op = operators.pop();
            mConverted.add(op);
        }

        return mConverted;
    }

    private void number(String token) {
        try {
            double digit = Double.parseDouble(token);
            mConverted.add(new Operand(digit));
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Неверное выражение");
        }
    }

    private void closedBracket(Stack<Operator> operators) {
        Operator tmp = null;
        while (!operators.isEmpty()) {
            tmp = operators.pop();
            if (tmp != Operator.CS) {
                mConverted.add(tmp);
            } else {
                break;
            }
        }
        if (tmp == null || tmp != Operator.CS) { // last
            throw new RuntimeException("Неправильно расставлены скобки");
        }
    }

    private void operator(Stack<Operator> operators, boolean isLastOperator, Operator current) {
        for (int i = operators.size() - 1; i >= 0 ; i--) { // идем с конца стека
            Operator prev = operators.elementAt(i); // смотрим элемент
            if (prev == Operator.CS) { // 4 * ( 1 + 2 )
                break;
            }

            // для отличия минуса от унарного
            boolean p = isLastOperator && (current == Operator.REMOVE);
            // если приоритет prev в стеке выше приоритета current текущего
            // и текущий оператор минус или предыдущий токен - операнд
            if (!p &&  (prev.priority() > current.priority())) {
                operators.pop(); // компенсируем elementAt
                mConverted.add(prev);
            } else if (prev.priority() == current.priority()) {
                if (prev.associate()) {
                    operators.pop(); // аналогично
                    mConverted.add(prev);
                } else {
                    operators.pop();
                    mConverted.add(prev);
                }
            }
        }

        // унарный минус поддерживается на уровне Double#parseDouble(String)
        operators.push(current);
    }


}