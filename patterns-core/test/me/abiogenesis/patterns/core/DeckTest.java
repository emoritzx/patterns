/**
 * Copyright (c) 2017, Evan Moritz.
 * Licensed under the MIT License. See the accompanying LICENSE file for terms.
 */
package me.abiogenesis.patterns.core;

import org.testng.annotations.Test;
import java.util.List;
import java.util.Stack;
import static org.testng.Assert.*;

public class DeckTest {

    @Test
    public void testCreate() throws Exception {
        Stack<Card> deck = Deck.create();
        assertEquals(
            deck.size(),
            Deck.PROPERTIES.stream()
                .mapToInt(List::size)
                .reduce(1, (a, b) -> a * b));
    }
}