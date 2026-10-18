/* Immutable record of one move; kept in a list so moves can be undone. */
public class Move {
    private final int row;
    private final int col;
    private final Mark mark;

    public Move(int row, int col, Mark mark) {
        this.row = row;
        this.col = col;
        this.mark = mark;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Mark getMark() { return mark; }
}
