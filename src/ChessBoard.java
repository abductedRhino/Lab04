import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChessBoard {
    private final int size;
    private ArrayList<int[][]> solutions;
    public final int[][] board;
    public boolean done;
    private int r;
    private int c;

    public ChessBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void run() {
        clearBoard();
        this.solutions = new ArrayList<>();
        this.done = false;
        this.r = 0;
        this.c = 0;
        while (!done) {
            backtrack(r, c);
        }
    }

    private void clearBoard() {
        for (int[] row : board) {
            Arrays.fill(row, 0);
        }
    }

    public void backtrack(int row, int column) {
        boolean danger = board[row][column] > 0;
        // no save spot in this row:
        if (column == size - 1 && danger) {
            // if last queen not on edge, move it.
            if (findQueen(row - 1) != size - 1) {
                backtrack(row - 1, removeQueen(row - 1) + 1);
            }
            // if last queen on edge and last row > 0
            else if (row - 1 > 0) {
                removeQueen(row - 1);
                backtrack(row - 2, removeQueen(row - 2) + 1);
            }
            // if last queen on edge and last row == 0
            else if (row - 1 == 0) {
                System.out.println("done.");
                this.done = true;
            }
        }
        // this spot is unsafe, but more columns are available in the same row:
        else if (danger) {
            backtrack(row, column + 1);
        }
        // this spot is safe, and it is the last row:
        else if (row == size - 1) {
            addQueen(row, column);
            solutions.add(this.board);
            System.out.println(solutions.size()+"\n"+this);
            if (column < size - 1) {
                this.r = row;
                this.c = removeQueen(row) + 1;
            } else {
                removeQueen(row);
                this.r = row - 1;
                this.c = removeQueen(row - 1) + 1;
            }
        }
        // this spot is safe, but not last row:
        else {
            addQueen(row, column);
            backtrack(row + 1, 0);
        }
    }

    public int removeQueen(int row) {
        int column = findQueen(row);
        // set queen to empty:
        board[row][column] = 0;
        // remove her threats
        int i = 1;
        while (row + i < size) {
            board[row + i][column]--;
            //can go left?
            if (column - i >= 0) {
                board[row + i][column - i]--;
            }
            //can go right?
            if (column + i < size) {
                board[row + i][column + i]--;
            }
            i++;
        }
        return column;
    }

    public int findQueen(int row) {
        for (int column = 0; column < size; column++) {
            if (board[row][column] == -1) {
                return column;
            }
        }
        return -1;
    }

    public void addQueen(int row, int column) {
        board[row][column] = -1;
        int i = 1;
        while (row + i < size) {
            board[row + i][column]++;
            //can go left?
            if (column - i >= 0) {
                board[row + i][column - i]++;
            }
            //can go right?
            if (column + i < size) {
                board[row + i][column + i]++;
            }
            i++;
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == -1) {
                    ret = ret.concat("|Q");
                } else if (board[row][column] > -1) {
                    ret = ret.concat("|_");
                }
            }
            ret = ret.concat("|\n");
        }
        return ret;
    }

}
