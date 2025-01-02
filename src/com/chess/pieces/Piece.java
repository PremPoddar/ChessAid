//Created by Prem Poddar on the 19/08/2023

package com.chess.pieces;

import com.app.AppManager;
import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Tile;
import com.chess.moves.Move;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final Alliance alliance;
    protected int piecePosition;
    protected final int pieceId;
    protected boolean isFirstMove;
    public Piece(final PieceType pieceType, final Alliance alliance, final int piecePosition) {
        this.pieceType = pieceType;
        this.alliance = alliance;
        this.piecePosition = piecePosition;
        pieceId = alliance.alliancePiecesId() + pieceType.rawPieceId();
        isFirstMove = true;
    }

    public int getPieceId() {
        return pieceId;
    }
    public Alliance getAlliance(){return alliance;}
    public int getPiecePosition() {
        return piecePosition;
    }

    public abstract Collection<Move> calculateLegalMoves(final List<Tile> board);
    public boolean isPawn(){return false;}
    public boolean isKing(){return false;}
    public boolean isNull(){return this.pieceId == PieceUtils.NONE;}
    public void setToNotFirstMove(){isFirstMove = false;}
    public PieceType getPieceType(){return pieceType;}
    public void setPiecePosition(final int piecePosition){this.piecePosition = piecePosition;}
    public ImageIcon getIcon(){
        Image scaledImage;
        try {
            BufferedImage pieceImage = ImageIO.read(new File(AppManager.PIECES+AppManager.piecesName+alliance.allianceLetter()+pieceType.pieceName+".png"));
            scaledImage = pieceImage.getScaledInstance((int)(pieceImage.getWidth()*AppManager.piecesScalingFactor), (int)(pieceImage.getHeight()*AppManager.piecesScalingFactor), Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageIcon(scaledImage);
    }
    public abstract String toString();
    public enum PieceType {
        PAWN("P") {
            @Override
            public int rawPieceId() {
                return PieceUtils.PAWN;
            }
        },
        BISHOP("B") {
            @Override
            public int rawPieceId() {
                return PieceUtils.BISHOP;
            }
        },
        KNIGHT("N") {
            @Override
            public int rawPieceId() {
                return PieceUtils.KNIGHT;
            }
        },
        ROOK("R") {
            @Override
            public int rawPieceId() {
                return PieceUtils.ROOK;
            }
        },
        QUEEN("Q") {
            @Override
            public int rawPieceId() {
                return PieceUtils.QUEEN;
            }
        },
        KING("K") {
            @Override
            public int rawPieceId() {
                return PieceUtils.KING;
            }
        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        public abstract int rawPieceId();

        @Override
        public String toString() {
            return pieceName;
        }

        public static PieceType getPieceTypeFromFenChar(final char c) {
            return switch (c) {
                case 'P', 'p' -> PieceType.PAWN;
                case 'B', 'b' -> PieceType.BISHOP;
                case 'N', 'n' -> PieceType.KNIGHT;
                case 'R', 'r' -> PieceType.ROOK;
                case 'Q', 'q' -> PieceType.QUEEN;
                case 'K', 'k' -> PieceType.KING;
                default -> throw new IllegalArgumentException("Invalid FEN character: " + c);
            };
        }
    }

}