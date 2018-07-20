package com.turlir.calculator.translator

import com.turlir.calculator.converter.Member
import com.turlir.calculator.member.Operator
import com.turlir.calculator.member.Operators
import java.util.*

/**
 * Алгоритм обратной польской записи
 */
class PolishTranslator : NotationTranslator {

    /**
     * Сортирует выражение согласно алгоритму обратной польской записи
     * @param parent математическое выражение
     * @return очередь токенов выражения в инфиксной форме
     * @throws Exception в случае ошибки разрабора
     */
    @Throws(Exception::class)
    override fun translate(parent: Iterator<Member>): Queue<Member> {
        val operators = ArrayDeque<Operator>()
        val converted = LinkedList<Member>()

        while (parent.hasNext()) {
            val now = parent.next()
            val isNumber = now.operand()
            when {
                isNumber -> converted.add(now)
                now == Operators.OS -> operators.addLast(Operators.CS)
                now == Operators.CS -> closedBracket(operators.descendingIterator(), converted)
                else -> {
                    now as Operator
                    operator(now, operators.descendingIterator(), converted)
                    operators.addLast(now)
                }
            }
        }

        while (!operators.isEmpty()) { // дописываем остатки операторов
            val op = operators.pollLast()
            if (op == Operators.CS) throw Exception("Неправильно расставлены скобки")
            converted.add(op)
        }

        return converted
    }

    private fun operator(current: Operator, iter: MutableIterator<Operator>, converted: Queue<Member>) {
        while (iter.hasNext()) {
            val prev = iter.next()
            if (prev == Operators.CS) { // 4 * ( 1 + 2 )
                break
            } else if (current.compareTo(prev) < 1) {
                // для всех операторов из стека, приоритет которых больше текущего оператора
                iter.remove()
                converted.add(prev)
            }
        }
    }

    @Throws(Exception::class)
    private fun closedBracket(iter: MutableIterator<Operator>, converted: Queue<Member>) {
        while (iter.hasNext()) {
            val tmp = iter.next()
            iter.remove() // poolLast() analog
            if (tmp != Operators.CS) {
                converted.add(tmp)
            } else {
                return
            }
        }
        // не нашлось ни одной закрывающейся скобки
        throw Exception("Неправильно расставлены скобки")
    }

}
