//Created by Prem Poddar on the 19/08/2023

package com.chess;

import com.chess.board.BoardUtils;
import com.chess.pieces.PieceUtils;

public enum Alliance {
    WHITE{
        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public int getOpponentDirection() {
            return -1;
        }

        @Override
        public int alliancePiecesId() {
            return PieceUtils.WHITE;
        }

        @Override
        public boolean isPawnPromotionSquare(final int position) {return BoardUtils.FIRST_RANK[position];}

        @Override
        public String allianceLetter(){return "w";}

        @Override
        public String toString(){
            return "White";
        }
    },
    BLACK{
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getOpponentDirection() {
            return 1;
        }

        @Override
        public int alliancePiecesId() {
            return PieceUtils.BLACK;
        }
        @Override
        public boolean isPawnPromotionSquare(final int position) {return BoardUtils.EIGHTH_RANK[position];}

        @Override
        public String allianceLetter(){return "b";}

        @Override
        public String toString(){
            return "Black";
        }
    };

    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract int getDirection();
    public abstract int getOpponentDirection();
    public abstract int alliancePiecesId();
    public abstract boolean isPawnPromotionSquare(final int position);
    public abstract String allianceLetter();
}
