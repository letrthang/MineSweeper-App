package test;

import dto.*;
import gamePlay.MinesweeperGridPlay;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for MinesweeperGridPlay
 */
public class MinesweeperGridPlayTest {

    private MinesweeperGridPlay game;
    private static final int TEST_GRID_SIZE = 4;
    private static final int TEST_MINE_COUNT = 3;

    @BeforeEach
    void setUp() {
        game = new MinesweeperGridPlay(TEST_GRID_SIZE, TEST_MINE_COUNT);
    }

    @Test
    @DisplayName("Should create game with correct grid size and mine count")
    void testGameInitialization() {
        assertEquals(TEST_GRID_SIZE, game.getSize());
        assertEquals(TEST_MINE_COUNT, game.getTotalMines());
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    @Test
    @DisplayName("Should place exact number of mines")
    void testMinePlacementCount() {
        int actualMines = 0;
        for (int row = 0; row < TEST_GRID_SIZE; row++) {
            for (int col = 0; col < TEST_GRID_SIZE; col++) {
                if (game.getCell(row, col).hasMine()) {
                    actualMines++;
                }
            }
        }
        assertEquals(TEST_MINE_COUNT, actualMines);
    }

    @Test
    @DisplayName("Should reveal safe cell successfully")
    void testRevealSafeCell() {
        Position safePosition = findSafeCell();
        assertNotNull(safePosition, "Should find at least one safe cell");

        RevealResult result = game.revealCell(safePosition);

        assertTrue(result.isSuccess());
        assertEquals(RevealResultType.NORMAL_REVEAL, result.getType());
        assertTrue(game.getCell(safePosition).isRevealed());
    }

    @Test
    @DisplayName("Should handle mine hit correctly")
    void testMineHit() {
        Position minePosition = game.getMinePositions().iterator().next();

        RevealResult result = game.revealCell(minePosition);

        assertFalse(result.isSuccess());
        assertEquals(RevealResultType.MINE_HIT, result.getType());
        assertEquals(GameState.LOST, game.getGameState());
    }

    @Test
    @DisplayName("Should parse position strings correctly")
    void testPositionParsing() {
        Position pos1 = game.parsePosition("A1");
        assertEquals(0, pos1.getRow());
        assertEquals(0, pos1.getColumn());

        Position pos2 = game.parsePosition("D4");
        assertEquals(3, pos2.getRow());
        assertEquals(3, pos2.getColumn());
    }

    @Test
    @DisplayName("Should throw exception for invalid positions")
    void testInvalidPositionException() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.revealCell(-1, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.parsePosition("Z1");
        });
    }

    @Test
    @DisplayName("Should reset game correctly")
    void testGameReset() {
        Position safePosition = findSafeCell();
        game.revealCell(safePosition);

        game.resetGame();

        assertEquals(GameState.PLAYING, game.getGameState());
        assertFalse(game.getCell(safePosition).isRevealed());
    }

    // Helper method
    private Position findSafeCell() {
        for (int row = 0; row < TEST_GRID_SIZE; row++) {
            for (int col = 0; col < TEST_GRID_SIZE; col++) {
                if (!game.getCell(row, col).hasMine()) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    @AfterEach
    void tearDown() {
        game = null;
    }
}