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
}
