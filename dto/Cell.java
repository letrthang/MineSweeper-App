package dto;

/**
 * Represents a single cell in the minesweeper grid
 */
public class Cell {
    private boolean hasMine;           // Does this cell contain a mine?
    private boolean isRevealed;        // Has this cell been uncovered?
    private int adjacentMineCount;     // Number of mines in adjacent cells

    public Cell() {
        this.hasMine = false;
        this.isRevealed = false;
        this.adjacentMineCount = 0;
    }

    // Getters and setters
    public boolean hasMine() { return hasMine; }
    public void setHasMine(boolean hasMine) { this.hasMine = hasMine; }

    public boolean isRevealed() { return isRevealed; }
    public void setRevealed(boolean revealed) { this.isRevealed = revealed; }

    public int getAdjacentMineCount() { return adjacentMineCount; }
    public void setAdjacentMineCount(int count) { this.adjacentMineCount = count; }
}