public class Sorting {
    public int[] selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int cursor = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[cursor]) {
                    cursor = j;
                }
            }
            int low = a[cursor];
            a[cursor] = a[i];
            a[i] = low;
        }
        return a ;
    }
    public int[] insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int start = a[i];
            int k = i - 1;
            while ((k >= 0) && (a[k] > start)) {
                a[k + 1] = a[k];
                k--;
            }
            a[k + 1] = start;
        }
        return a;
    }
}