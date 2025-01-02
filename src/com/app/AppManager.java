package com.app;

import com.chess.board.BoardUtils;
import com.chess.gui.ChessBoardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppManager {
    public static final String BOARDS = "graphics/boards/";
    public static final String PIECES = "graphics/pieces/";
    private static final BufferedImage emptyImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
    static {
        emptyImage.setRGB(0, 0, 0x00000000);
    }
    public static final ImageIcon emptyIcon = new ImageIcon(emptyImage);
    public static String boardName = "wood4.jpg";
    public static String piecesName = "alpha/";
    public static double piecesScalingFactor = 1;
    public static BufferedImage boardImage;

    static {
        try {
            boardImage = ImageIO.read(new File(BOARDS+boardName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon[] icons = new ImageIcon[BoardUtils.NUM_TILES];

    static {
        int x = 0;
        int y = 0;
        BufferedImage subImage;
        Image scaledImage;
        for(int i = 0; i < icons.length; i++){
            subImage = boardImage.getSubimage(x, y, ChessBoardPanel.TILE_DIMENSION.width, ChessBoardPanel.TILE_DIMENSION.height);
            scaledImage = subImage.getScaledInstance((int) (ChessBoardPanel.TILE_DIMENSION.width*ChessBoardPanel.SCALING_FACTOR), (int) (ChessBoardPanel.TILE_DIMENSION.height*ChessBoardPanel.SCALING_FACTOR), Image.SCALE_SMOOTH);
            icons[i] = new ImageIcon(scaledImage);
            x += ChessBoardPanel.TILE_DIMENSION.width;
            if(x == boardImage.getWidth()){
                x = 0;
                y += ChessBoardPanel.TILE_DIMENSION.height;
            }
        }
    }
//    static {
//        int tileWidth = ChessBoardPanel.TILE_DIMENSION.width;
//        int tileHeight = ChessBoardPanel.TILE_DIMENSION.height;
//
//        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
//            // Convert linear index to chess board coordinates
//            int row = i / BoardUtils.NUM_TILES_PER_ROW;  // 0-7
//            int col = i % BoardUtils.NUM_TILES_PER_ROW;  // 0-7
//
//            // Calculate x and y coordinates in the image
//            int x = col * tileWidth;
//            int y = row * tileHeight;
//
//            BufferedImage subImage = boardImage.getSubimage(x, y, tileWidth, tileHeight);
//            Image scaledImage = subImage.getScaledInstance(
//                    (int)(tileWidth * ChessBoardPanel.SCALING_FACTOR),
//                    (int)(tileHeight * ChessBoardPanel.SCALING_FACTOR),
//                    Image.SCALE_SMOOTH
//            );
//            icons[i] = new ImageIcon(scaledImage);
//        }
//    }
}
