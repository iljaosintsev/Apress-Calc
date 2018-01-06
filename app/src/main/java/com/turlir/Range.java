package com.turlir;

class Range {

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
