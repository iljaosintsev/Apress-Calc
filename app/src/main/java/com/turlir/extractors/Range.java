package com.turlir.extractors;

class Range {

    private int index;
    private int offset;

    Range(int low, int up) {
        this.index = low;
        this.offset = up;
    }

    void captureNext() {
        offset++;
    }

    char capture(String str) {
        return str.charAt(index + offset);
    }

    char begin(String str) {
        return str.charAt(index);
    }

    boolean exact(int l) {
        return index + offset < l;
    }

    boolean exactWithoutOffset(int l) {
        return index < l;
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
