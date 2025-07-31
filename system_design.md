# Design Overview
## Here are the main data structure classes:

### Core Classes:

#### 1. Cell - Represents each individual grid square with:

* hasMine: Boolean indicating if cell contains a mine
* isRevealed: Boolean indicating if cell has been uncovered
* adjacentMineCount: Number of mines in surrounding cells

#### 2. Position - Immutable coordinate class for grid positions:

* Handles row/column coordinates
* Includes equals/hashCode for proper collection usage
* Provides string representation

#### 3. GameState - Enum for tracking game status

* PLAYING, WON, LOST

#### 4. GameConfiguration - Static utility class

* Validates mine count constraints (35% maximum)
* Provides configuration constants

#### 5. MinesweeperGrid - Main DTO class for game board:

* 2D array of Cell objects
* Grid size and mine count tracking
* Position validation and parsing (A1, B2, etc.)
* Game state management

#### 6. MinesweeperGridPlay - Game play Logic implementation:

* Extends MinesweeperGrid for data access
* Mine placement logic (placeMines, calculateAdjacentMineCounts)
* Cell reveal logic (revealCell, autoRevealAdjacentCells)
* Game management (resetGame)

## Key Design Benefits:

* Single Responsibility Principle (SRP): Each class has one clear responsibility
* Open/Closed Principle: MinesweeperGrid is open for extension, closed for modification
* Encapsulation: Package-private setters prevent external misuse
* Inheritance: MinesweeperGridPlay inherits data access, adds behavior


## Game logic implementation:

![Alt text](https://raw.githubusercontent.com/letrthang/MineSweeper-App/main/docs/game_sequence.png)