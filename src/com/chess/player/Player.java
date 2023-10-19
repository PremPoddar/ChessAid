//Created by Prem Poddar on the 5/09/2023

package com.chess.player;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Tile;
import com.chess.moves.Move;
import com.chess.pieces.Piece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
    private final Alliance alliance;
    boolean kingIsCastled;
    public Player(final Alliance alliance) {
        this.alliance = alliance;
        kingIsCastled = false;
    }

    public Alliance getAlliance() {
        return alliance;
    }
    public Collection<Move>getAllLegalMoves(final List<Tile> board){
        Piece pieceOnTile;
        Collection<Move> legalMoves = new ArrayList<>();
        for(Tile tile : board){
            if(tile.getPieceOnTile() != null){
                pieceOnTile = tile.getPieceOnTile();
//                System.out.println(this.alliance + " " + pieceOnTile + " on " + BoardUtils.getPositionAtCoordinate(tile.getTileCoordinate()) + ": ");
//                for(Move move : pieceOnTile.calculateLegalMoves(board)){
//                    if (pieceOnTile.getPieceType() == Piece.PieceType.BISHOP){
//                        System.out.println(move);
//                    }
//                }
                legalMoves.addAll(pieceOnTile.calculateLegalMoves(board));

            }
        }
        //System.out.println("--------------------------");
        return legalMoves;
    }

    @Override
    public String toString(){
        return alliance == Alliance.WHITE ? "White Player" : "Black Player";
    }

}
