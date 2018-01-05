package com.turlir;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

class Demo implements IDemo {

    @Override
    public List<Pair<String, String>> input(final String s) {
        final String str = s.trim();
        List<Pair<String, String>> arr = new ArrayList<>();
        int length = str.length();
        int index = 0;
        int offset = 0;
        do {
            char c = str.charAt(index);
            boolean f = digit(c);
            boolean l; // false
            do {
                offset++;
                if (index + offset < length) {
                    c = str.charAt(index + offset);
                    l = digit(c);
                } else {
                    break;
                }
            } while (f == l);

            String subs = str.substring(index, index + offset);
            if (f) {
                arr.add(new Pair<>(TYPE_AND, subs));
                if (index + offset < length &&
                    operator(str.charAt(index + offset))) {
                    String o = str.substring(index + offset, index + offset + 1);
                    arr.add(new Pair<>(TYPE_OR, o)); // односложный оператор, быстрый путь
                    index++;
                }

            } else {
                arr.add(new Pair<>(TYPE_OR, subs)); // односложный оператор, медленный путь
            }

            index += offset;
            offset = 0;
        } while (index < length);

        return arr;
    }

    private boolean operator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean digit(char c) {
        return Character.isDigit(c) || c == '.' /*|| c == ','*/;
    }

}
