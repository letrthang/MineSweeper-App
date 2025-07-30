package dto;

import java.util.*;

/**
 * Main grid class representing the minesweeper board
 */
public class MinesweeperGrid {
    private final int size;                          // Grid size (size x size)
    private final int totalMines;                    // Total number of mines
    private final Cell[][] grid;                     // 2D array of cells
    private GameState gameState;                     // Current game state
    private int revealedCells;                       // Number of revealed non-mine cells
    private final Set<Position> minePositions;       // Track mine locations for efficiency

    public MinesweeperGrid(int size, int totalMines) {
        this.size = size;
        this.totalMines = totalMines;
        this.grid = new Cell[size][size];
        this.gameState = GameState.PLAYING;
        this.revealedCells = 0;
        this.minePositions = new HashSet<>();

        initializeGrid();
    }

    /**
     * Initialize the grid with empty cells
     */
    private void initializeGrid() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = new Cell();
            }
        }
    }

    // ========== Getters ==========
    public int getSize() {
        return size;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Get cell at specific position
     */
    public Cell getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        throw new IllegalArgumentException("Invalid position: " + row + "," + col);
    }

    public Cell getCell(Position position) {
        return getCell(position.getRow(), position.getColumn());
    }

    // ========== Setters (Package-private for MinesweeperGridPlay) ==========
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void incrementRevealedCells() {
        this.revealedCells++;
    }

    public void resetRevealedCells() {
        this.revealedCells = 0;
    }

    public void addMinePosition(Position position) {
        this.minePositions.add(position);
    }

    public void clearMinePositions() {
        this.minePositions.clear();
    }

    public Set<Position> getMinePositions() {
        return new HashSet<>(minePositions); // Return defensive copy
    }

    // ========== Validation Methods ==========
    /**
     * Check if position is within grid bounds
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Convert letter-number notation (e.g., "A1") to Position
     * A1 = (0,0), B1 = (1,0), A2 = (0,1), etc.
     */
    public Position parsePosition(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid input format");
        }

        char rowChar = Character.toUpperCase(input.charAt(0));
        String colStr = input.substring(1);

        try {
            int row = rowChar - 'A';
            int col = Integer.parseInt(colStr) - 1;

            if (!isValidPosition(row, col)) {
                throw new IllegalArgumentException("Position out of bounds");
            }

            return new Position(row, col);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid column number: " + colStr);
        }
    }

    /**
     * Check if all non-mine cells have been revealed (win condition)
     */
    public boolean isGameWon() {
        int totalCells = size * size;
        int expectedRevealedCells = totalCells - totalMines;
        return revealedCells == expectedRevealedCells;
    }

    /**
     * Get all adjacent positions for a given cell
     */
    public List<Position> getAdjacentPositions(int centerRow, int centerCol) {
        List<Position> adjacentPositions = new ArrayList<>();

        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                // Skip the center cell itself
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }

                int adjacentRow = centerRow + deltaRow;
                int adjacentCol = centerCol + deltaCol;

                if (isValidPosition(adjacentRow, adjacentCol)) {
                    adjacentPositions.add(new Position(adjacentRow, adjacentCol));
                }
            }
        }

        return adjacentPositions;
    }

}
