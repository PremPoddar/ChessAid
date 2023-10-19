package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TileButton extends JButton {
    private final ImageIcon mainIcon;
    public TileButton(final ImageIcon mainIcon) {
        this.mainIcon = mainIcon;
        this.setIcon(mainIcon);
        this.setVisible(true);
    }

    public void setOverlayIcon(final ImageIcon overlayIcon){
        final Image mainImage = mainIcon.getImage();
        final Image overLayImage = overlayIcon.getImage();

        BufferedImage combinedImage = new BufferedImage(mainImage.getWidth(null), mainImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = combinedImage.createGraphics();

        graphics2D.drawImage(mainImage, 0, 0, null);

        final int overlayX = (mainImage.getWidth(null) - overLayImage.getWidth(null))/2;
        final int overlayY = (mainImage.getHeight(null) - overLayImage.getHeight(null))/2;

        graphics2D.drawImage(overLayImage, overlayX, overlayY, null);
        graphics2D.dispose();

        ImageIcon icon = new ImageIcon(combinedImage);
        this.setIcon(icon);
    }
}