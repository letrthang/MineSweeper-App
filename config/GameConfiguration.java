package config;

/**
 * Game configuration and constraints
 */
public class GameConfiguration {
    public static final double MAX_MINE_PERCENTAGE = 0.35; // 35% maximum

    /**
     * Validate if the number of mines is acceptable for given grid size
     */
    public static boolean isValidMineCount(int gridSize, int mineCount) {
        int totalCells = gridSize * gridSize;
        int maxMines = (int) (totalCells * MAX_MINE_PERCENTAGE);
        return mineCount > 0 && mineCount <= maxMines;
    }

    /**
     * Get maximum allowed mines for grid size
     */
    public static int getMaxMines(int gridSize) {
        int totalCells = gridSize * gridSize;
        return (int) (totalCells * MAX_MINE_PERCENTAGE);
    }
}