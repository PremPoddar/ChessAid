package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TileButton extends JButton {
    private final ImageIcon backgroundImageIcon;
    public TileButton(final ImageIcon backgroundImageIcon) {
        this.backgroundImageIcon = backgroundImageIcon;
        this.setIcon(backgroundImageIcon);
        this.setVisible(true);
    }

    public void setOverlayIcon(final ImageIcon pieceImageIcon){
        final Image backgroundImage = backgroundImageIcon.getImage();
        final Image overlayPieceImage = pieceImageIcon.getImage();

        BufferedImage combinedImage = new BufferedImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = combinedImage.createGraphics();

        graphics2D.drawImage(backgroundImage, 0, 0, null);

        final int overlayX = (backgroundImage.getWidth(null) - overlayPieceImage.getWidth(null))/2;
        final int overlayY = (backgroundImage.getHeight(null) - overlayPieceImage.getHeight(null))/2;

        graphics2D.drawImage(overlayPieceImage, overlayX, overlayY, null);
        graphics2D.dispose();

        ImageIcon icon = new ImageIcon(combinedImage);
        this.setIcon(icon);
    }
}