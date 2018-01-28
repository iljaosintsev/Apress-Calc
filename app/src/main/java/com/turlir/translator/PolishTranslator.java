package com.turlir.translator;

import com.turlir.converter.Member;
import com.turlir.converter.Part;
import com.turlir.converter.Parts;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class PolishTranslator implements NotationTranslator {

    @Override
    public Queue<Member> translate(Queue<Member> parent) {
        Deque<Part> operators = new ArrayDeque<>();
        Queue<Member> converted = new LinkedList<>();

        for (Member now : parent) {
            boolean isNumber = now.operand();
            if (isNumber) {
                converted.add(now);

            } else if (now == Parts.OS) {
                operators.addLast(Parts.CS);

            } else if (now == Parts.CS) {
                closedBracket(operators.descendingIterator(), converted);

            } else {
                Part p = (Part) now;
                operator(p, operators.descendingIterator(), converted);
                operators.addLast(p);
            }
        }

        while (!operators.isEmpty()) { // дописываем остатки операторов
            Member op = operators.pollLast();
            if (op == Parts.CS)
                throw new RuntimeException("Неправильно расставлены скобки");
            converted.add(op);
        }

        return converted;
    }

    private void operator(Part current, Iterator<Part> iter, Queue<Member> converted) {
        while (iter.hasNext()) {
            Part prev = iter.next();
            if (prev == Parts.CS) { // 4 * ( 1 + 2 )
                break;
            } else if (current.compareTo(prev) < 1) {
                // для всех операторов из стека, приоритет которых больше текущего оператора
                iter.remove();
                converted.add(prev);
            }
        }
    }

    private void closedBracket(Iterator<Part> iter, Queue<Member> converted) {
        while (iter.hasNext()) {
            Member tmp = iter.next();
            iter.remove(); // poolLast() analog
            if (tmp != Parts.CS) {
                converted.add(tmp);
            } else {
                return;
            }
        }
        // не нашлось ни одной закрывающейся скобки
        throw new RuntimeException("Неправильно расставлены скобки");
    }

}
