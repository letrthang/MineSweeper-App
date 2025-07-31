package gamePlay;

import dto.*;

import java.util.*;

public class MinesweeperGridPlay extends MinesweeperGrid {
    private Random random;

    public MinesweeperGridPlay(int size, int totalMines) {
        super(size, totalMines);
        this.random = new Random();

        initializeGame();
    }

    /**
     * Initialize the game with mines and adjacent counts
     */
    private void initializeGame() {
        placeMines();
        calculateAdjacentMineCounts();
    }

    // ========== Mine Placement Logic ==========
    /**
     * Randomly place mines on the grid
     * Ensures no duplicate mine positions
     */
    private void placeMines() {
        Set<Position> placedMines = new HashSet<>();

        while (placedMines.size() < getTotalMines()) {
            int row = random.nextInt(getSize());
            int col = random.nextInt(getSize());
            Position position = new Position(row, col);

            if (!placedMines.contains(position)) {
                placedMines.add(position);
                getCell(row, col).setHasMine(true);
                addMinePosition(position);
            }
        }
    }

    /**
     * Calculate and set adjacent mine counts for all cells
     */
    private void calculateAdjacentMineCounts() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                if (!getCell(row, col).hasMine()) {
                    int count = countAdjacentMines(row, col);
                    getCell(row, col).setAdjacentMineCount(count);
                }
            }
        }
    }

    /**
     * Count mines in the 8 adjacent cells (3x3 area around the cell)
     */
    private int countAdjacentMines(int centerRow, int centerCol) {
        int count = 0;

        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                // Skip the center cell itself
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }

                int adjacentRow = centerRow + deltaRow;
                int adjacentCol = centerCol + deltaCol;

                if (isValidPosition(adjacentRow, adjacentCol) &&
                        getCell(adjacentRow, adjacentCol).hasMine()) {
                    count++;
                }
            }
        }

        return count;
    }

    // ========== Cell Reveal Logic ==========
    /**
     * Reveal a cell at the specified position
     */
    public RevealResult revealCell(Position position) {
        return revealCell(position.getRow(), position.getColumn());
    }

    /**
     * Reveal a cell at the specified coordinates
     * Handles mine detection, auto-reveal, and win condition checking
     */
    public RevealResult revealCell(int row, int col) {
        // Validate position
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid position: " + row + "," + col);
        }

        Cell cell = getCell(row, col);

        // If cell is already revealed, do nothing
        if (cell.isRevealed()) {
            return new RevealResult(RevealResultType.ALREADY_REVEALED,
                    cell.getAdjacentMineCount(),
                    Collections.singletonList(new Position(row, col)));
        }

        // Reveal the cell
        cell.setRevealed(true);
        incrementRevealedCells();

        // Check if it's a mine
        if (cell.hasMine()) {
            setGameState(GameState.LOST);
            return new RevealResult(RevealResultType.MINE_HIT,
                    0,
                    Collections.singletonList(new Position(row, col)));
        }

        // Get the mine count for this cell
        int mineCount = cell.getAdjacentMineCount();
        List<Position> revealedPositions = new ArrayList<>();
        revealedPositions.add(new Position(row, col));

        // If no adjacent mines, auto-reveal adjacent cells
        if (mineCount == 0) {
            List<Position> autoRevealed = autoRevealAdjacentCells(row, col);
            revealedPositions.addAll(autoRevealed);
        }

        // Check win condition
        if (isGameWon()) {
            setGameState(GameState.WON);
            return new RevealResult(RevealResultType.GAME_WON,
                    mineCount,
                    revealedPositions);
        }

        return new RevealResult(RevealResultType.NORMAL_REVEAL,
                mineCount,
                revealedPositions);
    }

    /**
     * Auto-reveal adjacent cells when a cell with 0 adjacent mines is revealed
     */
    private List<Position> autoRevealAdjacentCells(int startRow, int startCol) {
        List<Position> revealedPositions = new ArrayList<>();
        Queue<Position> toProcess = new LinkedList<>();
        Set<Position> processed = new HashSet<>();

        // Start with the initial position's adjacent cells
        List<Position> initialAdjacent = getAdjacentPositions(startRow, startCol);
        for (Position pos : initialAdjacent) {
            if (!processed.contains(pos)) {
                toProcess.offer(pos);
                processed.add(pos);
            }
        }

        while (!toProcess.isEmpty()) {
            Position current = toProcess.poll();
            int row = current.getRow();
            int col = current.getColumn();

            Cell cell = getCell(row, col);

            // Skip if already revealed or is a mine
            if (cell.isRevealed() || cell.hasMine()) {
                continue;
            }

            // Reveal this cell
            cell.setRevealed(true);
            incrementRevealedCells();
            revealedPositions.add(current);

            // If this cell also has 0 adjacent mines, add its neighbors to the queue
            if (cell.getAdjacentMineCount() == 0) {
                List<Position> neighbors = getAdjacentPositions(row, col);
                for (Position neighbor : neighbors) {
                    if (!processed.contains(neighbor)) {
                        toProcess.offer(neighbor);
                        processed.add(neighbor);
                    }
                }
            }
        }

        return revealedPositions;
    }

    // ========== Game Management ==========
    /**
     * Reset the game with new mine placement
     */
    public void resetGame() {
        // Clear existing state
        clearMinePositions();
        resetRevealedCells();
        setGameState(GameState.PLAYING);

        // Reset all cells
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                Cell cell = getCell(row, col);
                cell.setHasMine(false);
                cell.setRevealed(false);
                cell.setAdjacentMineCount(0);
            }
        }

        // Reinitialize game
        initializeGame();
    }
}