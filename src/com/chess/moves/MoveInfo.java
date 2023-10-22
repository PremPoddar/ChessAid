package com.chess.moves;

import com.app.AppManager;
import com.chess.pieces.Piece;

import javax.swing.*;

public class MoveInfo {
    public int sourceCoordinate;
    public int destinationCoordinate;
    public boolean isValidMove;
    public boolean isEnPassantMove;
    public int enPassantMoveCoordinate;
    public boolean isCastleMove;
    public int castlingRookSourceCoordinate;
    public int castlingRookDestinationCoordinate;

    public MoveInfo(final Move move){
        this.isValidMove = false;
        this.sourceCoordinate = move.getSourceTileCoordinate();
        this.destinationCoordinate = move.getDestinationTileCoordinate();
        this.isEnPassantMove = false;
        this.enPassantMoveCoordinate = Integer.MIN_VALUE;
        this.isCastleMove = false;
        this.castlingRookSourceCoordinate = Integer.MIN_VALUE;
        this.castlingRookDestinationCoordinate = Integer.MIN_VALUE;
    }
    public void setToValidMove(){isValidMove = true;}
    public void setToCastleMove(){isCastleMove = true;}
    public void setToEnPassantMove(){isEnPassantMove = true;}
}
