package Pieces;

import chessAI.Board;
import chessAI.Game;

public class Bishop extends Piece {
    
    
    public Bishop(int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "B", "\u2657", game, 35);
    }


    
    public String getUnicode() {
        return "\u2657";
    }
    
    /**
     * This method generates legal moves of the bishop, including captures.
     * Two variables are used to hold the current position of the bishop, and they are stepwise inc/decreased in 4 different while loops
     * to account for all directions. 
     * The first time the bishop reaches an enemy piece, the square will be labelled as a validMove, and will then break out of the while loop.
     */
    @Override
    public void generateLegalMoves() {
        // TODO Auto-generated method stub
        validMoves.clear();
        //System.out.println("Inside Bishop's generate moves. Current bishop coordinates are: " + x + ", " + y);
        int horizontal = y;
        int vertical = x;
        Integer[] coordinate;
        while (horizontal < 8 && vertical < 8) {
            
            if (horizontal == y && vertical == x) {
                horizontal++;
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
            
            horizontal++;
            vertical++;
        }
        
        horizontal = y;
        vertical = x;
        
        while (horizontal < 8 && vertical >= 0) {
            
            if (horizontal == y && vertical == x) {
                horizontal++;
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
            
            horizontal++;
            vertical--;
        }
        
        horizontal = y;
        vertical = x;
        
        while (horizontal >= 0 && vertical >= 0) {
            
            if (horizontal == y && vertical == x) {
                horizontal--;
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
            
            horizontal--;
            vertical--;
        }
        
        horizontal = y;
        vertical = x;
        
        
        while (horizontal >= 0 && vertical < 8) {
            
            if (horizontal == y && vertical == x) {
                horizontal--;
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
            
            horizontal--;
            vertical++;
        }
        
        //System.out.println("bishop's valid moves size is: " + validMoves.size());
        for (int i = 0; i<validMoves.size(); i++) {
           // System.out.println("bishop's valid moves: " + validMoves.get(Integer.valueOf(i)));
        }
        
    }
}
