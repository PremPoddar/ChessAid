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
        String defaultBoardFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String boardFen;

        System.out.print("Type d for the normal chess setup or enter in your own fen: ");
        boardFen = scanner.nextLine();
        if(boardFen.equals("d") || boardFen.equals("D")){
            boardFen = defaultBoardFen;
        }else if(!BoardUtils.isValidFen(boardFen)){
            System.out.println("The fen you entered is incorrect.");
            System.exit(0);
        }
        Board board = new Board(boardFen);

        while (board.isGameIsOn()){
            System.out.println(board);
            System.out.print("Enter the move: ");
            String moveInText = scanner.nextLine();

            Move move = new Move(BoardUtils.getCoordinateAtPosition(moveInText.substring(0, 2)), BoardUtils.getCoordinateAtPosition(moveInText.substring(2, 4)));
            board.makeMove(move);
        }
    }
}