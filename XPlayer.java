/* Player who plays X. */
public class XPlayer extends Player {
    public XPlayer(String name) { super(name); }

    @Override
    public Mark getMark() { return Mark.X; }
}
