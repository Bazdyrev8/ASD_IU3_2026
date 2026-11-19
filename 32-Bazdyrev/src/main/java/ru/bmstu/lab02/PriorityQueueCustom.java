package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом (min-heap или max-heap) на основе динамического массива.
 *
 * @param <T> тип элементов очереди
 */
public class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;

    private Object[] arr;
    private int n;
    private final Comparator<T> comparator;

    // Создаёт пустую очередь с естественным порядком сортировки (min-heap).
    public PriorityQueueCustom() {
        this((Comparator<T>) null);
    }

    /**
     * Создаёт пустую очередь с заданным comparator.
     * Если comparator равен null, используется естественный порядок.
     *
     * @param comparator компаратор для определения приоритета
     */
    public PriorityQueueCustom(Comparator<T> comparator) {
        this.arr = new Object[DEFAULT_CAPACITY];
        this.n = 0;
        this.comparator = comparator;
    }

    /**
     * Добавляет элемент в очередь за O(log n).
     *
     * @param item добавляемый элемент
     */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        if (n == arr.length) {
            grow();
        }
        arr[n] = item;
        siftUp(n);
        n++;
    }

    /**
     * Извлекает и возвращает элемент с наивысшим приоритетом за O(log n).
     *
     * @return элемент с наивысшим приоритетом
     * @throws NoSuchElementException если очередь пуста
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (n == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        T top = (T) arr[0];
        n--;
        arr[0] = arr[n];
        arr[n] = null; // очищаем ссылку, чтобы не было утечки памяти
        if (n > 0) {
            siftDown(0);
        }
        return top;
    }

    /**
     * Возвращает элемент с наивысшим приоритетом без удаления за O(1).
     *
     * @return элемент с наивысшим приоритетом или null, если очередь пуста
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (n == 0) {
            return null;
        }
        return (T) arr[0];
    }

    /**
     * Возвращает количество элементов в очереди за O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return n;
    }

    /**
     * Проверяет, пуста ли очередь, за O(1).
     *
     * @return true, если очередь пуста
     */
    public boolean isEmpty() {
        return n == 0;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (compare(arr[i], arr[parent]) < 0) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int best = i;

            if (left < n && compare(arr[left], arr[best]) < 0) {
                best = left;
            }
            if (right < n && compare(arr[right], arr[best]) < 0) {
                best = right;
            }
            if (best != i) {
                swap(i, best);
                i = best;
            } else {
                break;
            }
        }
    }

    private void grow() {
        Object[] newArr = new Object[arr.length * 2];
        for (int i = 0; i < n; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    private void swap(int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) {
        if (comparator != null) {
            return comparator.compare((T) a, (T) b);
        }
        return ((Comparable<? super T>) a).compareTo((T) b);
    }
}