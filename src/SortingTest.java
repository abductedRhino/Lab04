import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    Sorting sort = new Sorting();

    @org.junit.jupiter.api.Test
    void selectionSort() {
        int[] test = new int[0];
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{1, 3, 5};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{0, 0, 0, 0};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{2, 5, 3, 1};
        assertArrayEquals(new int[]{1, 2, 3, 5}, sort.insertionSort(test));
    }

    @org.junit.jupiter.api.Test
    void insertionSort() {
        int[] test = new int[0];
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{1, 3, 5};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{0, 0, 0, 0};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{2, 5, 3, 1};
        assertArrayEquals(new int[]{1, 2, 3, 5}, sort.insertionSort(test));
    }
}