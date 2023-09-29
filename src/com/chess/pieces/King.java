//Created by Prem Poddar on the 19/08/2023

package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Tile;
import com.chess.moves.Move;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece{
    private static final int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(final Alliance alliance, final int piecePosition){
        super(PieceType.KING, alliance, piecePosition);
    }

    @Override
    public String toString() {
        return alliance.isWhite() ? PieceType.KING.toString().toUpperCase() : PieceType.KING.toString().toLowerCase();
    }

    @Override
    public Collection<Move> calculateLegalMoves(final List<Tile> board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.getPiecePosition() + currentCandidateOffset;

            if (isFirstColumnExclusion(this.getPiecePosition(), currentCandidateOffset) ||
                    isEightColumnExclusion(this.getPiecePosition(), currentCandidateOffset)) {
                continue;
            }

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.get(candidateDestinationCoordinate);

                if (!candidateDestinationTile.tileIsOccupied()) {
                    legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPieceOnTile();
                    final Alliance pieceAlliance = pieceAtDestination.getAlliance();

                    if (this.getAlliance() != pieceAlliance) {
                        legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                    }
                }
            }
        }
        if(isFirstMove) {
            if (!board.get(piecePosition + 1).tileIsOccupied() && !board.get(piecePosition+2).tileIsOccupied()) {
                if(!Board.ATTACKED_TILES[piecePosition+1] && !Board.ATTACKED_TILES[piecePosition+2]){
                    legalMoves.add(new Move(piecePosition, piecePosition + 2));
                }
            } else if (!board.get(piecePosition - 1).tileIsOccupied() && !board.get(piecePosition-2).tileIsOccupied() && !board.get(piecePosition-3).tileIsOccupied()) {
                if(!Board.ATTACKED_TILES[piecePosition-1] && !Board.ATTACKED_TILES[piecePosition-2]){
                    legalMoves.add(new Move(piecePosition, piecePosition - 2));
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }

    @Override
    public boolean isKing(){return true;}
}
