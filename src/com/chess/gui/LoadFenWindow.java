package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadFenWindow extends JDialog {
    private final String STARTING_CHESS_POSITION_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private final JTextField fenTextField;

    LoadFenWindow(StringBuilder positionInFen){
        fenTextField = new JTextField(90);
        JButton loadFenButton = new JButton("Load fen");
        JButton defaultFenButton = new JButton("Use default fen");
        JPanel fenPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        loadFenButton.addActionListener(e -> {
            positionInFen.setLength(0);
            positionInFen.append(fenTextField.getText());
            this.dispose();
            synchronized (this){
                this.notifyAll();
            }
        });

        defaultFenButton.addActionListener(e -> {
            positionInFen.setLength(0);
            positionInFen.append(STARTING_CHESS_POSITION_FEN);
            this.dispose();
            synchronized (this){
                this.notifyAll();
            }
        });

        buttonPanel.add(loadFenButton);
        buttonPanel.add(defaultFenButton);

        defaultFenButton.setDefaultCapable(true);

        fenPanel.add(fenTextField, BorderLayout.NORTH);
        fenPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent e){
            System.exit(0);
        }
        });
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().add(fenPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
