package exception;

/**
 * Custom exception for game-related errors
 * Provides better error handling and debugging information
 */
public class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
