import javax.swing.*;
import java.awt.*;

public class QueenViewer {
    private JFrame frame;
    private JPanel panel;
    private Dimension dimension;

    private ChessBoard chessBoard;

    public QueenViewer(int chessBoardSize, int GUISize) {
        this.dimension = new Dimension(GUISize, GUISize);
        this.chessBoard = new ChessBoard(chessBoardSize, GUISize);
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
    }

    private void drawChessBoard(Graphics2D g) {
        int fieldSize = (int) Math.round((double) panel.getHeight() / chessBoard.getWidth());
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

}
