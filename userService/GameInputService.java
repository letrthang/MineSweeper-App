package userService;

import config.GameConfiguration;
import java.util.Scanner;

/**
 * Service responsible for handling user input
 * Responsibility: Input validation and collection
 */
public class GameInputService {
    private final Scanner scanner;

    public GameInputService() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Get grid size from user input
     */
    public int getGridSize() {
        while (true) {
            try {
                System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
                System.out.println();
                int size = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (size < 2) {
                    System.out.println("Grid size must be at least 2. Please try again.");
                    continue;
                }

                if (size > 26) {
                    System.out.println("Grid size cannot exceed 26 (A-Z). Please try again.");
                    continue;
                }

                return size;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    /**
     * Get number of mines from user input
     */
    public int getMineCount(int gridSize) {
        int maxMines = GameConfiguration.getMaxMines(gridSize);

        while (true) {
            try {
                System.out.print("Enter the number of mines to place on the grid " +
                        "(maximum is 35% of the total squares): ");
                System.out.println();
                int mines = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (!GameConfiguration.isValidMineCount(gridSize, mines)) {
                    System.out.println("Invalid number of mines. Please enter between 1 and " +
                            maxMines + " mines.");
                    continue;
                }

                return mines;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    /**
     * Get cell position from user input
     */
    public String getCellPosition() {
        while (true) {
            try {
                System.out.print("Select a square to reveal (e.g. A1): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Please enter a position (e.g. A1).");
                    continue;
                }

                return input;
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    /**
     * Wait for user to press any key
     */
    public void waitForKeyPress() {
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Ask if user wants to play again
     */
    public boolean askPlayAgain() {
        while (true) {
            try {
                System.out.print("Do you want to play again? (y/n): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("y") || input.equals("yes")) {
                    return true;
                } else if (input.equals("n") || input.equals("no")) {
                    return false;
                } else {
                    System.out.println("Please enter 'y' for yes or 'n' for no.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    /**
     * Close the scanner (call when application exits)
     */
    public void close() {
        scanner.close();
    }
}
