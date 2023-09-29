package com.chess.gui;


import com.app.AppManager;
import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.moves.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {

    private StringBuilder positionInFen;
    private JPanel panel;
    private JLabel allianceLabel;
    private JButton moveButton;
    private JTextField moveTextField;
    private ChessBoardPanel chessBoardPanel;

    public Window() {
        positionInFen = new StringBuilder();
        moveTextField = new JTextField(5);
        allianceLabel = new JLabel();
        chessBoardPanel = new ChessBoardPanel();
        panel = new JPanel(new BorderLayout());
        moveButton = new JButton("Make Move");

        moveButton.addActionListener(e -> {
            final Move move = new Move(BoardUtils.getCoordinateAtPosition(moveTextField.getText().substring(0, 2)), BoardUtils.getCoordinateAtPosition(moveTextField.getText().substring(2, 4)));
            if (chessBoardPanel.board.getCurrentPlayer().getAlliance() == Alliance.WHITE) {
                allianceLabel.setText("Black");
            }else {
                allianceLabel.setText("White");
            }
            chessBoardPanel.makeMove(move);
            moveTextField.setText("");

        });

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(allianceLabel, BorderLayout.WEST);
        southPanel.add(moveTextField, BorderLayout.CENTER);
        southPanel.add(moveButton, BorderLayout.EAST);

        panel.add(chessBoardPanel, BorderLayout.NORTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        LoadFenWindow loadFenWindow = new LoadFenWindow(positionInFen);

        loadFenWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                chessBoardPanel.updateBoard(positionInFen.toString());
                if (chessBoardPanel.board.getCurrentPlayer().getAlliance() == Alliance.WHITE) {
                    allianceLabel.setText("White");
                }else {
                    allianceLabel.setText("Black");
                }
                Window.this.add(panel);
                Window.this.pack();
                Window.this.setLocationRelativeTo(null);
                Window.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Window.this.setVisible(true);
            }
        });
    }
}
