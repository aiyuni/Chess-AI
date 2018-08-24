package Pieces;

import chessAI.Board;
import chessAI.Game;

public class Pawn extends Piece {
    
    boolean hasMoved = false;
    
    public Pawn (int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "P", "\u2659", game, 10);
    }

    @Override
    public void generateLegalMoves() {
        validMoves.clear();
        // TODO Auto-generated method stub
        
        if (isWhite == true) {
            
            //checks for right capture
            if (y < 7 && x > 0 && game.isPieceOnTile(x - 1, y + 1) && game.getPieceColor(x - 1, y + 1) != this.isWhite()) {
                validMoves.add(new Integer[] {x - 1, y + 1});
            }
            
            //checks for left capture
            if (y > 0 && x > 0 && game.isPieceOnTile(x - 1, y - 1) && game.getPieceColor(x - 1, y - 1) != this.isWhite()) {
                validMoves.add(new Integer[] {x - 1, y - 1});
            }
            
            //checks for right enpassent
           /* if (y < 7 && game.isPieceOnTile(x, y + 1) && game.getPiece(x, y + 1).pieceName.equals("BP") && (((Pawn)(game.getPiece(x, y + 1))).hasMoved == true) ) {
                validMoves.add(new Integer[] {x, y + 1});
            }
            
            //checks for left enpassent
            if (y > 0 && game.isPieceOnTile(x, y - 1) && game.getPiece(x, y - 1).pieceName.equals("BP") && (((Pawn)(game.getPiece(x, y - 1))).hasMoved == true) ) {
                validMoves.add(new Integer[] {x, y - 1});
            } */
            
            if (game.isPieceOnTile(x - 1, y)) {
                System.out.println("a piece is directly in front of pawn");
                return;
            }
            
            Integer[] validMove = {x - 1, y};
            validMoves.add(validMove);
            if (hasMoved) {
                return;
            }
            else {
                if (game.isPieceOnTile(x - 2, y)) {
                    System.out.println("premature return 2");
                    return;
                }
                validMoves.add(new Integer[] {x - 2, y});
            }
        }
        
        if (isWhite == false) {
            
            if (y < 7 && x < 7 && game.getPiece(x + 1, y + 1) != null && game.getPieceColor(x + 1, y + 1) != this.isWhite()) {
                validMoves.add(new Integer[] {x + 1, y + 1});
            }
            if (y > 0 && x < 7 && game.isPieceOnTile(x + 1, y - 1) && game.getPieceColor(x + 1, y - 1) != this.isWhite()) {
                validMoves.add(new Integer[] {x + 1, y - 1});
            }
            
          /*  if (y < 7 && game.isPieceOnTile(x, y + 1) && game.getPiece(x, y + 1).pieceName.equals("WP") && (((Pawn)(game.getPiece(x, y + 1))).hasMoved == true) ) {
                validMoves.add(new Integer[] {x, y + 1});
            }
            
            if (y > 0 &&  game.isPieceOnTile(x, y - 1) && game.getPiece(x, y - 1).pieceName.equals("WP") && (((Pawn)(game.getPiece(x, y - 1))).hasMoved == true) ) {
                validMoves.add(new Integer[] {x, y - 1});
            } */
            
            if (game.isPieceOnTile(x + 1, y)) {
                System.out.println("a piece is directly in front of pawn");
                return;
            }
            
            Integer[] validMove = {x + 1, y};
            validMoves.add(validMove);
            if (hasMoved) {
                return;
            }
            else {
                if (game.isPieceOnTile(x + 2, y)) {
                    System.out.println("premature return 2");
                    return;
                }
                validMoves.add(new Integer[] {x + 2, y});
            }
        }
       // System.out.println("One of the valid moves is: " + validMoves.get(0)[0] + ", " + validMoves.get(0)[1]);
        
        
    }
    
    @Override
    public void makeMove(int x, int y) {
        System.out.println("inside pawn's makeMove");
        oldX = this.x;
        oldY = this.y;
        oldXStack.push(this.x);
        oldYStack.push(this.y);
        this.x = x;
        this.y = y;
        hasMoved = true;
        System.out.println("size of stack is: " + oldXStack.size());
        test++;
        test2++;
        
    }

    
}
