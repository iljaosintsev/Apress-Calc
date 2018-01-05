package com.turlir;

import android.support.v4.util.Pair;

import java.util.List;

interface IDemo {

    String
            TYPE_OR = "or",
            TYPE_AND = "and";

    List<Pair<String, String>> input(String s);

}
