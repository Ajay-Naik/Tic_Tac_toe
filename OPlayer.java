/* Player who plays O. */
public class OPlayer extends Player {
    public OPlayer(String name) { super(name); }

    @Override
    public Mark getMark() { return Mark.O; }
}
