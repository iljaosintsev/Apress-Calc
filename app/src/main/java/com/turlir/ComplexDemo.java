package com.turlir;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexDemo implements IDemo {

    private final Pattern mOperators = Pattern.compile("sin\\(|cos\\(|tag\\(|[(]|[)]");

    private final IDemo mDemo;

    ComplexDemo(IDemo demo) {
        mDemo = demo;
    }

    @Override
    public List<Pair<String, String>> input(String s) {
        List<Pair<String, String>> origin = mDemo.input(s);
        List<Pair<String, String>> now = new ArrayList<>(origin.size());
        for (Pair<String, String> p : origin) {
            if (TYPE_AND.equals(p.first)) {
                now.add(p);
            } else {
                if (p.second.length() > 1) {
                    List<Pair<String, String>> split = split(p.second);
                    now.addAll(split);
                } else {
                    now.add(p);
                }
            }
        }
        return now;
    }

    private List<Pair<String, String>> split(String o) {
        Matcher matcher = mOperators.matcher(o);
        if (matcher.find()) {
            List<Pair<String, String>> arr = new ArrayList<>(matcher.groupCount());
            do {
                arr.add(new Pair<>(TYPE_OR, matcher.group()));
            } while (matcher.find());
            return arr;
        } else {
            throw new IllegalArgumentException("неизвестный оператор");
        }
    }

}
