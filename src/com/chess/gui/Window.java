package com.chess.gui;



import com.chess.Alliance;
import com.chess.board.BoardUtils;
import com.chess.moves.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {

    private final StringBuilder positionInFen;
    private final JPanel panel;
    private static JLabel allianceLabel;
    private final JTextField moveTextField;
    private final ChessBoardPanel chessBoardPanel;

    public Window() {
        positionInFen = new StringBuilder();
        moveTextField = new JTextField(5);
        allianceLabel = new JLabel();
        chessBoardPanel = new ChessBoardPanel();
        panel = new JPanel(new BorderLayout());
        JButton moveButton = getjButton();

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

    private JButton getjButton() {
        JButton moveButton = new JButton("Make Move");

        moveButton.addActionListener(e -> {
            final Move move = new Move(BoardUtils.getCoordinateAtPosition(moveTextField.getText().substring(0, 2)), BoardUtils.getCoordinateAtPosition(moveTextField.getText().substring(2, 4)));
            chessBoardPanel.makeMove(move);
            allianceLabel.setText(chessBoardPanel.board.getCurrentPlayer().getAlliance().toString());
            moveTextField.setText("");

        });
        return moveButton;
    }

    public static void updateAllianceLabel(final String alliance){
        allianceLabel.setText(alliance);
    }
}
