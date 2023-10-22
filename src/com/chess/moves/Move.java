//Created by Prem Poddar on the 19/08/2023

package com.chess.moves;

import com.chess.board.BoardUtils;

public class Move {
     private final int sourceTileCoordinate;
     private final int destinationTileCoordinate;

    public Move(final int sourceTileCoordinate, final int destinationTileCoordinate) {
        this.sourceTileCoordinate = sourceTileCoordinate;
        this.destinationTileCoordinate = destinationTileCoordinate;
    }

    public int getSourceTileCoordinate() {
        return sourceTileCoordinate;
    }

    public int getDestinationTileCoordinate() {
        return destinationTileCoordinate;
    }

    public boolean isEnPassant(){
        //This is a dirty way to check and relies on the fact that you checked that the move is a pawn move before checking if it is an EnPasant Move
        return sourceTileCoordinate+16 == destinationTileCoordinate || sourceTileCoordinate-16 == destinationTileCoordinate;
    }

    @Override
    public boolean equals(Object o){
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
