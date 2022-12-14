import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private Dimension dimension;
    private final int size;
    private ArrayList<int[][]> solutions;
    public final int[][] board;
    public int[] queens;
    public boolean done;
    private int r;
    private int c;
    private int t;
    private Timer timer;

    public ChessBoard(int chessBoardSize, int GUISize) {
        this.dimension = new Dimension(GUISize, GUISize);
        this.size = chessBoardSize;
        this.board = new int[chessBoardSize][chessBoardSize];
        this.queens = new int[chessBoardSize];
    }

    public void run() {
        clearBoard();
        this.t = 0;
        this.solutions = new ArrayList<>();
        this.done = false;
        this.r = 0;
        this.c = 0;
        this.timer = new Timer(500, this);
        display();
        backtrack(r, c);
    }

    private void clearBoard() {
        for (int[] row : board) {
            Arrays.fill(row, 0);
        }
    }

    public void backtrack(int row, int column) {
        t++;
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

    public void display() {
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
        frame.setSize(dimension.width, dimension.height+frame.getContentPane().getHeight());
        frame.setResizable(false);
        frame.setVisible(true);
        timer.start();
    }

    private void drawChessBoard(Graphics2D g) {
        int fieldSize = (int) Math.round((double) panel.getHeight() / this.size);
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
        g.setColor(Color.CYAN);
        int half = (int) Math.round((double) fieldSize / 2);
        for (int queenRow = half, i = 0; queenRow <= panel.getHeight(); queenRow += fieldSize, i++) {
            g.fillOval(half+queens[i]*fieldSize,queenRow,20,20);
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

    public int getWidth() {
        return this.size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t++;
        if (done) {
            timer.stop();
        }
        panel.repaint();
    }
}
