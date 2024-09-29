package Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SudokuSolver extends JFrame {

    private static final int GRID_SIZE = 9;
    private JTextField[][] boardFields = new JTextField[GRID_SIZE][GRID_SIZE];

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setLayout(new GridLayout(GRID_SIZE + 1, GRID_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        // Create the Sudoku grid
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                JTextField field = new JTextField(2);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setText("0"); // Initial value
                boardFields[row][column] = field;
                add(field);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = getBoardFromFields();
                if (solveBoard(board)) {
                    updateFields(board);
                    JOptionPane.showMessageDialog(null, "Solved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Unsolvable board :(");
                }
            }
        });
        add(solveButton);
    }

    private int[][] getBoardFromFields() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                try {
                    int value = Integer.parseInt(boardFields[row][column].getText());
                    board[row][column] = value;
                } catch (NumberFormatException e) {
                    board[row][column] = 0;
                }
            }
        }
        return board;
    }

    private void updateFields(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                boardFields[row][column].setText(String.valueOf(board[row][column]));
            }
        }
    }

    private boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;

                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }
}