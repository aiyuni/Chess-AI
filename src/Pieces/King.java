package Pieces;

import chessAI.Board;
import chessAI.Game;

//TODO: PLS FIGURE OUT A BETTER WAY TO RECODE KING MOVEMENT lol
public class King extends Piece {

    boolean hasMoved = false;
    boolean isChecked = false;
    
    public King(int x, int y, boolean isWhite, Game game) {
        super(x, y, isWhite, "K", "\u2654", game, 5000);
    }


    @Override
    public void generateLegalMoves() {
        // TODO Auto-generated method stub
        
        validMoves.clear();
        
        //Checks the bottom right corner king
        if (x == 7 && y == 7) {
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
            if (game.getPiece(x-1, y-1) == null || (game.getPiece(x-1, y-1) != null && game.getPieceColor(x-1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y-1});
            }
            return;
        }
        
        //checks the top left corner king
        if (x == 0 && y == 0){
            if (game.getPiece(x+1, y+1) == null || (game.getPiece(x+1, y+1) != null && game.getPieceColor(x+1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y+1});
            }
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            return;
        }
        
        //checks the top right corner king
        if (x == 0 && y == 7) {
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x+1, y-1) == null || (game.getPiece(x+1, y-1) != null && game.getPieceColor(x+1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y-1});
            }
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
            return;
        }
        
        //checks bottom right corner
        if (x == 7 && y == 0) {
            if (game.getPiece(x-1, y+1) == null || (game.getPiece(x-1, y+1) != null && game.getPieceColor(x-1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y+1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            return;
        }
        
        //CHANGE THIS SO THAT IT GOES FROM SPECIFIC TO NON-SPECIFIC(CORNERS-EDGES-CENTER)
        else if (x == 7 ) {
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
            if (game.getPiece(x-1, y+1) == null || (game.getPiece(x-1, y+1) != null && game.getPieceColor(x-1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y+1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
            if (game.getPiece(x-1, y-1) == null || (game.getPiece(x-1, y-1) != null && game.getPieceColor(x-1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y-1});
            }
            
        }
        
        else if (x == 0) {
            if (game.getPiece(x+1, y+1) == null || (game.getPiece(x+1, y+1) != null && game.getPieceColor(x+1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y+1});
            }
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x+1, y-1) == null || (game.getPiece(x+1, y-1) != null && game.getPieceColor(x+1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y-1});
            }
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
         
        }
        
        else if (y == 7) {
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x+1, y-1) == null || (game.getPiece(x+1, y-1) != null && game.getPieceColor(x+1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y-1});
            }
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
            if (game.getPiece(x-1, y-1) == null || (game.getPiece(x-1, y-1) != null && game.getPieceColor(x-1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y-1});
            }
            
        }
        
        else if (y == 0 ) {
            if (game.getPiece(x+1, y+1) == null || (game.getPiece(x+1, y+1) != null && game.getPieceColor(x+1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y+1});
            }
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            if (game.getPiece(x-1, y+1) == null || (game.getPiece(x-1, y+1) != null && game.getPieceColor(x-1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y+1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
           
        }
        
        else {
            if (game.getPiece(x+1, y+1) == null || (game.getPiece(x+1, y+1) != null && game.getPieceColor(x+1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y+1});
            }
            if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y});
            }
            if (game.getPiece(x+1, y-1) == null || (game.getPiece(x+1, y-1) != null && game.getPieceColor(x+1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x+1, y-1});
            }
            if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y+1});
            }
            if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x, y-1});
            }
            if (game.getPiece(x-1, y+1) == null || (game.getPiece(x-1, y+1) != null && game.getPieceColor(x-1, y+1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y+1});
            }
            if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y});
            }
            if (game.getPiece(x-1, y-1) == null || (game.getPiece(x-1, y-1) != null && game.getPieceColor(x-1, y-1) != this.isWhite())) {
                validMoves.add(new Integer[] {x-1, y-1});
            } 
        }
        
       /* if (game.getPiece(x+1, y+1) == null || (game.getPiece(x+1, y+1) != null && game.getPieceColor(x+1, y+1) != this.isWhite())) {
            validMoves.add(new Integer[] {x+1, y+1});
        }
        if (game.getPiece(x+1, y) == null || (game.getPiece(x+1, y) != null && game.getPieceColor(x+1, y) != this.isWhite())) {
            validMoves.add(new Integer[] {x+1, y});
        }
        if (game.getPiece(x+1, y-1) == null || (game.getPiece(x+1, y-1) != null && game.getPieceColor(x+1, y-1) != this.isWhite())) {
            validMoves.add(new Integer[] {x+1, y-1});
        }
        if (game.getPiece(x, y+1) == null || (game.getPiece(x, y+1) != null && game.getPieceColor(x, y+1) != this.isWhite())) {
            validMoves.add(new Integer[] {x, y+1});
        }
        if (game.getPiece(x, y-1) == null || (game.getPiece(x, y-1) != null && game.getPieceColor(x, y-1) != this.isWhite())) {
            validMoves.add(new Integer[] {x, y-1});
        }
        if (game.getPiece(x-1, y+1) == null || (game.getPiece(x-1, y+1) != null && game.getPieceColor(x-1, y+1) != this.isWhite())) {
            validMoves.add(new Integer[] {x-1, y+1});
        }
        if (game.getPiece(x-1, y) == null || (game.getPiece(x-1, y) != null && game.getPieceColor(x-1, y) != this.isWhite())) {
            validMoves.add(new Integer[] {x-1, y});
        }
        if (game.getPiece(x-1, y-1) == null || (game.getPiece(x-1, y-1) != null && game.getPieceColor(x-1, y-1) != this.isWhite())) {
            validMoves.add(new Integer[] {x-1, y-1});
        } */
    }
    
    @Override
    public void makeMove(int x, int y) {
        System.out.println("inside king's makeMove");
        oldX = this.x;
        oldY = this.y;
        oldXStack.push(this.x);
        oldYStack.push(this.y);
        this.x = x;
        this.y = y;
        hasMoved = true;

        test++;
        test2++;
    }
}
