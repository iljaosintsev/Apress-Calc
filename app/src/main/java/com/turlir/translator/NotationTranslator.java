package com.turlir.translator;

import com.turlir.converter.Member;

import java.util.Iterator;
import java.util.Queue;

public interface NotationTranslator {

    Queue<Member> translate(Iterator<Member> parent);

}
