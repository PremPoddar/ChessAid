//Created by Prem Poddar on the 19/08/2023

package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.BoardUtils;
import com.chess.board.Tile;
import com.chess.moves.Move;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bishop extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR = {-9, -7, 7, 9};
    public Bishop(final Alliance alliance, final int piecePosition){
        super(PieceType.BISHOP, alliance, piecePosition);
    }

    @Override
    public String toString() {
        return alliance.isWhite() ? PieceType.BISHOP.toString().toUpperCase() : PieceType.BISHOP.toString().toLowerCase();
    }
    @Override
    public Collection<Move> calculateLegalMoves(final List<Tile> board) {

        final List<Move> legalMoves = new ArrayList<>();
        for (int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR) {
            int candidateDestinationCoordinate = this.getPiecePosition();

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.get(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPieceOnTile();
                    final Alliance pieceAllianceOfDestination = pieceAtDestination.getAlliance();

                    if (this.getAlliance() != pieceAllianceOfDestination) {
                        legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                    }
                    break;
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
