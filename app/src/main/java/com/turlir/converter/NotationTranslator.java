package com.turlir.converter;

import java.util.Iterator;
import java.util.Queue;

public interface NotationTranslator {

    Queue<Member> translate(Iterator<Member> parent);

}
