import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessAnimation {
    private JFrame frame;
    private static JPanel panel;
    private Dimension d;
    private int[][] board;
    private int[] queens;
    private int size;
    private ArrayList<int[][]> solutions;
    private static boolean done;
    private int r;
    private int c;

    public ChessAnimation(int frameSize, int boardSize) {
        this.board = new int[boardSize][boardSize];
        this.queens = new int[boardSize];
        this.d = new Dimension(frameSize, frameSize);
        this.size = boardSize;
        this.r = 0;
        this.c = 0;
    }

    public static void main(String[] args) {
        ChessAnimation animation = new ChessAnimation(800, 8);
        animation.displayBackground();
        animation.backtrack(animation.c, animation.r);
    }

    public void displayBackground() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawChessBoard((Graphics2D) g);
            }
        };
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(d.width, d.height + 35);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void displayAnimation(Graphics2D g) {
        int queenSize = (int) Math.round((double) panel.getWidth() / (board.length * 2));
        int fieldSize = (int) Math.round((double) panel.getWidth() / board.length);
        Point upperLeft = new Point(queenSize/2, queenSize/2);
        g.setColor(Color.BLUE);
        while (!done) {
            for (int r = 0; r < queens.length; r++) {
                int c = queens[r];
                g.fillOval(
                        upperLeft.x + (c*fieldSize),
                        upperLeft.y + (r * fieldSize),
                        queenSize,queenSize);
            }
        }
    }

    private void drawChessBoard(Graphics2D g) {
        int fieldSize = (int) Math.round((double) panel.getWidth() / board.length);
        Point upperLeft = new Point(0, 0);
        Color lightField = Color.LIGHT_GRAY;
        Color darkField = Color.DARK_GRAY;
        g.setColor(lightField);
        for (int row = upperLeft.y; row < panel.getHeight(); row += fieldSize) {
            for (int col = upperLeft.x; col < panel.getHeight(); col += fieldSize) {
                g.fillRect(col, row, fieldSize, fieldSize);
                g.setColor(g.getColor() == lightField ? darkField : lightField);
            }
            g.setColor(g.getColor() == lightField ? darkField : lightField);
        }
    }

    public void backtrack(int row, int column) {
        displayAnimation((Graphics2D) panel.getGraphics());
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
                done = true;
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
        queens[row] = column;
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

}
