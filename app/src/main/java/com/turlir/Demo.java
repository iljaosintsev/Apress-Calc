package com.turlir;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

class Demo implements IDemo {

    private static class Range {
        int index;
        private int offset;

        Range(int low, int up) {
            this.index = low;
            this.offset = up;
        }

        void captureNext() {
            offset++;
        }

        boolean exact(int l) {
            return index + offset < l;
        }

        boolean exactWithoutOffset(int l) {
            return index < l;
        }

        int sum() {
            return index + offset;
        }

        Range cycle() {
            return new Range(index += offset, 0);
        }

        String directSubstring(String s) {
            return s.substring(index, index + offset);
        }

        String nextSubstring(String s) {
            String substring = s.substring(index + offset, index + offset + 1);
            index++;
            return substring;
        }

    }

    @Override
    public List<Pair<String, String>> input(final String s) {
        final String str = s.trim();
        List<Pair<String, String>> arr = new ArrayList<>();
        int length = str.length();
        Range r = new Range(0, 0);

        do {
            char c = str.charAt(r.index);
            boolean f = digit(c);
            boolean l; // false
            do {
                r.captureNext();
                if (r.exact(length)) {
                    c = str.charAt(r.sum());
                    l = digit(c);
                } else {
                    break;
                }
            } while (f == l);

            String subs = r.directSubstring(str);
            if (f) {
                arr.add(new Pair<>(TYPE_AND, subs));
                if (r.exact(length) && operator(str.charAt(r.sum()))) {
                    String o = r.nextSubstring(str);
                    arr.add(new Pair<>(TYPE_OR, o)); // односложный оператор, быстрый путь
                }

            } else {
                arr.add(new Pair<>(TYPE_OR, subs)); // односложный оператор, медленный путь
            }

            r = r.cycle();
        } while (r.exactWithoutOffset(length));

        return arr;
    }

    private boolean operator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean digit(char c) {
        return Character.isDigit(c) || c == '.' /*|| c == ','*/;
    }

}
