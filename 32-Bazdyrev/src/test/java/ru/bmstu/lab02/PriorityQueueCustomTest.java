package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class PriorityQueueCustomTest {

    @Test
    void shouldBeEmptyWhenCreated() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
        assertNull(pq.peek());
    }

    @Test
    void shouldThrowExceptionWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, pq::poll);
    }

    @Test
    void shouldThrowExceptionWhenAddingNull() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> pq.add(null));
    }

    @Test
    void shouldHandleSingleElement() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(42);

        assertFalse(pq.isEmpty());
        assertEquals(1, pq.size());
        assertEquals(42, pq.peek());
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    void shouldExtractElementsInNonDecreasingOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int[] input = {5, 1, 9, -3, 0, 7, 2};
        for (int x : input) {
            pq.add(x);
        }

        int[] expected = {-3, 0, 1, 2, 5, 7, 9};
        assertEquals(expected.length, pq.size());
        for (int exp : expected) {
            assertEquals(exp, pq.peek());
            assertEquals(exp, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    @Test
    void shouldHandleDuplicateElements() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(1);
        pq.add(5);
        pq.add(1);
        pq.add(3);

        assertEquals(1, pq.poll());
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void shouldGrowAutomaticallyBeyondDefaultCapacity() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int count = 100;
        for (int i = count - 1; i >= 0; i--) {
            pq.add(i);
        }

        assertEquals(count, pq.size());
        for (int i = 0; i < count; i++) {
            assertEquals(i, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    @Test
    void shouldWorkWithStrings() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>();
        pq.add("cherry");
        pq.add("apple");
        pq.add("banana");

        assertEquals("apple", pq.poll());
        assertEquals("banana", pq.poll());
        assertEquals("cherry", pq.poll());
    }
}