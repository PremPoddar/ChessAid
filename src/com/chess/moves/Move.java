//Created by Prem Poddar on the 19/08/2023

package com.chess.moves;

import com.chess.board.BoardUtils;

public class Move {
     private final int sourceTileCoordinate;
     private final int destinationTileCoordinate;
     private boolean isValidMove;
     private boolean isEnPassantMove;
     private int enPassantMoveCoordinate;
     private boolean isCastleMove;
     private int castlingRookSourceCoordinate;
     private int castlingRookDestinationCoordinate;

    public Move(final int sourceTileCoordinate, final int destinationTileCoordinate) {
        this.sourceTileCoordinate = sourceTileCoordinate;
        this.destinationTileCoordinate = destinationTileCoordinate;
        isValidMove = false;
        isEnPassantMove = false;
        enPassantMoveCoordinate = Integer.MIN_VALUE;
        isCastleMove = false;
        castlingRookSourceCoordinate = Integer.MIN_VALUE;
        castlingRookDestinationCoordinate = Integer.MIN_VALUE;
    }
    public int getSourceTileCoordinate() {
        return sourceTileCoordinate;
    }

    public int getDestinationTileCoordinate() {
        return destinationTileCoordinate;
    }
    public void setToValidMove(){isValidMove = true;}
    public void setToCastleMove(){isCastleMove = true;}
    public void setToEnPassantMove(){isEnPassantMove = true;}
    public void setCastlingRookSourceCoordinate(final int castlingRookSourceCoordinate){this.castlingRookSourceCoordinate = castlingRookSourceCoordinate;}
    public void setCastlingRookDestinationCoordinate(final int castlingRookDestinationCoordinate){this.castlingRookDestinationCoordinate = castlingRookDestinationCoordinate;}
    public void setEnPassantMoveCoordinate(final int enPassantMoveCoordinate){this.enPassantMoveCoordinate = enPassantMoveCoordinate;}
    public boolean isValidMove() {return isValidMove;}
    public boolean isEnPassantMove(){return isEnPassantMove;}
    public int getEnPassantMoveCoordinate(){return enPassantMoveCoordinate;}
    public boolean isCastleMove(){return isCastleMove;}
    public int getCastlingRookSourceCoordinate(){return castlingRookSourceCoordinate;}
    public int getCastlingRookDestinationCoordinate(){return castlingRookDestinationCoordinate;}
    public boolean isEnPassantPawn(){
        //This is a dirty way to check and relies on the fact that you checked that the move is a pawn move before checking if it is an EnPasant Move
        return sourceTileCoordinate+16 == destinationTileCoordinate || sourceTileCoordinate-16 == destinationTileCoordinate;
    }
    @Override
    public boolean equals(final Object o){
        if(o == null){
            return false;
        }else {
            Move move = (Move)o;
            return this.sourceTileCoordinate == move.getSourceTileCoordinate() && this.destinationTileCoordinate == move.getDestinationTileCoordinate();
        }
    }
    @Override
    public String toString(){
        return BoardUtils.getPositionAtCoordinate(sourceTileCoordinate)+BoardUtils.getPositionAtCoordinate(destinationTileCoordinate);
    }
}
