package com.turlir.abakcalc;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Калькулятор по принципу обратной польской нотации
 * не выводит непосредственно строку в нотации, вычисляет "на ходу"
 */
class Calc {

    private final Pattern mPattern;

    Calc() {
        mPattern = Pattern.compile("[0-9]+"); // only digit
    }

    /**
     * Получить ответ по строке в инфиксной форме
     * @param input операторы и операнды
     * @return ответ
     * @exception RuntimeException в случае ошибки
     */
    Double calc(String input) throws RuntimeException {
        Stack<Operator> operators = new Stack<>();
        Stack<Double> numbers = new Stack<>();

        String[] tokens = input.split(" ");
        boolean isLastOperator = true; // flag for detect unar minus

        boolean haveAssociativeOperation = false; // flag for detect associative op

        for (String token : tokens) {
            boolean isNumber = mPattern.matcher(token).find();
            if (isNumber) { // операнд
                numbers.push(Double.parseDouble(token));
                isLastOperator = false;

            } else if (token.equals("(")) { // открыв. скобка
                operators.push(Operator.CS);
                isLastOperator = true;

            } else if(token.equals(")")) { // (2 + 3) расчет скобки
                Operator tmp = null;
                while (!operators.isEmpty()) {
                    tmp = operators.pop();
                    if (tmp != Operator.CS) {
                        operateFirst(tmp, numbers);

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
                            operate(numbers, prev);
                            haveAssociativeOperation = true;
                        } else if (prev.priority() == current.priority()) {
                            if (prev.associate()) {
                                operators.pop(); // аналогично
                                operate(numbers, prev);
                                haveAssociativeOperation = true;
                            }
                        }
                    }

                    if (current == Operator.REMOVE && isLastOperator) { // унарный минус
                        operators.push(Operator.MULTIPLY);
                        numbers.push(-1.0);
                    } else { // обычный оператор
                        operators.push(current);
                        isLastOperator = true;
                    }
                }
            }
        }

        if (operators.contains(Operator.CS)) {
            throw new RuntimeException("Неправильно расставлены скобки");
        }

        // если одинаковое количество операндов и операторов
        // значит какого-то операнда не хватает
        if (numbers.size() == operators.size()) {
            if (haveAssociativeOperation) {
                if (operators.size() >= 2) {
                    operators.pop();
                } else {
                    return numbers.pop();
                }
            } else {
                if (operators.size() >= 2) {
                    operators.pop();
                } else {
                    throw new ArithmeticException("Неверное выражение");
                }
            }
        }

        while (!operators.isEmpty()) { // окончательное вычисление выражения
            Operator current = operators.pop();
            operateFirst(current, numbers);
        }

        return numbers.pop();
    }

    private void operateFirst(Operator op, Stack<Double> numbers) {
        Double result;
        Double first = numbers.pop();
        Double last = numbers.pop();
        result = op.apply(last, first); // считаем приоритетную операцию
        numbers.push(result);
    }

    private void operate(Stack<Double> numbers, Operator tmp) {
        Double result;
        Double first = numbers.pop();
        Double last = numbers.pop();
        result = tmp.apply(last, first); // считаем приоритетную операцию
        numbers.push(result);
    }


    private enum Operator {

        /*EX("^") {
            @Override
            public int priority() {
                return 4;
            }
        },*/

        MULTIPLY("*") {
            @Override
            public int priority() {
                return 3;
            }

            @Override
            public Double apply(Double left, Double right) {
                return left * right;
            }
        },

        DIVIDE("/") {
            @Override
            public int priority() {
                return 3;
            }

            @Override
            public Double apply(Double left, Double right) {
                if (right == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return left / right;
            }
        },

        ADD("+") {
            @Override
            public int priority() {
                return 2;
            }

            @Override
            public Double apply(Double left, Double right) {
                return left + right;
            }
        },

        REMOVE("-") {
            @Override
            public int priority() {
                return 2;
            }

            @Override
            public Double apply(Double left, Double right) {
                return left - right;
            }

            @Override
            public boolean associate() {
                return true;
            }
        },

        OS("(") {
            @Override
            public int priority() {
                return 1;
            }

            @Override
            public Double apply(Double left, Double right) {
                return null;
            }
        },

        CS(")") {
            @Override
            public int priority() {
                return 1;
            }

            @Override
            public Double apply(Double left, Double right) {
                return null;
            }
        };

        private final String mOperator;

        private static final Map<String, Operator> map;
        static {
            map = new HashMap<>();
            for (Operator sign : Operator.values()) {
                map.put(sign.mOperator, sign);
            }
        }

        public static Operator find(String sign) {
            return map.get(sign);
        }


        Operator(String op) {
            mOperator = op;
        }

        public abstract int priority();

        public abstract Double apply(Double left, Double right);

        public boolean associate() {
            return false;
        }

    }

}