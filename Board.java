/*
  Encapsulation: the grid is private. The rest of the program can only
  change it through methods that check the rules.
 */
public class Board {
    public static final int SIZE = 3;

    private final Mark[][] cells = new Mark[SIZE][SIZE];
    private int[][] winningCells = null;   // 3 {row, col} pairs after a win

    public Board() { reset(); }

    public void reset() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                cells[r][c] = Mark.EMPTY;
        winningCells = null;
    }

    public Mark getCell(int row, int col) { return cells[row][col]; }

    public void place(int row, int col, Mark mark) throws InvalidMoveException {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE)
            throw new InvalidMoveException("That square is outside the board.");
        if (cells[row][col] != Mark.EMPTY)
            throw new InvalidMoveException("That square is already taken.");
        cells[row][col] = mark;
    }

    public void clear(int row, int col) { cells[row][col] = Mark.EMPTY; }

    public boolean isFull() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (cells[r][c] == Mark.EMPTY) return false;
        return true;
    }

    /* Does this mark have three in a row? (No side effects.) */
    public boolean checkWin(Mark mark) { return findWinningLine(mark) != null; }

    /* Remember the winning line so the GUI can highlight it. */
    public void markWin(Mark mark) { winningCells = findWinningLine(mark); }

    public int[][] getWinningCells() { return winningCells; }

    private int[][] findWinningLine(Mark m) {
        for (int i = 0; i < SIZE; i++) {
            if (cells[i][0] == m && cells[i][1] == m && cells[i][2] == m)
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};          // row
            if (cells[0][i] == m && cells[1][i] == m && cells[2][i] == m)
                return new int[][]{{0, i}, {1, i}, {2, i}};          // column
        }
        if (cells[0][0] == m && cells[1][1] == m && cells[2][2] == m)
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};              // diagonal
        if (cells[0][2] == m && cells[1][1] == m && cells[2][0] == m)
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};              // anti-diagonal
        return null;
    }
}
