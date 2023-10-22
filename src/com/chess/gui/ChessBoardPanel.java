package com.chess.gui;

import com.app.AppManager;
import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.moves.Move;
import com.chess.moves.MoveInfo;
import com.chess.player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    public static Dimension TILE_DIMENSION = new Dimension(128, 128);
    public static double SCALING_FACTOR = 1/1.5;
    TileButton[] tiles;
    public Board board;
    private String partialMove = "";

    ChessBoardPanel(){
        GridLayout gridLayout = new GridLayout(BoardUtils.NUM_TILES_PER_ROW, BoardUtils.NUM_TILES_PER_ROW);
        gridLayout.setHgap(0);
        gridLayout.setVgap(0);
        this.setLayout(gridLayout);
        tiles = new TileButton[BoardUtils.NUM_TILES];
        initializeTiles();
        this.setVisible(true);
    }
    public void makeMove(final Move move){
        MoveInfo moveInfo = board.makeMove(move);
        if (moveInfo.isValidMove) {
            tiles[moveInfo.sourceCoordinate].setOverlayIcon(AppManager.emptyIcon);
            tiles[moveInfo.destinationCoordinate].setOverlayIcon(board.tiles.get(moveInfo.destinationCoordinate).getPieceOnTile().getIcon());
            if (moveInfo.isEnPassantMove) {
                tiles[moveInfo.enPassantMoveCoordinate].setOverlayIcon(AppManager.emptyIcon);
            }else if (moveInfo.isCastleMove) {
                tiles[moveInfo.castlingRookSourceCoordinate].setOverlayIcon(AppManager.emptyIcon);
                tiles[moveInfo.castlingRookDestinationCoordinate].setOverlayIcon(board.tiles.get(moveInfo.castlingRookDestinationCoordinate).getPieceOnTile().getIcon());
            }
        }
    }
    private void initializeTiles(){

        for(int i = tiles.length-1; i >= 0; i--){
            tiles[i] = new TileButton(AppManager.icons[i]);
            tiles[i].setBorder(new EmptyBorder(0,0,0,0));
            tiles[i].setMargin(new Insets(0, 0, 0, 0));
            tiles[i].setBorderPainted(false);
            int finalI = i;
            tiles[i].addActionListener(e-> {
                partialMove += BoardUtils.getPositionAtCoordinate(finalI);
                if(partialMove.length() == 4){
                    makeMove(new Move(BoardUtils.getCoordinateAtPosition(partialMove.substring(0, 2)), BoardUtils.getCoordinateAtPosition(partialMove.substring(2, 4))));
                    Window.updateAllianceLabel(board.getCurrentPlayer().getAlliance().toString());
                    partialMove = "";
                }
            });
        }
        for (int i = 0; i < tiles.length; i++) {
            this.add(tiles[i+(56-(i/8)*16)]);
        }
    }
    public void updateBoard(final String fen){
        board = new Board(fen);
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            if(board.tiles.get(i).tileIsOccupied()){
                ImageIcon overlayIcon = board.tiles.get(i).getPieceOnTile().getIcon();
                tiles[i].setOverlayIcon(overlayIcon);
            }
        }
    }
}
