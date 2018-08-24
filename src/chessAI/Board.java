package chessAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Pieces.Piece;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**This class will create the visual board that is based off the text board 
 * IMPORTANT: x will always refer to rows, y will always refer to columns: pls keep consistant
 * TODO: en passent implementation is INCORRECT. Find a way to do it correctly without so much code like my first chess game rofl
 * PROBLEM: Single player pinned piece working. now test AI.  
 * Should do this in generateLegalMoves, so that the AI will also know? 
 * fix this first!!!
 * */

public class Board {
    
    public static final int BOARD_LENGTH = 8;
    
    private boolean isWhite;
    private Stage primaryStage;
    private BorderPane root = new BorderPane();
    private GridPane grid; 
    private Button newGameButton = new Button("New Game");
    private Button undoButton = new Button("Undo move");
    public Button testButton = new Button("placeholder");
    public Button testButtonTop = new Button("pls");
    private Text gameInfoText = new Text("Game Info here");
    public Text searchText = new Text("Lines analyzed: ");
    public VBox vbox = new VBox();
    
    private Game game;
    
    private Tile[][] visualBoard = new Tile[BOARD_LENGTH][BOARD_LENGTH];
    
    private Tile clickedTile;
    private boolean isClicked = false;
    private boolean isChecked = false;
    private boolean isWhiteTurn = true;
    
    private ArrayList <Integer[]> legalMoves;
    private Piece clickedPiece; //this is the piece of the tile of the first click
    
    private Stack<Piece[][]> boardHistory = new Stack<Piece[][]>();
    private Stack<Tile[][]> visualBoardHistory = new Stack<Tile[][]>();
    
    public Board(Stage stage, Game game) {

        primaryStage = stage;
        this.game = game;
        
        grid = new GridPane();
        createVisualBoard();
        
        vbox.getChildren().addAll(gameInfoText, searchText);
        
        root.setCenter(grid);
        root.setLeft(undoButton);
        root.setRight(vbox);
        root.setBottom(testButton);
        root.setTop(testButtonTop);

        undoButton.setOnAction((event) -> {
            
            Piece[][] previousBoard = boardHistory.pop();
           // System.out.println("undo event" + previousBoard.toString());
            for (int i = 0; i < previousBoard.length; i++) {
                for (int j = 0; j < previousBoard[i].length; j++) {
                    Piece piece = previousBoard[i][j];
                    //System.out.println(piece);
                    game.textBoard[i][j] = previousBoard[i][j];
                    if (piece != null) {
                        visualBoard[j][i].setPiece(piece.toUnicode(), piece.isWhite());
                    }
                    else {
                        visualBoard[j][i].setPiece("", true);
                    }
                }
            }
            this.clearAllHighlights(); //bug fix: prevent clicking once then undo then moving
            isClicked = false; //same bug fix
        });
        
        
        Scene scene = new Scene(root, 750, 600);
        primaryStage.setTitle("Chess " + isWhite);
        primaryStage.setScene(scene);
        primaryStage.show();
            
    }
    
    /*This method creates the visual board*/
    public void createVisualBoard() {
        for (int i = 0; i< BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Tile tile = new Tile(game.getPieceUnicode(i,  j), game.getPieceColor(i, j), i, j);
                grid.add(tile, j, i);
                //assigns tile to the tile array
                visualBoard[j][i] = tile;
                if (game.getPiece(tile.getX(), tile.getY()) != null) {
                    Piece tempPiece = game.getPiece(tile.getX(), tile.getY());  //this lets the Piece get a Tile reference, 
                    tempPiece.setTile(tile);                                    //maybe find a better way to do this
                } 
                tile.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event mouseClicked) {
                        System.out.println(tile);
                        
                        //If first click is on a piece, highlight its squares
                        if(!isClicked && game.isPieceOnTile(tile.getX(), tile.getY())) {
                          
                            clickedTile = tile;
                            clickedTile.highlightOn();
                            clickedPiece = game.getPiece(tile.getX(), tile.getY());
                            System.out.println("Clicked Piece in first click is: " + clickedPiece.toString());
                            clickedPiece.generateLegalMoves();
                            legalMoves = checkLegalMovesForPin(clickedPiece.returnLegalMoves(), clickedPiece);
                            if (legalMoves == null) {
                                System.out.println("The piece you are trying to move is pinned.");
                                isClicked = true;
                            }
                            else {
                                highlightLegalMoves(legalMoves);
                                isClicked = true;
                            }
                            isWhiteTurn = true;
                        }
                        
                        //If it is 2nd click, attempt to make move.
                        else if (isClicked) {
                            if (tile != clickedTile) {
                                System.out.println("clicked different tile.");
                                legalMoves = checkLegalMovesForPin(clickedPiece.returnLegalMoves(), clickedPiece);
                                if (legalMoves == null) {
                                    System.out.println("No valid moves. Pinned piece.");
                                    return;
                                } 
                                for (int i = 0; i < legalMoves.size(); i++) {
                                    ////////This is to prevent user from moving without regards to being checked. disable for testing
                                    ///this if statement checks if the user is already in check, and whether or not the move removes the check
                                   //WE DONT NEED THIS IF STATEMENT. IT IS INDIRECTLY COVERED BY checkLegalMovesForPin!
                                    /* if (isChecked && legalMoves.get(i)[0] == tile.getX() && legalMoves.get(i)[1] == tile.getY()) {
                                        System.out.println("IN CHECK: attempted move is legal");
                                        addToStack();
                                        makeMove(clickedPiece, tile.getX(), tile.getY());
                                        if (isKingUnderCheck(false, game.textBoard) == true) {  //this is set to black king right now
                                            System.out.println("Move doesn't remove check");
                                            undoBoard(boardHistory.pop());
                                            break;
                                        }
                                        else {
                                            undoBoard(boardHistory.pop());
                                        }
                                    }  */
                                    
                                    if (legalMoves.get(i)[0] == tile.getX() && legalMoves.get(i)[1] == tile.getY()) {
                                        isChecked = false;
                                        System.out.println("attempted move is legal");
                                        addToStack(); //adds the move to the stack

                                        System.out.println(clickedPiece.getY());
                                        System.out.println(clickedPiece.getX());
                                        System.out.println(tile.getY()); 
                                        System.out.println(tile.getX());
                                        
                                        System.out.println("the clicked piece in the 2nd click is (should be same as 1st click)" + clickedPiece.toString());
                                        System.out.println("this should print the same piece: " + game.textBoard[clickedPiece.getX()][clickedPiece.getY()]);
                                        
                                        
                                        makeMove(clickedPiece, tile.getX(), tile.getY());
                                        
                                        //game.showTextBoard();
                                        clearAllHighlights();
                                        
                                        System.out.println("printing textboard:");
                                        //game.showTextBoard();
                                        
                                        isChecked = isKingUnderCheck(false, game.textBoard); //true == checks if white king is in check
                                        if (isChecked) {
                                            testButtonTop.setText("Black King Checked");
                                           if (isKingMated(false, game.textBoard)) {
                                               testButtonTop.setText("Black King Mated");
                                           }; 
                                           //game.showTextBoard();
                                        }
                                        //check if white king is under check (this can be else statement as only 1 king can be in check anyways)
                                        else if (isKingUnderCheck(true, game.textBoard)){
                                            testButtonTop.setText("White King checked");
                                            if (isKingMated(true, game.textBoard)) {
                                                testButtonTop.setText("White King Mated");
                                            }
                                        }
                                        //Neither king is checked.
                                        else {
                                            testButtonTop.setText("not checked");
                                        }
                                        
                                        //let player always be white.
                                        if (isWhiteTurn) {
                                            //System.out.println("MAKING COMPUTER MOVE");
                                            testButton.setText(testButton.getText() + "i");
                                            game.setBoard(Board.this);
                                            game.run();
                                            
                                            //game.computerMove(Board.this);
                                            searchText.setText("Lines Analyzed: "  + game.linesAnalyzed 
                                                    + "Positions analyzed: " + game.positionsAnalyzed);
                                        }
                                        
                                        isWhiteTurn = false;
                                        
                                        break;
                                    }
                                    else {
                                        System.out.println("attempted move is ILLEGAL");
                                    }
                                }
                                clickedTile.highlightOff();
                                isClicked = false;
                                clearAllHighlights();
                            }
                            else {
                                System.out.println("Clicked same tile.");
                                clickedTile.highlightOff();
                                isClicked = false;
                                clearAllHighlights();
                            }
                            
                        }
                        
                        //This triggers if user is on first click, and clicked a tile with no pieces on it (invalid first click)
                        else {
                            System.out.println("First click on empty tile.");
                        }
                    }

                });
                
            }
        }
    }
    
    public void addToStack() {
        Piece[][] tempBoard = new Piece[BOARD_LENGTH][BOARD_LENGTH];
        
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (game.textBoard[i][j] != null) {
                    tempBoard[i][j] = (Piece)game.textBoard[i][j].Clone();  //clones the Piece so that it keeps hasMoved
                    
                    //tempBoard[i][j] = (Piece)game.textBoard[i][j];
                }
                else {
                    tempBoard[i][j] = game.textBoard[i][j];
                }                
            }
        }
        
        System.out.println("Adding this board to stack: ");
        for(int i = 0; i<BOARD_LENGTH; i++) {
            for (int j =0; j< BOARD_LENGTH; j++) {
                if (tempBoard[i][j] == null) {
                    System.out.print(tempBoard[i][j] + "  |  ");
                }
                else {
                    System.out.print(tempBoard[i][j] + "   |   "); 
                }
            }
            System.out.println();
        }
        
        
        
        boardHistory.push(tempBoard);
    }
    
    //checks if the parameter color's king is in check or not
    public boolean isKingUnderCheck(boolean isWhite, Piece[][] board) {
        
        //army is either the white pieces or black pieces (opposite of the king under check), 
        //aka: can the army threaten the enemy king?
        List<Piece> army = new ArrayList<Piece>();
        Piece king = null;
        
        if (isWhite) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].toString().equals("WK")) {
                        king = game.textBoard[i][j];
                    }
                    if (game.textBoard[i][j] != null && !game.textBoard[i][j].isWhite()) {
                        army.add(game.textBoard[i][j]);
                    }
                }
            }
        }
        
        else {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].toString().equals("BK")) {
                        king = game.textBoard[i][j];
                    }
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].isWhite()) {
                        army.add(game.textBoard[i][j]);
                    }
                }
            }
        }
        
        //for each of the pieces in army, iterate through their legal moves and seeing if any of the moves hits the enemy king
        for (int i = 0; i < army.size(); i++) {
            
            ArrayList<Integer[]> moves;
            
            army.get(i).generateLegalMoves();
            moves = army.get(i).returnLegalMoves();
            
            //System.out.println("Moves size in underCheck is: " + moves.size());
            for (int j = 0; j < moves.size(); j++) {
                
                if (king == null) {
                    System.out.println("somehow king is null.");
                    break;
                }
                if (moves.get(j)[0].equals(king.getX()) && moves.get(j)[1].equals(king.getY())) { //error: king is somehow null!
                    
                    System.out.println("Text check: King is under attack by: " + army.get(i).toString() + 
                            ", at king position: " + king.getX() + ", " + king.getY());
                    gameInfoText.setText("King in check");
                    return true;
                }           
            }
        }
        
        gameInfoText.setText("Stuff");
        return false;
        
    }
    
    /**
     * Checks if the king is checkmated. 
     * @param isWhite the king color to check for 
     * @return true if king is mated
     */
    public boolean isKingMated(boolean isWhite, Piece[][] board) {
        
        //this is the army of the king being checked for mate (aka, can the army stop the mate?)
        List<Piece> army = new ArrayList<Piece>(); 
        
        Piece king = null;
        
        if (isWhite) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].toString().equals("WK")) {
                        king = game.textBoard[i][j];
                    }
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].isWhite()) {
                        army.add(game.textBoard[i][j]);
                    }
                }
            }
        }
        
        else {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    if (game.textBoard[i][j] != null && game.textBoard[i][j].toString().equals("BK")) {
                        king = game.textBoard[i][j];
                    }
                    if (game.textBoard[i][j] != null && !game.textBoard[i][j].isWhite()) {
                        army.add(game.textBoard[i][j]);
                    }
                }
            }
        }
        
        for (int i = 0; i < army.size(); i++) {
            
            ArrayList<Integer[]> moves;
            Piece currentPiece;
            boolean inCheck = true;
            
            System.out.println(army.get(i));
            currentPiece = army.get(i);
            currentPiece.generateLegalMoves();
            moves = currentPiece.returnLegalMoves();
            
            for (int j = 0; j < moves.size(); j++) {
                this.addToStack();
                System.out.println("Inside checkMAte. Getting original black king pos: " + currentPiece.getX() 
                        + ", " + currentPiece.getY());  //currentPiece is somehow not equal to textBoard[][] piece due to stack/undo
                
                //the Piece is cloned here each time so that the original piece's X and Y doesn't get updated
                this.makeMove((Piece)currentPiece.Clone(), moves.get(j)[0], moves.get(j)[1]);

                inCheck = this.isKingUnderCheck(isWhite, game.textBoard);
                if (!inCheck) {
                    System.out.println("King is not mated, piece: " + currentPiece +", escape move: " + moves.get(j)[0] +", " + moves.get(j)[1]);
                    //game.showTextBoard();
                    this.undoBoard(this.boardHistory.pop());
                    return false;
                }
                else {
                    System.out.println("Thus far king is mated.");
                    game.showTextBoard();
                }
                this.undoBoard(this.boardHistory.pop());
            }
            
        }
        System.out.println(isWhite + " King is mated");
        return true;
    }
    
    public Tile getTile(int x, int y) {
        return visualBoard[x][y];
    }
    
    public Stack<Piece[][]> getBoardHistory(){
        return boardHistory;
    }
    
    public void highlightLegalMoves(ArrayList<Integer[]> moves) {
        for (int i = 0; i < moves.size(); i++) {
            this.getTile(moves.get(i)[1], moves.get(i)[0]).highlightOn();
        }
    }
    
    public void clearAllHighlights() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {               
                this.getTile(i, j).highlightOff();
            }
        }
    }
    
    /**
     * Takes in the previous board, and undos both the textual and visual board to that 
     * previous board's state.
     * Since the board is an array of Pieces, we cannot just assign the current 
     * board to the previous board due to the fact that array references to Pieces. 
     * Each piece must be assigned to the old piece.
     * The previous board is a Shallow copy of the board.  
     * @param previousBoard
     */
    public void undoBoard(Piece[][] previousBoard) {
        
        for (int i = 0; i < previousBoard.length; i++) {
            for (int j = 0; j < previousBoard[i].length; j++) {
                Piece piece = previousBoard[i][j];
                Tile tile = this.getTile(j, i);
                //System.out.println(piece);
                //game.textBoard[i][j] = (Piece) previousBoard[i][j].Clone();
                
                //this.visualBoard[i][j] = previousVisualBoard[i][j];
                
                if (piece != null) {
                   // System.out.println("The original textBoard piece is: " + game.textBoard[i][j]);
                    //System.out.println("The previous textBoard piece is: " + previousBoard[i][j]);
                    
                    game.textBoard[i][j] = (Piece) previousBoard[i][j].Clone();
                   // System.out.println("previousBoard piece name: " + previousBoard[i][j].getName());
                    visualBoard[j][i].setPiece(piece.toUnicode(), piece.isWhite());
                    
                    game.textBoard[i][j].setX(previousBoard[i][j].getX());
                    game.textBoard[i][j].setOldX(previousBoard[i][j].getOldX());
                    game.textBoard[i][j].setY(previousBoard[i][j].getY());
                    game.textBoard[i][j].setOldY(previousBoard[i][j].getOldY());
                    game.textBoard[i][j].setTile(tile);
                    
                    tile.updatePiece(piece);
                    tile.returnPiece().setUnicode(piece.toUnicode());
                    tile.returnPiece().setPieceName(piece.toString()); 
                }
                else {
                    game.textBoard[i][j] = null;
                    visualBoard[j][i].setPiece("", true);
                    tile.updatePiece(null);
                    tile.setPiece("", true);
                } 
            }
        }
        
        System.out.println("Finished UNDO board. New board looks like: ");
       /* System.out.println("Getting original black king pos: " + game.textBoard[0][4].getX() 
                + ", " + game.textBoard[0][4].getY()); */
        game.showTextBoard();
    }
    
    /**
     * Fully moves a Piece to its new location.
     * The piece is moved textually AND visually. 
     * This is done by changing the attributes of the Piece AND the Tile the piece is on
     * and then updating the Piece[][] array and Tile[][] Array so that its element refers to 
     * the correct Piece/Tile object
     *   
     * @param piece
     * @param x 
     * @param y
     */
    public void makeMove(Piece piece, int x, int y) {
        
        //Stores the piece's coordinates before moving
        int oldPieceX = piece.getX();
        int oldPieceY = piece.getY();
        Tile oldTile = piece.getTile();
        
        System.out.println("Inside makeMove, coordinates of piece being moving: " + oldPieceX + ", " + oldPieceY);
        
        //Sets the piece's new coordinates
        piece.makeMove(x, y);
        
        //Gets the tile of the piece's new coordinates 
        piece.setTile(this.getTile(y, x));
        Tile tile = piece.getTile();
        
        //Sets the piece to the tile that the piece coordinate is referring to
        tile.updatePiece(piece);
        tile.returnPiece().setUnicode(piece.toUnicode());
        tile.returnPiece().setPieceName(piece.toString()); 
        
        /*The element in the index of the array that matches the coordinate of the Piece is updated to reference to that Piece,
        while the element in the index that matches the old coordinates of the Piece is set to null. 
        Both the textual board and the visual board gets updated here.*/
        game.textBoard[tile.getX()][tile.getY()] = piece; //tile.getX here
        game.textBoard[oldPieceX][oldPieceY] = null;
        System.out.println("Setting " + oldPieceX + ", " + oldPieceY + " to null");
        //problem: the king has its oldPieceX/Y to its previous move, even after undo, but we want it to be the original oldPieceX
        
        visualBoard[tile.getY()][tile.getX()]
                .setPiece(piece.toUnicode(), 
                        piece.isWhite());
        visualBoard[oldPieceY][oldPieceX].setPiece(null, true);
        visualBoard[oldPieceY][oldPieceX].updatePiece(null);
        oldTile.updatePiece(null);
        oldTile.setPiece(null, isWhite);
        System.out.println("textboard after moving: ");
        game.showTextBoard();
    }
    
    /**
     * This method will iterate over each of the possible moves for a certain piece, and see
     * if moving the piece will result in its own king being checked.  If so, that means the 
     * piece is PINNED and thus that move cannot be a legal move (it will get removed)
     * @param validMoves
     * @param piece
     * @return
     */
    public ArrayList<Integer[]> checkLegalMovesForPin(ArrayList<Integer[]> validMoves, Piece piece) {
        
        System.out.println("size before removing " + validMoves.size());
        for (int i = 0; i < validMoves.size(); i++) {
            this.addToStack();
            this.makeMove((Piece)piece.Clone(), validMoves.get(i)[0], validMoves.get(i)[1]);
            if (isKingUnderCheck(piece.isWhite(), game.textBoard)) {
                System.out.println("legal moves moved into check");
                System.out.println("removing: " + validMoves.get(i)[0] + ", " + validMoves.get(i)[1]);
                validMoves.remove(i);
                i--; //very important for logic purposes since we are removing an element in the array!
            }
            this.undoBoard(boardHistory.pop());
        }
        this.legalMoves = validMoves;
        System.out.println("size after removing " + legalMoves.size());
        return legalMoves;
    } 
    
}
