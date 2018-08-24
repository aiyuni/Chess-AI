package Pieces;

import chessAI.Board;
import chessAI.Game;

public class Queen extends Piece {
    
    public Queen(int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "Q", "\u2655", game, 90);
    }


    @Override
    public void generateLegalMoves() {
        // TODO Auto-generated method stub
        validMoves.clear();
        
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
        
        horizontal = y;
        vertical = x;
        
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
}
