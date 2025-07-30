package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of a cell reveal operation
 */
public class RevealResult {
    private final RevealResultType type;
    private final int adjacentMineCount;
    private final List<Position> revealedPositions;

    public RevealResult(RevealResultType type, int adjacentMineCount, List<Position> revealedPositions) {
        this.type = type;
        this.adjacentMineCount = adjacentMineCount;
        this.revealedPositions = new ArrayList<>(revealedPositions);
    }

    public RevealResultType getType() { return type; }
    public int getAdjacentMineCount() { return adjacentMineCount; }
    public List<Position> getRevealedPositions() { return new ArrayList<>(revealedPositions); }

    public boolean isGameOver() {
        return type == RevealResultType.MINE_HIT || type == RevealResultType.GAME_WON;
    }

    public boolean isSuccess() {
        return type != RevealResultType.MINE_HIT;
    }
}
