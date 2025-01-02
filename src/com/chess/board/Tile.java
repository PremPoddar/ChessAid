//Created by Prem Poddar on the 19/08/2023

package com.chess.board;

import com.chess.pieces.Piece;
import com.chess.pieces.PieceUtils;

public class Tile {
    private final int tileCoordinate;
    private Piece pieceOnTile;
    private int pieceIdOnTile;

    public Tile(final int tileCoordinate, final Piece pieceOnTile, final int pieceIdOnTile) {
        this.tileCoordinate = tileCoordinate;
        this.pieceOnTile = pieceOnTile;
        this.pieceIdOnTile = pieceIdOnTile;
    }
    public int getTileCoordinate() {
        return tileCoordinate;
    }
    public Piece getPieceOnTile() {
        return pieceOnTile;
    }
    public void setPieceOnTile(final Piece pieceOnTile) {
        this.pieceOnTile = pieceOnTile;
        pieceIdOnTile = pieceOnTile.getPieceId();
    }
    public void setNullPieceOnTile(){
        pieceOnTile = null;
        pieceIdOnTile = PieceUtils.NONE;
    }
    public boolean isOccupied(){
        return pieceIdOnTile!=0;
    }
    @Override
    public String toString(){
        return pieceIdOnTile!=0 ? pieceOnTile.toString() : " ";
    }
    @Override
    public int hashCode(){
        int hashCode = 17;
        hashCode = 31 * hashCode + tileCoordinate;
        hashCode = 31 * hashCode + pieceIdOnTile;

        return hashCode;
    }
}
