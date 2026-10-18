/* Observer interface: the GUI implements this to react to game events. */
public interface GameListener {
    void onBoardChanged();
    void onGameOver(Player winnerOrNull);
    void onMessage(String message);
}
