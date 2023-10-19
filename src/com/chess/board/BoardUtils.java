//Created by Prem Poddar on the 5/09/2023

package com.chess.board;

import com.chess.Alliance;
import com.chess.pieces.*;
import java.util.*;

public class BoardUtils {
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    public static final boolean[] FIRST_COLUMN = initializeColumn(1);
    public static final boolean[] SECOND_COLUMN = initializeColumn(2);
    public static final boolean[] SEVENTH_COLUMN = initializeColumn(7);
    public static final boolean[] EIGHT_COLUMN = initializeColumn(8);

    public static final boolean[] EIGHTH_RANK = initializeRow(8);
    public static final boolean[] SEVENTH_RANK = initializeRow(7);
    public static final boolean[] SIXTH_RANK = initializeRow(6);
    public static final boolean[] FIFTH_RANK = initializeRow(5);
    public static final boolean[] FOURTH_RANK = initializeRow(4);
    public static final boolean[] THIRD_RANK = initializeRow(3);
    public static final boolean[] SECOND_RANK = initializeRow(2);
    public static final boolean[] FIRST_RANK = initializeRow(1);

    private static final String[] CHESS_NOTATION = initializeCHESSNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initializePosotionToCoordinateMap();
    private static boolean[] initializeRow(int rowNumber) {
        rowNumber = (8 - rowNumber) * 8;
        final boolean[] row = new boolean[NUM_TILES];

        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);

        return row;
    }
    private static boolean[] initializeColumn(int columnNumber) {
        columnNumber = columnNumber - 1;
        final boolean[] column = new boolean[NUM_TILES];

        while (columnNumber < BoardUtils.NUM_TILES) {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }

        return column;
    }
    private static String[] initializeCHESSNotation() {
        return new String[]{
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"
        };
    }

    private static Map<String, Integer> initializePosotionToCoordinateMap() {
        final Map<String, Integer> positionCoordinate = new HashMap<>();
        for(int i = 0; i < NUM_TILES; i++){
            positionCoordinate.put(CHESS_NOTATION[i], i);
        }
        return Collections.unmodifiableMap(positionCoordinate);
    }
    public static List<Tile> loadFromFen(final String fen) {
        List<Tile> tiles = new ArrayList<>();
        for(int i = 0; i < 64; i++){
            final Tile tile = new Tile(i, null, PieceUtils.NONE);
            tiles.add(tile);
        }
        final String[] fenData = fen.split(" ");
        final String positionData = fenData[0];

        int piecePosition = 56;
        int rankNumber = 0;
        for (char c : positionData.toCharArray()){

            if(rankNumber == 8){
                rankNumber = 0;
                piecePosition--;
            }
            switch (c) {
                case 'P' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Pawn(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'p' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Pawn(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'N' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Knight(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'n' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Knight(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'B' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Bishop(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'b' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Bishop(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'R' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Rook(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'r' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Rook(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'Q' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Queen(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'q' -> {
                    tiles.get(piecePosition).setPieceOnTile(new Queen(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'K' -> {
                    tiles.get(piecePosition).setPieceOnTile(new King(Alliance.WHITE, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case 'k' -> {
                    tiles.get(piecePosition).setPieceOnTile(new King(Alliance.BLACK, piecePosition));
                    piecePosition += 1;
                    rankNumber++;
                }
                case '/' -> piecePosition -= 15;
                case '8' -> {
                    piecePosition += 8;
                    rankNumber += 8;
                }
                case '7' -> {
                    piecePosition += 7;
                    rankNumber += 7;
                }
                case '6' -> {
                    piecePosition += 6;
                    rankNumber += 6;
                }
                case '5' -> {
                    piecePosition += 5;
                    rankNumber += 5;
                }
                case '4' -> {
                    piecePosition += 4;
                    rankNumber += 4;
                }
                case '3' -> {
                    piecePosition += 3;
                    rankNumber += 3;
                }
                case '2' -> {
                    piecePosition += 2;
                    rankNumber += 2;
                }
                case '1' -> {
                    piecePosition += 1;
                    rankNumber += 1;
                }
            }
        }
        return tiles;
    }
    public static String generateFenFromTiles(List<Tile> tiles) {
        StringBuilder fenBuilder = new StringBuilder();
        int emptySquareCount = 0;

        for (int i = 63; i >= 0; i--) {
            Tile tile = tiles.get(i);
            if (tile.tileIsOccupied()) {
                if (emptySquareCount > 0) {
                    fenBuilder.append(emptySquareCount);
                    emptySquareCount = 0;
                }
                fenBuilder.append(tile.getPieceOnTile());
            } else {
                emptySquareCount++;
            }

            if (i % 8 == 0) {
                if (emptySquareCount > 0) {
                    fenBuilder.append(emptySquareCount);
                    emptySquareCount = 0;
                }
                if (i != 0) {
                    fenBuilder.append("/");
                }
            }
        }

        // Add additional FEN components (castling, en passant, etc.) here if needed
        fenBuilder.append(" w KQkq - 0 1");
        return fenBuilder.toString();
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
    public static int getCoordinateAtPosition(final String position){
        return POSITION_TO_COORDINATE.get(position);
    }
    public static String getPositionAtCoordinate(int coordinate) {
        return CHESS_NOTATION[coordinate];
    }

    public static int getNumberOfMoves(final String[] fenData){
        return Integer.parseInt(fenData[fenData.length-1])-1;
    }

    public static int getNumberOfPlies(final String[] fenData){
        int numberOfMoves = getNumberOfMoves(fenData);

        int numberOfPlies = 2 * numberOfMoves;

        if(fenData[1].equals("w")){
            numberOfPlies += 1;
        }

        return numberOfPlies;
    }

    public static boolean isValidFen(final String fen){
        return true;
    }

}
