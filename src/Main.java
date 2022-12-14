import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sorting Test = new Sorting();
        int[] aeh = Test.selectionSort(new int[]{4,7,3,1,6});
        System.out.println(Arrays.toString(aeh));

        int[] oeh = Test.insertionSort(new int[]{4,7,3,1,6});
        System.out.println(Arrays.toString(oeh));

        ChessBoard board = new ChessBoard(8, 1000);
        board.run();
    }
}
