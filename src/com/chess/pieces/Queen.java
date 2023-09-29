package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.BoardUtils;
import com.chess.board.Tile;
import com.chess.moves.Move;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Queen extends Piece{
    private final static int[] CANDIDATE_MOVE_VECTOR = {-9, -8, -7, -1, 1, 7, 8, 9};
    public Queen(final Alliance alliance, final int piecePosition){
        super(PieceType.QUEEN, alliance, piecePosition);
    }
    @Override
    public String toString() {
        return alliance.isWhite() ? PieceType.QUEEN.toString().toUpperCase() : PieceType.QUEEN.toString().toLowerCase();
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

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
