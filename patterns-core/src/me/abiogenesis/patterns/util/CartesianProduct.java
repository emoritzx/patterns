package me.abiogenesis.patterns.util;

import java.util.*;
import java.util.stream.Stream;

public final class CartesianProduct<T> implements Iterator<List<T>> {

    private final List<List<T>> values;
    private int current = 0;
    private final long last;

    public static <T> Iterable<List<T>> of(Collection<T> ... lists) {
        return () -> new CartesianProduct<>(Arrays.asList(lists));
    }

    public CartesianProduct(Collection<? extends Collection<T>> rawValues) {
        values = new ArrayList<>(rawValues.size());
        long product = 1L;
        for (Collection<T> list: rawValues) {
            values.add(new ArrayList<>(list));
            product *= list.size();
        }
        last = product;
    }

    public boolean hasNext() {
        return current != last;
    }

    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ++current;
        List<T> combination = new ArrayList<>();
        int n = current - 1;
        int start = 0;
        while (start < values.size()) {
            List<T> inner = values.get(start);
            combination.add(inner.get(n % inner.size()));
            n /= inner.size();
            ++start;
        }
        return combination;
    }

    public void remove() {
        ++current;
    }

}
