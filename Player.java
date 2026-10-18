/* Abstract class: shared data and behaviour for every player. */
public abstract class Player {
    private final String name;
    private int wins = 0;

    protected Player(String name) { this.name = name; }

    /* Each subclass says which mark it plays (polymorphism). */
    public abstract Mark getMark();

    public String getName() { return name; }
    public int getWins() { return wins; }
    public void addWin() { wins++; }
    public void resetScore() { wins = 0; }

    @Override
    public String toString() { return name + " (" + getMark().getSymbol() + ")"; }
}
