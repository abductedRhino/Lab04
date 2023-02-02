public class Sorting {
    public int[] selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int j = i;
            for (int k = i + 1; k < a.length; k++) {
                if (a[k] < a[j]) {
                    j = k;
                }
            }
            int low = a[j];
            a[j] = a[i];
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