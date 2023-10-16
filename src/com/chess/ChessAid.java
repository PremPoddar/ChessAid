//Created by Prem Poddar on the 19/08/2023

package com.chess;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.gui.Window;
import com.chess.moves.Move;

import javax.swing.*;
import java.util.Scanner;


public class ChessAid {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
        //consoleMode();
    }

    public static void consoleMode(){
        Scanner scanner = new Scanner(System.in);
        Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        while (board.isGameIsOn()){
            System.out.println(board);
            System.out.println("Enter the move: ");
            String moveInText = scanner.nextLine();

            Move move = new Move(BoardUtils.getCoordinateAtPosition(moveInText.substring(0, 2)), BoardUtils.getCoordinateAtPosition(moveInText.substring(2, 4)));
            board.makeMove(move);
        }
    }
}