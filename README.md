# ChessAid

A desktop chess board, written in Java.

On launch it asks for a position in FEN, sets the board up from it, and plays from there: type each move into the box under the board, and the board updates with a label showing whose turn it is.

## Run it

Needs a JDK (8 or newer) on your path.

```sh
javac -d out $(find src -name "*.java")
java -cp out com.chess.ChessAid
```
