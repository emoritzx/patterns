package me.abiogenesis.patterns.core;

import me.abiogenesis.patterns.util.CartesianProduct;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

public final class Deck {

    static final List<List<Enum>> PROPERTIES
        = Collections.unmodifiableList(Arrays.asList(
            Arrays.asList(Color.values()),
            Arrays.asList(Fill.values()),
            Arrays.asList(Number.values()),
            Arrays.asList(Shape.values())));


    public static Stack<Card> create() {
        Iterable<List<Enum>> iterable = () -> new CartesianProduct<>(PROPERTIES);
        return StreamSupport.stream(iterable.spliterator(), false)
            .map(Card::new)
            .collect(Collector.of(
                Stack<Card>::new,
                Stack::add,
                (a, b) -> { throw new UnsupportedOperationException(); }));
    }
}
