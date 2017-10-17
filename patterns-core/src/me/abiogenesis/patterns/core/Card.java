/**
 * Copyright (c) 2017, Evan Moritz.
 * Licensed under the MIT License. See the accompanying LICENSE file for terms.
 */
package me.abiogenesis.patterns.core;

import java.util.Comparator;
import java.util.List;

public final class Card implements Comparable<Card> {

    private final Color color;
    private final Fill fill;
    private final Number number;
    private final Shape shape;

    public static final Comparator<Card> COMPARATOR
        = Comparator
            .comparing(Card::getColor)
            .thenComparing(Card::getFill)
            .thenComparing(Card::getNumber)
            .thenComparing(Card::getShape);

    public Card(Color color, Fill fill, Number number, Shape shape) {
        this.color = color;
        this.fill = fill;
        this.number = number;
        this.shape = shape;
    }

    Card(List<Enum> gene) {
        this(
            (Color) gene.get(0),
            (Fill) gene.get(1),
            (Number) gene.get(2),
            (Shape) gene.get(3));
    }

    public Color getColor() {
        return color;
    }

    public Fill getFill() {
        return fill;
    }

    public Number getNumber() {
        return number;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public int compareTo(Card o) {
        return COMPARATOR.compare(this, o);
    }

    public boolean disjoint(Card o) {
        return color != o.color
            && fill != o.fill
            && number != o.number
            && shape != o.shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (color != card.color) return false;
        if (fill != card.fill) return false;
        if (number != card.number) return false;
        return shape == card.shape;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + fill.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + shape.hashCode();
        return result;
    }
}
