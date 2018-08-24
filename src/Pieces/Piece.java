package Pieces;

import java.util.ArrayList;
import java.util.Stack;

import chessAI.Game;
import chessAI.Tile;

public abstract class Piece implements Cloneable {
    
    boolean isWhite;
    int x;
    int y;
    int oldX;
    int oldY;
    int value;
    String pieceName;
    String pieceUnicode;
    Game game;
   // Board board;
    Tile tile;
    
    
    ArrayList<Integer[]> validMoves = new ArrayList<Integer[]>();
    Stack<Integer> oldXStack = new Stack<Integer>();
    Stack<Integer> oldYStack = new Stack<Integer>();
    int test = 0;
    int test2 = 0;
    
    
    public Piece(int x, int y, boolean isWhite, String pieceName, String pieceUnicode, Game game, int value) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.isWhite = isWhite;
        this.pieceUnicode = pieceUnicode;
        this.game = game;
        this.value = value;
       // this.board = board;
        
        if (isWhite) {
            this.pieceName = "W" + pieceName;
        }
        else {
            this.pieceName = "B" + pieceName;
        }
        //System.out.println("Piece name is: " + this.pieceName);
    }
    
    public Piece() {
        
    }
    public abstract void generateLegalMoves();
    
    public void makeMove(int x, int y) {
        oldX = this.x;
        oldY = this.y;
        oldXStack.push(this.x);
        oldYStack.push(this.y);
        this.x = x;
        this.y = y;
        test++;
        test2++;
        System.out.println("size of stack is: " + oldXStack.size());
        
    }
    
    @Override
    public String toString() {
        return pieceName;
    }
    
    public void setPieceName(String name) {
        pieceName = name;
    }
    
    public String toUnicode() {
        return pieceUnicode;
    }
    
    public void setUnicode(String unicode) {
        this.pieceUnicode = unicode;
    }
    public boolean isWhite() {
        if (isWhite) {
            return true;
        }
        else return false;
    }
    
    
    public ArrayList<Integer[]> returnLegalMoves() {
        return validMoves;
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public Object Clone() {
        
        Piece clonedPiece = null;
        try {
            clonedPiece = (Piece) super.clone();
            clonedPiece.pieceName = this.pieceName;
            clonedPiece.pieceUnicode = this.pieceUnicode;

            //System.out.println("size of stack in original piece is: " + this.pieceName + ", " +  clonedPiece.oldXStack.size());
            clonedPiece.oldXStack = (Stack<Integer>) this.oldXStack.clone();
            clonedPiece.oldYStack = (Stack<Integer>) this.oldYStack.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //System.out.println("size of stack in cloned piece is: " + clonedPiece.pieceName + ", " + clonedPiece.oldXStack.size());
        return clonedPiece;
    }
    
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    
    public Tile getTile() {
        return tile;
    }
    
    public int getValue() {
        return value;
    }
    public String getUnicode() {
        return pieceUnicode;
    }
    public String getName() {
        return pieceName;
    }
    
    public void setX(int x) {
        this.x = x;
        //System.out.println("test 2 is: " + test2);
    }
    
    public void setY(int y) {
        this.y = y; 
    }
    
    public void setOldX(int x) {
        oldXStack.push(x);
    }
    
    public void setOldY(int y) {
        oldYStack.push(y);
    }
    
    public int getOldX() {
        //return oldX;
        
        //System.out.println("size of stack in get is: " + oldXStack.size());
        //System.out.println(test);
        if (oldXStack.size() != 0) {
            return oldXStack.pop();
        }
        else {
            return oldX;
        } 
    }
    
    public int getOldY() {
        //return oldY;
        //System.out.println("size of stack in get is: " + oldXStack.size());
        if (oldYStack.size() != 0) {
            return oldYStack.pop();
        }
        else {
            return oldY;
        } 
    }
}
