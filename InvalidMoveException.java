/* Custom checked exception: thrown when a move breaks the rules. */
public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}
