package chessAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import javafx.application.Platform;

/**
 * TODO: The current AI is messed up. At a depth of 1 it works, but any higher and it doesnt work
 * Checking for mate: computer does not go for mating moves as it breaks the AI.
 * Multithreading: not working at all. Re-implement
 * Castling, en-passent, promotion not implemented
 * Alpha-beta pruning is implemented, but still very slow search for some reason.
 * 
 * @author Perry
 *
 */
public class Game implements Runnable {
    
    public static final int BOARD_LENGTH = 8;
    public static final int DEPTH_LIMIT = 1; //if 1, makes 2 moves for some reason. If 0, just makes 1 move. if 2, makes ??
    public Piece[][] textBoard = new Piece[BOARD_LENGTH][BOARD_LENGTH];
    
    public int linesAnalyzed = 0;
    public int positionsAnalyzed = 0;
    public int boardScoreNotZero = 0;
    public Piece firstMovingPiece;
    
    public int testX = 0;
    
    private Board board;
    
    public int maxDepth = 0;
    
    public MultiReturn<Piece, int[]> pieceAndMovePosition;
    
    public Game(boolean isWhite) {
        
         if (isWhite) {
             createTextBoard(); //do it so you can only play as white for now
             //showTextBoard();
         }      
    }
    
    /**
     * Two separate threads that handles the AI calculations and GUI updates so it does not freeze the GUI application.
     */
    @Override
    public void run() {      
        computerMove(board);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                board.createVisualBoard();
            }
        });
    }
    
    public void createTextBoard(){
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                textBoard[i][j] = null; //null = no piece is present on square
                
            }
        }
        
        //Spawn white pieces
        textBoard[7][0] = new Rook(7, 0, true, this);
        textBoard[7][1] = new Knight(7, 1, true, this);
        textBoard[7][2] = new Bishop(7, 2, true, this);
        textBoard[7][3] = new Queen(7, 3, true, this);
        textBoard[7][4] = new King(7, 4, true, this);
        textBoard[7][5] = new Bishop(7, 5, true, this);
        textBoard[7][6] = new Knight(7, 6, true, this);
        textBoard[7][7] = new Rook(7, 7, true, this);
        
        //Spawn the white and black pawns.
        for (int i = 0; i < BOARD_LENGTH; i++) {
            textBoard[6][i] = new Pawn(6, i, true, this);
            textBoard[1][i] = new Pawn(1, i, false, this);
        }
        
        //Spawn black pieces
        textBoard[0][0] = new Rook(0, 0, false, this);
        textBoard[0][1] = new Knight(0, 1, false, this);
        textBoard[0][2] = new Bishop(0, 2, false, this);
        textBoard[0][3] = new Queen(0, 3, false, this);
        textBoard[0][4] = new King(0, 4, false, this);
        textBoard[0][5] = new Bishop(0, 5, false, this);
        textBoard[0][6] = new Knight(0, 6, false, this);
        textBoard[0][7] = new Rook(0, 7, false, this);
        
    }
    
    public void showTextBoard() {
        for (int i = 0; i< BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (textBoard[i][j] == null) {
                    System.out.print(textBoard[i][j] + " | ");
                }
                else {
                    System.out.print(textBoard[i][j] + "  |  ");
                }
            }
            System.out.println();
        }
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void computerMove(Board board) {
        
        linesAnalyzed = 0;
        
        this.board = board;
        Piece pieceToMove;
        
        int[] coordinateOfMove = new int[2];
        coordinateOfMove = findBestMove();
        
        System.out.println("Best Move is: " + coordinateOfMove[0] + ", " + coordinateOfMove[1]);
        
        pieceToMove = pieceAndMovePosition.getFirst();
        coordinateOfMove = pieceAndMovePosition.getSecond();
        
        System.out.println("Best Move from helper method is: " + coordinateOfMove[0] + ", " + coordinateOfMove[1] + ", " + pieceToMove);
        
        board.addToStack();
        board.makeMove(pieceToMove, coordinateOfMove[0], coordinateOfMove[1]);
       
    }
    
    public int[] findBestMove(){
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int bestVal = Integer.MIN_VALUE;
        int moveVal = -1;
        Random rand = new Random();
        
        Piece movingPiece = null;
        List<Piece> movingPieces = new ArrayList<Piece>();
        List<Piece> computerPieces = new ArrayList<Piece>();
        List<Integer[]> moveList1 = new ArrayList<Integer[]>();
        int[] bestMove = {0,0};
        List<int[]> bestMoves = new ArrayList<int[]>();
        
        Piece currentPiece;
        
        System.out.println("inside findBestMove");
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                currentPiece = this.getPiece(i, j);
                if (currentPiece != null) {
                    if (currentPiece.isWhite() == false) {
                        computerPieces.add(currentPiece);
                    }
                }
            }
        }
        System.out.println("The list of computer pieces is: " + computerPieces.toString());
        
        for (int i = 0; i < computerPieces.size(); i++) {
            Piece piece;
            piece = (Piece) computerPieces.get(i).Clone();
            piece.generateLegalMoves();
            moveList1 = (List<Integer[]>) board.checkLegalMovesForPin(piece.returnLegalMoves(), piece).clone(); //newest change: clone is somehow working?
            for (int j = 0; j < moveList1.size(); j++) {
                moveVal = -1000000;
                board.addToStack();
                System.out.println("moving piece is moving to: " + moveList1.get(j)[0] + ", " + moveList1.get(j)[1]);
                board.makeMove((Piece)piece.Clone(), moveList1.get(j)[0], moveList1.get(j)[1]);  //make move
                
                /*in other words, this evaluateMove will get the best possible move that the human will make in response to 
                 * the computer that makes this move in the moveList.  Hence, the value will be a Min, and later on 
                 * in this loop, the computer will evaluate if this min is higher than the previous min calculated,
                 * hence, the computer is Max. */
               
                moveVal = this.evaluateMove(this.textBoard.clone(), false, 0, alpha, beta);  //evaluate move, let player be min
                //moveVal = 0;
                System.out.println("OUT OF THE RECURSION LOOPS");
                System.out.println("j is: " + j);
                board.undoBoard(board.getBoardHistory().pop());  //undo move
                System.out.println("moveVal is: " + moveVal);
                System.out.println("j is: " + j);
              //if the move is the best move thus far, keep track of its val and coordinates
                if (moveVal > bestVal && moveVal != -1) {
                    System.out.println("new Best bestVal is: " + moveVal);
                    movingPiece = (Piece) piece.Clone();
                    movingPieces.clear();
                    movingPieces.add((Piece) movingPiece.Clone());
                    System.out.println("moveList1 size: " + moveList1.size());
                    System.out.println("j is: " + j);
                    
                    System.out.println("clearing and adding new value: " + movingPiece + ", at: " + moveList1.get(j)[0] 
                            + ", " + moveList1.get(j)[1]);
                    
                    bestVal = moveVal;
                    bestMove[0] = moveList1.get(j)[0].intValue();
                    bestMove[1] = moveList1.get(j)[1].intValue();  //for now, just have 1 best move.  No need for ties yet
                    bestMoves.clear(); //important to remove previous 'best' moves from array
                    bestMoves.add(bestMove.clone());
                    //break outer;
                }
                
                else if (moveVal == bestVal && moveVal != -1) {
                    System.out.println("more than 1 best move");
                    System.out.println("bestVal is: " + bestVal);
                    
                    if (moveList1.size() != 0) {
                        
                        movingPiece = (Piece)piece;
                        movingPieces.add(movingPiece);
                        bestMove[0] = moveList1.get(j)[0];
                        bestMove[1] = moveList1.get(j)[1];
                        
                        System.out.println("adding same value: " + movingPiece + ", at: " + moveList1.get(j)[0] 
                                + ", " + moveList1.get(j)[1]);
                        bestMoves.add(bestMove.clone());
                    }
                }
               
            } 
         
        }  

        System.out.println("bestMove is: " + bestMove[0] + ", " + bestMove[1] + ", " + movingPiece);
        
        if (movingPiece == null) {
            System.out.println("Computer cannot find a move, mated?");
        }
        
        System.out.println("printing movingPieces arraylist: " + movingPieces.toString());
        System.out.println("printing bestMoves arraylist: ");
        System.out.println("MoveList size is: " + moveList1.size() + ", and bestMoves size is: " + bestMoves.size());
        for (int i = 0; i < bestMoves.size(); i++) {
            System.out.print(bestMoves.get(i)[0] + "," + bestMoves.get(i)[1] +"  ");
        }
        
        System.out.println("Boardscore not 0 is: " + boardScoreNotZero);
        int randNumber = rand.nextInt(movingPieces.size());
        pieceAndMovePosition = new MultiReturn<Piece, int[]>(movingPieces.get(randNumber), bestMoves.get(randNumber));
        
        return bestMove;
    }
    
    //cocmputer is MAX, player is MIN (computer = true, player = false for isMax)
    public int evaluateMove(Piece[][] textualBoard, boolean isMax, int depth, int alpha, int beta) {
        
        int boardScore = 0;
        int whitePieceValue = 0;
        int blackPieceValue = 0;
        int best = 0;
        Piece piece;
        
        System.out.println("inside evaluateMove");
        
        if (maxDepth < depth) {
            maxDepth = depth;
        }       
        
        //Since human is always White player, if the white king is mated, return high score (computer is MAX)
        /*if (board.isKingMated(true, this.textBoard)) {  //.isKingMated(isWhite())
            board.testButton.setText(board.testButton.getText() + "WKM");
            boardScore = 10000;
            //board.undoBoard(board.getBoardHistory().pop());
            return boardScore;
        }
        
        if (board.isKingMated(false, this.textBoard)) {
            board.testButton.setText(board.testButton.getText() + "BKM");
            boardScore = -10000;
            //board.undoBoard(board.getBoardHistory().pop());
            return boardScore;
        } */
        
        //If max depth has been reached, dont recursion call, just compare piece value differences
        if (depth == DEPTH_LIMIT) {
            linesAnalyzed++;
            positionsAnalyzed++;
            System.out.println("LAST DEPTH");
            //add up all human pieces
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j ++) {
                    //piece = textualBoard[i][j];
                    piece = this.textBoard[i][j];
                    if (piece != null && piece.isWhite()) {
                        whitePieceValue += piece.getValue();
                        System.out.println("White piece value here: " + whitePieceValue);
                    }
                }
            }
            
            //add up computer piece values
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j ++) {
                    //piece = textualBoard[i][j];
                    piece = this.textBoard[i][j];
                    if (piece != null && piece.isWhite() == false) {
                        blackPieceValue += piece.getValue();
                    }
                }
            }
            
            if(isMax == false) {
                boardScore = blackPieceValue - whitePieceValue - depth;
            }
            else {
                boardScore = -(whitePieceValue - blackPieceValue) + depth;
            }
            
            System.out.println("blackPiecevalue: " + blackPieceValue);
            System.out.println("White piece value: " + whitePieceValue);
            System.out.println("The boardScore for evaluateMove piece difference is: " + boardScore);
            
            if (boardScore != 0) {
                boardScoreNotZero++;
            }
            //board.undoBoard(board.getBoardHistory().pop());
            return boardScore;
        }
        
        /*If maxdepth not reached, and if the isMax (computer) made this move that resulted in this board position, evaluate the best human 
         * move in response to this position (by taking the min value)
         */
        if (isMax) {
            List<Piece> army = new ArrayList<Piece>();
            Piece currentPiece;
            List<Integer[]> moveList = new ArrayList<Integer[]>();
            
            //adds all the white pieces to the list
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j ++) {
                    //piece = textualBoard[i][j];
                    piece = this.textBoard[i][j];
                    if (piece != null && !piece.isWhite()) {
                        army.add(piece);
                    }
                }
            }
            
            for (int i = 0; i < army.size(); i++) {
                currentPiece = army.get(i);
                currentPiece.generateLegalMoves();
                moveList = board.checkLegalMovesForPin(currentPiece.returnLegalMoves(), currentPiece);
                for (int j = 0; j < moveList.size(); j++) {
                    
                    positionsAnalyzed++;
                    
                    board.addToStack();  //save board so that the move can be undo
                    
                    if (depth == 0) {
                        firstMovingPiece = (Piece) currentPiece.Clone();
                    }
                    
                    board.makeMove((Piece)currentPiece.Clone(), moveList.get(j)[0], moveList.get(j)[1]); //make move
                    testX++;
                    board.testButton.setText(", " + testX);
                    
                    /*if (board.isKingUnderCheck(true, textualBoard)) {
                        System.out.println("Computer found that the white king is under check, returning...");
                        board.undoBoard(board.getBoardHistory().pop()); //undo move
                        testX--;
                        return 1000;
                    } */
                    
                    //Checks if the turn player's king is in check (the turn player cannot move into check)
                    if (board.isKingUnderCheck(false, textualBoard)) {
                        System.out.println("In max: found that computer king is under check.");
                        board.undoBoard(board.getBoardHistory().pop());
                        break;
                    }
                    
                    best = Math.max(best, evaluateMove(this.textBoard, false, depth + 1, alpha, beta));  //recursion call 
                    
                    board.undoBoard(board.getBoardHistory().pop()); //undo move
                    
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) {
                        //System.out.println("breaking");
                        break;
                    }
                    
                    testX--;
                    board.testButton.setText("hello, " + testX);
                }
            }
            
            return best;          
        }
        
        else if (!isMax) {
            List<Piece> army = new ArrayList<Piece>();
            Piece currentPiece;
            List<Integer[]> moveList = new ArrayList<Integer[]>();
            
            //adds all the white pieces to the list
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j ++) {
                    piece = textualBoard[i][j];
                    if (piece != null && piece.isWhite()) {
                        army.add(piece);
                    }
                }
            }
            
            for (int i = 0; i < army.size(); i++) {
                currentPiece = army.get(i);
                currentPiece.generateLegalMoves();
                moveList = board.checkLegalMovesForPin(currentPiece.returnLegalMoves(), currentPiece);
                for (int j = 0; j < moveList.size(); j++) {
                    
                    positionsAnalyzed++;
                    
                    board.addToStack();  //save board so that the move can be undo
                    
                    if (depth == 0) {
                        firstMovingPiece = (Piece) currentPiece.Clone();
                    }
                    
                    board.makeMove((Piece)currentPiece, moveList.get(j)[0], moveList.get(j)[1]); //make move
                    
                    testX++;
                    board.testButton.setText(", " + testX);
                    
                    /*if (board.isKingUnderCheck(false, textualBoard)) {
                        System.out.println("Computer found that the black king is under check, returning...");
                        board.undoBoard(board.getBoardHistory().pop()); //undo move
                        testX--;
                        return -1000;
                    } */
                    
                    //Checks if the turn player's king is in check (the turn player cannot move into check)
                    if (board.isKingUnderCheck(true, textualBoard)) {
                        System.out.println("In min: found that player king is under check.");
                        board.undoBoard(board.getBoardHistory().pop());
                        break;
                    }
                    
                    
                    best = Math.min(best, evaluateMove(this.textBoard, true, depth + 1, alpha, beta));  //recursion call 
                    
                    board.undoBoard(board.getBoardHistory().pop()); //undo move
                    
                    beta = Math.min(beta, best);
                    if (beta <= alpha) {
                       // System.out.println("breaking");
                        break;
                    }
                    
                    testX--;
                    board.testButton.setText("hello "+ ", " + testX);
                }
            }
            
            return best;          
        }
        

        System.out.println("this will never be printed: The boardScore for evaluateMove is: " + boardScore);
        return boardScore;
    }
    
    public String getPieceUnicode(int x, int y) {
        if (textBoard[x][y] != null) {
            return textBoard[x][y].toUnicode();
        }
        else {
            return " ";
        }
    }
    
    public boolean getPieceColor(int x, int y) {
        if (textBoard[x][y] != null) {
            return textBoard[x][y].isWhite();
        }
        else {
            return false;
        }
    }
    
    public boolean isPieceOnTile(int x, int y) {
        //System.out.println(x + ", " + y );
       if (textBoard[x][y] != null) {
           return true;
       }
       else return false;
    }
    
    public Piece getPiece(int x, int y) {
        return textBoard[x][y];
    }
    
    public int[] convertIntegerToInt(Integer[] xy) {
        
        int[] result = new int[xy.length];
        for (int i = 0; i < xy.length; i++) {
            result[i] = xy[i].intValue();
        }
        return result;
    }
}
