package dto;

/**
 * Types of reveal results
 */
public enum RevealResultType {
    NORMAL_REVEAL,      // Regular cell revealed
    MINE_HIT,           // Mine was hit - game over
    GAME_WON,           // Last cell revealed - game won
    ALREADY_REVEALED    // Cell was already revealed
}
