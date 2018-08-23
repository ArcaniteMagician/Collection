package com.endymion.weather;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    int[] array;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        initArray();
        bubbleSort(array);
        initArray();
        selectionSort(array);
    }

    public void initArray() {
        array = new int[]{2, 4, 6, 1, 5, 3, 9, 8};
        System.out.println("------------");
        System.out.println(Arrays.toString(array));
    }

    private void bubbleSort(int[] array) {
        int temp;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j + 1] < array[j]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }

    private void selectionSort(int[] array) {
        int temp;
        int min;
        for (int i = 0; i < array.length; i++) {
            min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = array[i];
                array[i] = array[min];
                array[min] = temp;
                System.out.println(Arrays.toString(array));
            }
        }
    }

    public static class TestClass {
        public void test() {
            // 静态内部类无法直接引内外部类的非静态对象
        }
    }
}