import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*

        QueenViewer viewer = new QueenViewer(8, 800);
        viewer.display();

         */


        ChessBoard board = new ChessBoard(4,800);
        System.out.println(board.toString());
        board.addQueen(0, 1);
        board.addQueen(1, 3);
        board.addQueen(2, 0);
        board.addQueen(3, 2);
        System.out.println(board.toString());
        System.out.println(Arrays.toString(board.board[0]));
        System.out.println(Arrays.toString(board.board[1]));
        System.out.println(Arrays.toString(board.board[2]));
        System.out.println(Arrays.toString(board.board[3]));
        /*
        |  _|  Q|  _|  _|
        |  _|  _|  _|  Q|
        |  Q|  _|  _|  _|
        |  _|  _|  Q|  _|

        [ 1, -1,  3,  1]
        [ 3,  2,  2, -1]
        [-1,  2,  2,  2]
        [ 1,  3, -1,  1]
         */
        ChessBoard newBoard = new ChessBoard(8,800);
        newBoard.run();
        newBoard.display();
        System.out.println(Arrays.toString(newBoard.board[0]));
        System.out.println(Arrays.toString(newBoard.board[1]));
        System.out.println(Arrays.toString(newBoard.board[2]));
        System.out.println(Arrays.toString(newBoard.board[3]));
        System.out.println(Arrays.toString(newBoard.board[4]));
        System.out.println(Arrays.toString(newBoard.board[5]));
        System.out.println(Arrays.toString(newBoard.board[6]));
        System.out.println(Arrays.toString(newBoard.board[7]));
    }
}
