package Pieces;

import java.util.ArrayList;
import java.util.Iterator;

import chessAI.Board;
import chessAI.Game;

public class Knight extends Piece {
    
    public Knight(int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "Kn", "\u2658", game, 30);
    }

    @Override
    public void generateLegalMoves() {
        // TODO Auto-generated method stub
        
        validMoves.clear();
        
        int horizontal;
        int vertical;
        
        vertical = x + 1;
        horizontal = y + 2;
        if (horizontal < 8 && vertical < 8) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x + 2;
        horizontal = y + 1;
        if (horizontal < 8 && vertical < 8) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x - 1;
        horizontal = y + 2;
        if (horizontal < 8 && vertical >= 0) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x + 2;
        horizontal = y - 1;
        if (horizontal >= 0 && vertical < 8) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x + 2;
        horizontal = y - 1;
        if (horizontal >= 0 && vertical < 8) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x - 2;
        horizontal = y + 1;
        if (horizontal < 8 && vertical >= 0) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x + 1;
        horizontal = y - 2;
        if (horizontal >= 0 && vertical < 8) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x - 1;
        horizontal = y - 2;
        if (horizontal >= 0 && vertical >= 0) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        vertical = x - 2;
        horizontal = y - 1;
        if (horizontal >= 0 && vertical >= 0) {
            if (game.getPiece(vertical, horizontal) == null) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite) {
                validMoves.add(new Integer[] {vertical, horizontal});
            }
        }
        
        /*for (int i = 0; i < validMoves.size(); i++) {
            //PIECE DOESNT HAVE A BOARD REFERENCE. ADD IT?
        } */
    }
}
