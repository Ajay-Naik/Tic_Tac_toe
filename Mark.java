import java.awt.Color;

/* Enum: what a board cell can contain. */
public enum Mark {
    EMPTY("", Color.DARK_GRAY),
    X("X", new Color(80, 160, 255)),
    O("O", new Color(255, 170, 60));

    private final String symbol;
    private final Color color;

    Mark(String symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() { return symbol; }
    public Color getColor() { return color; }
}
