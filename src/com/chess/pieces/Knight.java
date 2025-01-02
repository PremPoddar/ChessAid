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

public class Knight extends Piece{
    private static final int[] CANDIDATE_OFFSET = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Knight(final Alliance alliance, final int piecePosition){
        super(PieceType.KNIGHT, alliance, piecePosition);
    }

    @Override
    public String toString() {
        return alliance.isWhite() ? PieceType.KNIGHT.toString().toUpperCase() : PieceType.KNIGHT.toString().toLowerCase();
    }

    @Override
    public Collection<Move> calculateLegalMoves(final List<Tile> board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_OFFSET) {

            final int candidateDestinationCoordinate = this.getPiecePosition() + currentCandidateOffset;

            //Check 1
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                //Check 2
                if (isFirstColumnExclusion(this.getPiecePosition(), currentCandidateOffset) ||
                        isSecondColumnExclusion(this.getPiecePosition(), currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.getPiecePosition(), currentCandidateOffset) ||
                        isEightColumnExclusion(this.getPiecePosition(), currentCandidateOffset)) {
                    continue;
                }

                final Tile candidateDestinationTile = board.get(candidateDestinationCoordinate);
                //Check 3
                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPieceOnTile();
                    final Alliance pieceAllianceOfDestination = pieceAtDestination.getAlliance();

                    //Check 4
                    if (this.getAlliance() != pieceAllianceOfDestination) {
                        legalMoves.add(new Move(piecePosition, candidateDestinationCoordinate));
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 17 || candidateOffset == 10);
    }

}
