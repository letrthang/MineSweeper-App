package userService;

import dto.*;
import gamePlay.MinesweeperGridPlay;

/**
 * Service responsible for displaying the game state
 * Responsibility: Game visualization and formatting
 */
public class GameDisplayService {

    /**
     * Display the current game grid
     */
    public void displayGrid(MinesweeperGridPlay game) {
        System.out.println("\nHere is your minefield:");

        // Print column headers (1, 2, 3, 4...)
        System.out.print("  ");
        for (int col = 1; col <= game.getSize(); col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        // Print each row
        for (int row = 0; row < game.getSize(); row++) {
            // Print row letter (A, B, C, D...)
            char rowLetter = (char) ('A' + row);
            System.out.print(rowLetter + " ");

            // Print each cell in the row
            for (int col = 0; col < game.getSize(); col++) {
                Cell cell = game.getCell(row, col);
                String cellDisplay = getCellDisplay(cell);
                System.out.print(cellDisplay + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Get the display string for a cell based on its state
     */
    private String getCellDisplay(Cell cell) {
        if (!cell.isRevealed()) {
            return "_";  // Hidden cell
        }

        if (cell.hasMine()) {
            return "*";  // Mine (only shown when game is over)
        }

        int mineCount = cell.getAdjacentMineCount();
        return String.valueOf(mineCount);  // Show number (0, 1, 2, etc.)
    }

    /**
     * Display game over state with all mines revealed
     */
    public void displayGameOver(MinesweeperGridPlay game) {
        System.out.println("\nFinal minefield:");

        // Print column headers
        System.out.print("  ");
        for (int col = 1; col <= game.getSize(); col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        // Print each row with mines revealed
        for (int row = 0; row < game.getSize(); row++) {
            char rowLetter = (char) ('A' + row);
            System.out.print(rowLetter + " ");

            for (int col = 0; col < game.getSize(); col++) {
                Cell cell = game.getCell(row, col);
                String cellDisplay = getGameOverCellDisplay(cell);
                System.out.print(cellDisplay + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Get cell display for game over state (shows all mines)
     */
    private String getGameOverCellDisplay(Cell cell) {
        if (cell.hasMine()) {
            return "*";  // Always show mines
        }

        if (cell.isRevealed()) {
            return String.valueOf(cell.getAdjacentMineCount());
        }

        return "_";  // Unrevealed non-mine cells
    }

    /**
     * Display welcome message
     */
    public void displayWelcome() {
        System.out.println("Welcome to Minesweeper!");
        System.out.println();
    }

    /**
     * Display win message
     */
    public void displayWin() {
        System.out.println("Congratulations, you have won the game!");
        System.out.println("Press any key to play again...");
    }

    /**
     * Display lose message
     */
    public void displayLose() {
        System.out.println("Oh no, you detonated a mine! Game over.");
        System.out.println("Press any key to play again...");
    }

    /**
     * Display reveal result message
     */
    public void displayRevealResult(RevealResult result) {
        switch (result.getType()) {
            case NORMAL_REVEAL:
            case GAME_WON:
                if (result.getAdjacentMineCount() == 1) {
                    System.out.println("This square contains 1 adjacent mine.");
                } else {
                    System.out.println("This square contains " +
                            result.getAdjacentMineCount() + " adjacent mines.");
                }
                break;
            case MINE_HIT:
                // Message will be handled by displayLose()
                break;
            case ALREADY_REVEALED:
                System.out.println("This square is already revealed.");
                break;
        }
        System.out.println();
    }
}
