package com.turlir.abakcalc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Калькулятор по принципу обратной польской нотации
 * не выводит непосредственно строку в нотации, вычисляет "на ходу"
 */
class NotationConverter {

    private final Pattern mPattern;

    NotationConverter() {
        mPattern = Pattern.compile("\\d+(.?)\\d*"); // только цифры
    }

    /**
     * Получить ответ по строке в инфиксной форме
     * @param input операторы и операнды
     * @return ответ
     * @exception RuntimeException в случае ошибки
     */
    Queue<Item> calc(String input) throws RuntimeException {
        Stack<Operator> operators = new Stack<>();
        Queue<Item> result = new LinkedList<>();

        String[] tokens = input.split(" ");
        boolean isLastOperator = true; // flag for detect unar minus

        for (String token : tokens) {
            boolean isNumber = mPattern.matcher(token).matches();
            if (isNumber) { // операнд
                double digit;
                try {
                    digit = Double.parseDouble(token);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("Неверное выражение");
                }
                //numbers.push(digit);
                result.add(new Operand(digit));
                isLastOperator = false;

            } else if (token.equals("(")) { // открыв. скобка
                operators.push(Operator.CS);
                isLastOperator = true;

            } else if(token.equals(")")) { // закрывающаяся скобка - окончательный расчет
                Operator tmp = null;
                while (!operators.isEmpty()) {
                    tmp = operators.pop();
                    if (tmp != Operator.CS) {
                        result.add(tmp);
                    } else {
                        break;
                    }
                }
                if (tmp == null || tmp != Operator.CS) { // last
                    throw new RuntimeException("Неправильно расставлены скобки");
                }
                isLastOperator = false;

            } else { // оператор
                Operator current = Operator.find(token);
                if (current != null) {
                    Operator prev;
                    for (int i = operators.size() - 1; i >= 0 ; i--) { // идем с конца стека
                        prev = operators.elementAt(i); // смотрим элемент
                        if (prev == Operator.CS) { // 4 * ( 1 + 2 )
                            break;
                        }

                        // для отличия минуса от унарного
                        boolean p = isLastOperator && (current == Operator.REMOVE);
                        // если приоритет prev в стеке выше приоритета current текущего
                        // и текущий оператор минус или предыдущий токен - операнд
                        if (!p &&  (prev.priority() > current.priority())) {
                            operators.pop(); // компенсируем elementAt
                            //operate(numbers, prev, result);
                            result.add(prev);
                        } else if (prev.priority() == current.priority()) {
                            if (prev.associate()) {
                                operators.pop(); // аналогично
                                //operate(numbers, prev, result);
                                result.add(prev);
                            } else {
                                operators.pop();
                                result.add(prev);
                            }
                        }
                    }

                    if (current == Operator.REMOVE && isLastOperator) { // унарный минус
                        //operators.push(Operator.MULTIPLY); //
                        //numbers.push(-1.0); // TODO
                        //result.add(current);
                    } else { // обычный оператор
                        operators.push(current);
                        isLastOperator = true;
                    }

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
            result.add(op);
        }

        return result;
    }

}