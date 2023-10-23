//Created by Prem Poddar on the 19/08/2023

package com.chess.board;

import com.chess.Alliance;
import com.chess.moves.Move;
import com.chess.pieces.*;
import com.chess.player.Player;
import java.util.*;

import static com.chess.board.BoardUtils.isValidFen;

public class Board {
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    public List<Tile> tiles;
    private boolean gameIsOn;
    private int numberOfMoves;
    private int numberOfPlies;
    private int fiftyMoveCounter;
    public static boolean[] ATTACKED_TILES = new boolean[64];
    List<Integer> positionHashes;
    public static Pawn enPassantPawn;

    public Board(final String fen) {
        if(!isValidFen(fen)){
            //TODO: handle incorrect fen properly.
            throw new RuntimeException();
        }
        gameIsOn = true;
        String[] fenData = fen.split(" ");

        numberOfMoves = BoardUtils.getNumberOfMoves(fenData);
        numberOfPlies = BoardUtils.getNumberOfPlies(fenData);
        fiftyMoveCounter = Integer.parseInt(fenData[fenData.length-2]);
        whitePlayer = new Player(Alliance.WHITE);
        blackPlayer = new Player(Alliance.BLACK);

        if(Objects.equals(fenData[1], "w")){
            currentPlayer = whitePlayer;
        }else if(Objects.equals(fenData[1], "b")){
            currentPlayer = blackPlayer;
        }
        tiles = new ArrayList<>();
        tiles = BoardUtils.loadFromFen(fenData[0]);
        calculateAttackedSquares();
        positionHashes = new ArrayList<>();
        positionHashes.add(calculateBoardHash());
    }

    public void initializeTiles(){//Use it to create an empty board
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            final Tile tile = new Tile(i, null, PieceUtils.NONE);
            tiles.add(tile);
        }
    }
    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int row = BoardUtils.NUM_TILES_PER_ROW-1; row >= 0; row--) {
            board.append("+---+---+---+---+---+---+---+---+\n");
            for (int col = 0; col < BoardUtils.NUM_TILES_PER_ROW; col++) {
                int tileIndex = row * BoardUtils.NUM_TILES_PER_ROW + col;
                board.append("| ").append(tiles.get(tileIndex).toString()).append(" ");
            }
            board.append("| ").append(row + 1); // Append the rank number on the right
            board.append("\n");
        }
        board.append("+---+---+---+---+---+---+---+---+\n");
        board.append("  a   b   c   d   e   f   g   h");

        return board.toString();
    }
    public void makeMove(final Move move){
        Collection<Move> legalMoves = currentPlayer.getAllLegalMoves(tiles);

        if(legalMoves.contains(move)){
            move.setToValidMove();
            tiles.get(move.getSourceTileCoordinate()).getPieceOnTile().setToNotFirstMove();
            final Tile sourceTile = tiles.get(move.getSourceTileCoordinate());
            final Piece sourceTilePiece = sourceTile.getPieceOnTile();
            final Tile destinationTile = tiles.get(move.getDestinationTileCoordinate());
            final boolean destinationPieceIsNull = destinationTile.getPieceOnTile() == null;
            if (destinationPieceIsNull || (destinationTile.getPieceOnTile().getAlliance() != null && currentPlayer.getAlliance() != destinationTile.getPieceOnTile().getAlliance())) {//Checking if the source tile and destination tile have different alliances
                final boolean enPassantPawnIsNull = enPassantPawn == null;
                if(!enPassantPawnIsNull && enPassantPawn.getAlliance() == currentPlayer.getAlliance()){
                    enPassantPawn = null;
                }
                if(sourceTilePiece.isPawn() && move.isEnPassantPawn()){
                    enPassantPawn = (Pawn) sourceTilePiece;
                }
                final int sourceTileCoordinate = sourceTile.getTileCoordinate();
                final int destinationTileCoordinate = destinationTile.getTileCoordinate();
                tiles.get(sourceTileCoordinate).setNullPieceOnTile();
                sourceTilePiece.setPiecePosition(destinationTileCoordinate);
                tiles.get(destinationTileCoordinate).setPieceOnTile(sourceTilePiece);
                tiles.get(destinationTileCoordinate).getPieceOnTile().setToNotFirstMove();
                if(sourceTilePiece.isKing()){
                    if(move.getSourceTileCoordinate()+2 == move.getDestinationTileCoordinate()){
                        move.setToCastleMove();
                        tiles.get(destinationTileCoordinate+1).setNullPieceOnTile();
                        move.setCastlingRookSourceCoordinate(destinationTileCoordinate+1);
                        tiles.get(destinationTileCoordinate-1).setPieceOnTile(new Rook(currentPlayer.getAlliance(), destinationTileCoordinate-1));
                        move.setCastlingRookDestinationCoordinate(destinationTileCoordinate-1);
                        tiles.get(destinationTileCoordinate-1).getPieceOnTile().setToNotFirstMove();
                    }else if(move.getSourceTileCoordinate()-2 == move.getDestinationTileCoordinate()){
                        move.setToCastleMove();
                        tiles.get(destinationTileCoordinate-2).setNullPieceOnTile();
                        move.setCastlingRookSourceCoordinate(destinationTileCoordinate-2);
                        tiles.get(destinationTileCoordinate+1).setPieceOnTile(new Rook(currentPlayer.getAlliance(), destinationTileCoordinate+1));
                        move.setCastlingRookDestinationCoordinate(destinationTileCoordinate+1);
                        tiles.get(destinationTileCoordinate+1).getPieceOnTile().setToNotFirstMove();
                    }
                }
                if(sourceTilePiece.isPawn()){
                    if(sourceTileCoordinate+7*currentPlayer.getAlliance().getDirection() == destinationTileCoordinate || sourceTileCoordinate+9*currentPlayer.getAlliance().getDirection() == destinationTileCoordinate){
                        int capturedPawnCoordinate = destinationTileCoordinate-BoardUtils.NUM_TILES_PER_ROW*currentPlayer.getAlliance().getDirection();
                        tiles.get(capturedPawnCoordinate).setNullPieceOnTile();
                        move.setToEnPassantMove();
                        move.setEnPassantMoveCoordinate(capturedPawnCoordinate);
                    }
                }
                updateFiftyMoveCounter(move);
                positionHashes.add(calculateBoardHash());
                checkForThreeFoldRepetition();
                switchPlayer();
                calculateAttackedSquares();
            }

        }

    }
    private void switchPlayer(){
        if(currentPlayer == whitePlayer){
            numberOfPlies++;
            currentPlayer = blackPlayer;
        }else if(currentPlayer == blackPlayer){
            numberOfPlies++;
            numberOfMoves++;
            currentPlayer = whitePlayer;
        }else{
            System.out.println("Either there are more than 2 players(white and black) or there is a null player.");
            System.out.println("Board.java: switchPlayer()");
        }
    }
    private void calculateAttackedSquares(){
        Arrays.fill(ATTACKED_TILES, false);
        final Collection <Move> opponentLegalMoves = getOpponentPlayer().getAllLegalMoves(tiles);
        for(Move move : opponentLegalMoves){
            ATTACKED_TILES[move.getDestinationTileCoordinate()] = true;
        }
    }
    public static Pawn getEnPassantPawn(){return enPassantPawn;}
    private Player getOpponentPlayer(){return currentPlayer == whitePlayer ? blackPlayer : whitePlayer;}
    public boolean isGameIsOn(){return gameIsOn;}
    private int calculateBoardHash(){
        int hashCode = 1;

        for(Tile tile : tiles){
            hashCode = 31 * hashCode() + tile.hashCode();
        }
        return hashCode;
    }
    private void checkForThreeFoldRepetition(){
        final int currentHashCode = tiles.hashCode();
        int counter = 0;

        for(int hashCode : positionHashes){
            if(currentHashCode == hashCode){
                counter++;
            }
        }

        if(counter >= 3){
            offerDrawToBothPlayers();
        }
    }
    public void offerDrawToBothPlayers(){
        System.out.println("Game has ended in a draw");
        gameIsOn = false;
        gameOver("Draw");
    }
    private void updateFiftyMoveCounter(final Move move) {
        if(tiles.get(move.getSourceTileCoordinate()).tileIsOccupied()) {
            if (!tiles.get(move.getSourceTileCoordinate()).getPieceOnTile().isPawn()) {
                if (tiles.get(move.getDestinationTileCoordinate()).getPieceOnTile().isNull()) {
                    fiftyMoveCounter++;
                }
            } else {
                fiftyMoveCounter = 0;
            }
        }

        if(fiftyMoveCounter == 100){
            System.out.println("Game has ended in a draw due to 50 move rule.");
            gameOver("Draw");
        }
    }
    private void gameOver(final String conclusion){
        if(Objects.equals(conclusion, "Draw")){
            gameIsOn = false;
            System.exit(0);
        }
    }
    private void printCurrentPlayersLegalMove(){
        for (Tile tile : tiles) {
            if (tile.tileIsOccupied()) {
                if (tile.getPieceOnTile().getAlliance() == currentPlayer.getAlliance()) {
                    Piece pieceOnTile = tile.getPieceOnTile();
                    System.out.println(currentPlayer.getAlliance() + " " + pieceOnTile + " on " + BoardUtils.getPositionAtCoordinate(pieceOnTile.getPiecePosition()) + ": ");
                    for (Move move : pieceOnTile.calculateLegalMoves(tiles)) {
                        System.out.println(move);
                    }
                    System.out.println("-----------------------------");
                }
            }
        }
    }
    public Player getCurrentPlayer(){return currentPlayer;}
}
