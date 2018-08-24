package Pieces;

import chessAI.Board;
import chessAI.Game;

public class Rook extends Piece {
    
    boolean hasMoved = false;
    
    public Rook(int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "R", "\u2656", game, 50);
    }

    public Rook() {
        super();
    }

    @Override
    public void generateLegalMoves() {
        
        validMoves.clear();
        
        int horizontal = y;
        int vertical = x;
        Integer[] coordinate;
        
        while (horizontal < 8) {
            if (horizontal == y) {
                horizontal++;
                continue;
            }
            
            if (game.getPiece(vertical , horizontal) == null) {
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
            }
            
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite()){
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
                break;
            }
            
            else {
                break;
            }
            
            horizontal++;
        }
        
        horizontal = y;
        vertical = x;
        
        while (horizontal >= 0) {
            if (horizontal == y) {
                horizontal--;
                continue;
            }
            
            if (game.getPiece(vertical , horizontal) == null) {
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
            }
            
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite()){
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
                break;
            }
            
            else {
                break;
            }
            
            horizontal--;
        }
        
        horizontal = y;
        vertical = x;
        
        while (vertical < 8) {
            if (vertical == x) {
                vertical++;
                continue;
            }
            
            if (game.getPiece(vertical , horizontal) == null) {
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
            }
            
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite()){
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
                break;
            }
            
            else {
                break;
            }
            
            vertical++;
        }
        
        horizontal = y;
        vertical = x;
        
        while (vertical >= 0) {
            if (vertical == x) {
                vertical--;
                continue;
            }
            
            if (game.getPiece(vertical , horizontal) == null) {
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
            }
            
            else if (game.getPieceColor(vertical, horizontal) != this.isWhite()){
                coordinate = new Integer[] {vertical, horizontal};
                validMoves.add(coordinate);
                break;
            }
            
            else {
                break;
            }
            
            vertical--;
        }
        
    }
    
    @Override
    public void makeMove(int x, int y) {
        System.out.println("inside rook's makeMove");
        oldX = this.x;
        oldY = this.y;
        this.x = x;
        this.y = y;
        hasMoved = true;
        
    }
}
