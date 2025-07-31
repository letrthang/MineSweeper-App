package controller;

import dto.*;
import gamePlay.MinesweeperGridPlay;
import userService.GameDisplayService;
import userService.GameInputService;
import exception.GameException;

/**
 * handle input from user. It acts as a dispatcher
 */
public class GameController {
    private final GameDisplayService displayService;
    private final GameInputService inputService;

    public GameController() {
        this.displayService = new GameDisplayService();
        this.inputService = new GameInputService();
    }

    /**
     * Main game loop with enhanced error handling
     */
    public void playGame() {
        displayService.displayWelcome();

        boolean playAgain = true;
        while (playAgain) {
            try {
                playSingleGame();
                playAgain = inputService.askPlayAgain();
            } catch (Exception e) {
                System.err.println("Error during game: " + e.getMessage());
                System.out.println("Starting a new game...");
                System.out.println();
            }
        }

        cleanup();
        System.out.println("Thanks for playing Minesweeper!");
    }

    /**
     * Play a single game session with comprehensive error handling
     */
    private void playSingleGame() throws GameException {
        MinesweeperGridPlay game = null;

        try {
            // Get game configuration from user
            int gridSize = inputService.getGridSize();
            int mineCount = inputService.getMineCount(gridSize);

            // Create new game
            game = new MinesweeperGridPlay(gridSize, mineCount);

            // Main game loop
            gameLoop(game);

        } catch (Exception e) {
            if (game != null) {
                displayService.displayGameOver(game);
            }
            throw new GameException("Game error: " + e.getMessage(), e);
        }
    }

    /**
     * Main game loop logic
     */
    private void gameLoop(MinesweeperGridPlay game) {
        while (game.getGameState() == GameState.PLAYING) {
            try {
                displayService.displayGrid(game);

                String input = inputService.getCellPosition();
                Position position = game.parsePosition(input);
                RevealResult result = game.revealCell(position);

                displayService.displayRevealResult(result);

                // Check game end conditions
                if (result.getType() == RevealResultType.MINE_HIT) {
                    displayService.displayGameOver(game);
                    displayService.displayLose();
                    break;
                } else if (result.getType() == RevealResultType.GAME_WON) {
                    displayService.displayGrid(game);
                    displayService.displayWin();
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid position: " + e.getMessage());
                System.out.println("Please try again (format: A1, B2, etc.).");
                System.out.println();
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println();
            }
        }

        inputService.waitForKeyPress();
    }

    /**
     * Cleanup resources
     */
    private void cleanup() {
        try {
            inputService.close();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}