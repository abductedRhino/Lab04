import java.util.ArrayList;

public class ChessBoard {
    private final int rows;
    private ArrayList<Integer[][]> solutions;
    private final int[][] board;

    public ChessBoard(int rows) {
        this.rows = rows;
        this.board = new int[rows][rows];
    }

    public void run() {
        this.solutions = new ArrayList<>();

    }
}
