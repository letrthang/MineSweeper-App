import controller.GameController;

/**
 * Main application entry point for Minesweeper game
 *
 * This is the main class that starts the Minesweeper game application.
 * It follows the single responsibility principle by delegating all game logic
 * to the GameController.
 *
 * @author Your Name
 * @version 1.0
 * @since Java 17
 */
public class MinesweeperApplication {

    /**
     * Main method - Entry point of the application
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        try {
            // Create and start the game controller
            GameController gameController = new GameController();
            gameController.playGame();

        } catch (Exception e) {
            // Handle any unexpected errors gracefully
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.err.println("Please restart the application.");
            e.printStackTrace();
        }
    }
}