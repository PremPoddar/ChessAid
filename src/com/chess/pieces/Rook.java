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

public class Rook extends Piece{

    private static final int[] CANDIDATE_MOVE_VECTOR = {-8, -1, 1, 8};
    public Rook(final Alliance alliance, final int piecePosition){
        super(PieceType.ROOK, alliance, piecePosition);
    }

    @Override
    public String toString() {
        return alliance.isWhite() ? PieceType.ROOK.toString().toUpperCase() : PieceType.ROOK.toString().toLowerCase();

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

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.get(candidateDestinationCoordinate);

                    if (!candidateDestinationTile.tileIsOccupied()) {
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
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(int currentPosition, int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && candidateOffset == -1;
    }

    private boolean isEightColumnExclusion(int currentPosition, int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && candidateOffset == 1;
    }

}
