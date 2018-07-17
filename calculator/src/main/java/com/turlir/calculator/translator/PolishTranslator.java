package com.turlir.calculator.translator;

import com.turlir.calculator.converter.Member;
import com.turlir.calculator.member.Operator;
import com.turlir.calculator.member.Operators;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class PolishTranslator implements NotationTranslator {

    @Override
    public Queue<Member> translate(Iterator<Member> parent) {
        Deque<Operator> operators = new ArrayDeque<>();
        Queue<Member> converted = new LinkedList<>();

        while (parent.hasNext()) {
            Member now = parent.next();
            boolean isNumber = now.operand();
            if (isNumber) {
                converted.add(now);

            } else if (now == Operators.OS) {
                operators.addLast(Operators.CS);

            } else if (now == Operators.CS) {
                closedBracket(operators.descendingIterator(), converted);

            } else {
                Operator p = (Operator) now;
                operator(p, operators.descendingIterator(), converted);
                operators.addLast(p);
            }
        }

        while (!operators.isEmpty()) { // дописываем остатки операторов
            Member op = operators.pollLast();
            if (op == Operators.CS)
                throw new RuntimeException("Неправильно расставлены скобки");
            converted.add(op);
        }

        return converted;
    }

    private void operator(Operator current, Iterator<Operator> iter, Queue<Member> converted) {
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

    private void closedBracket(Iterator<Operator> iter, Queue<Member> converted) {
        while (iter.hasNext()) {
            Member tmp = iter.next();
            iter.remove(); // poolLast() analog
            if (tmp != Operators.CS) {
                converted.add(tmp);
            } else {
                return;
            }
        }
        // не нашлось ни одной закрывающейся скобки
        throw new RuntimeException("Неправильно расставлены скобки");
    }

}
