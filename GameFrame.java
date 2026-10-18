import javax.swing.*;
import java.awt.*;

/* The window. Implements GameListener so it redraws when the game changes. */
public class GameFrame extends JFrame implements GameListener {
    private static final Color BG = new Color(40, 40, 48);

    private final Game game;
    private final Player playerX;
    private final Player playerO;
    private final JButton[][] tiles = new JButton[Board.SIZE][Board.SIZE];
    private final JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel scoreLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JButton undoButton = new JButton("Undo");

    public GameFrame(String nameX, String nameO) {
        super("Tic-Tac-Toe - 2 Player");
        playerX = new XPlayer(nameX);
        playerO = new OPlayer(nameO);
        game = new Game(playerX, playerO);
        game.addListener(this);

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(200, 200, 210));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.setBackground(BG);
        header.setBorder(BorderFactory.createEmptyBorder(12, 10, 8, 10));
        header.add(statusLabel);
        header.add(scoreLabel);

        JPanel grid = new JPanel(new GridLayout(Board.SIZE, Board.SIZE, 4, 4));
        grid.setBackground(BG);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                JButton tile = new JButton();
                tile.setFont(new Font("SansSerif", Font.BOLD, 72));
                tile.setFocusable(false);
                final int row = r, col = c;
                tile.addActionListener(e -> game.playTurn(row, col));
                tiles[r][c] = tile;
                grid.add(tile);
            }
        }
        grid.setPreferredSize(new Dimension(420, 420));

        JButton newRound = new JButton("New Round");
        newRound.addActionListener(e -> game.newRound());
        undoButton.addActionListener(e -> game.undo());
        JButton resetScores = new JButton("Reset Scores");
        resetScores.addActionListener(e -> { game.resetScores(); refresh(); });

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controls.setBackground(BG);
        controls.add(newRound);
        controls.add(undoButton);
        controls.add(resetScores);

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        refresh();
    }

    private void refresh() {
        Board board = game.getBoard();
        int[][] win = board.getWinningCells();

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Mark m = board.getCell(r, c);
                JButton t = tiles[r][c];
                t.setText(m.getSymbol());
                t.setForeground(m.getColor());
                t.setBackground(isWinning(win, r, c) ? new Color(70, 110, 70) : new Color(62, 62, 72));
            }
        }

        if (game.getState() == Game.State.PLAYING) {
            Player p = game.getCurrentPlayer();
            statusLabel.setText(p.getName() + "'s turn (" + p.getMark().getSymbol() + ")");
            statusLabel.setForeground(p.getMark().getColor());
        }
        scoreLabel.setText(String.format("%s: %d   |   %s: %d   |   Draws: %d",
                playerX, playerX.getWins(), playerO, playerO.getWins(), game.getDraws()));
        undoButton.setEnabled(game.canUndo());
    }

    private boolean isWinning(int[][] win, int r, int c) {
        if (win == null) return false;
        for (int[] cell : win)
            if (cell[0] == r && cell[1] == c) return true;
        return false;
    }

    // ---- GameListener ----
    @Override public void onBoardChanged() { refresh(); }

    @Override public void onGameOver(Player winner) {
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setText(winner == null ? "It's a draw!" : winner.getName() + " wins!");
        refresh();
    }

    @Override public void onMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid move", JOptionPane.WARNING_MESSAGE);
    }
}
