import java.util.ArrayList;
import java.util.List;

/* Game rules and turn handling. Knows nothing about Swing. */
public class Game {
    public enum State { PLAYING, WON, DRAW }

    private final Board board = new Board();
    private final List<Player> players = new ArrayList<>();
    private final List<Move> history = new ArrayList<>();
    private final List<GameListener> listeners = new ArrayList<>();
    private int currentIndex = 0;
    private State state = State.PLAYING;
    private Player winner = null;
    private int draws = 0;

    public Game(Player first, Player second) {
        players.add(first);
        players.add(second);
    }

    public void addListener(GameListener l) { listeners.add(l); }

    public Board getBoard() { return board; }
    public State getState() { return state; }
    public Player getWinner() { return winner; }
    public int getDraws() { return draws; }
    public Player getCurrentPlayer() { return players.get(currentIndex); }
    public boolean canUndo() { return state == State.PLAYING && !history.isEmpty(); }

    /* The current player tries to play on (row, col). */
    public boolean playTurn(int row, int col) {
        if (state != State.PLAYING) return false;
        Player p = getCurrentPlayer();
        try {
            board.place(row, col, p.getMark());
        } catch (InvalidMoveException e) {
            for (GameListener l : listeners) l.onMessage(e.getMessage());
            return false;
        }
        history.add(new Move(row, col, p.getMark()));

        if (board.checkWin(p.getMark())) {
            state = State.WON;
            board.markWin(p.getMark());
            winner = p;
            p.addWin();
            notifyBoard();
            for (GameListener l : listeners) l.onGameOver(p);
        } else if (board.isFull()) {
            state = State.DRAW;
            draws++;
            notifyBoard();
            for (GameListener l : listeners) l.onGameOver(null);
        } else {
            currentIndex = (currentIndex + 1) % players.size();
            notifyBoard();
        }
        return true;
    }

    /* Takes back the last move (only while the round is still running). */
    public void undo() {
        if (!canUndo()) return;
        Move last = history.remove(history.size() - 1);
        board.clear(last.getRow(), last.getCol());
        currentIndex = (currentIndex + players.size() - 1) % players.size();
        notifyBoard();
    }

    /* New round; the player who starts alternates. */
    public void newRound() {
        board.reset();
        history.clear();
        state = State.PLAYING;
        winner = null;
        currentIndex = (currentIndex + 1) % players.size();
        notifyBoard();
    }

    public void resetScores() {
        for (Player p : players) p.resetScore();
        draws = 0;
    }

    private void notifyBoard() { for (GameListener l : listeners) l.onBoardChanged(); }
}
