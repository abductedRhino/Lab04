import javax.swing.*;
import java.awt.*;

public class QueenViewer {
    private JFrame frame;
    private Dimension dimension;

    public QueenViewer(int dimension) {
        this.dimension = new Dimension(dimension, dimension);
    }

    public void makeFrame() {
        frame = new JFrame("stuff");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(dimension);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private void drawChessBoard(int size) {

    }
}
