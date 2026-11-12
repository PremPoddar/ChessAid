# ChessAid

A desktop chess board, written in Java.

On launch it asks for a position in FEN, sets the board up from it, and plays from there: type each move into the box under the board, and the board updates with a label showing whose turn it is.

## Run it

Needs a JDK (8 or newer) on your path.

```sh
javac -d out $(find src -name "*.java")
java -cp out com.chess.ChessAid
```

## Project layout

- `src/com/chess` — the game itself; `ChessAid` is the entry point
- `src/com/chess/board` — the board and its tiles
- `src/com/chess/pieces` — each piece and the moves it can make
- `src/com/chess/moves` — the move types the game works with
- `src/com/chess/player` — the two players and their legal moves
- `src/com/chess/gui` — the window, the board panel and the FEN loader
- `src/com/app` — app plumbing
- `graphics/boards` — the board themes
