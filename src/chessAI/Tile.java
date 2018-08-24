package chessAI;

import Pieces.Piece;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

    public class Tile extends StackPane implements Cloneable {
        
        int x;
        int y;
        String unicode;
        Text text = new Text();
        Circle circle = new Circle(25);
        Rectangle border;
        boolean isWhite;
        Piece piece;
        boolean highlighted = false;
        
        
        public Tile(String unicode, boolean isWhite, int x, int y) {
            
            this.x = x;
            this.y = y;
            this.unicode = unicode;
            this.isWhite = isWhite;
            border = new Rectangle(60,60);
            border.setFill(Color.WHITE);
            border.setStroke(Color.BLACK);
            text.setText(unicode);
            text.setFont(Font.font(30));
            if (this.isWhite) {
               text.setFill(Color.ORANGERED);
            }
            else {
                text.setFill(Color.BLACK);
            }

            getChildren().addAll(border, text);
            
        }
        
        public void setPiece(String unicode, boolean isWhite) {
            
            this.unicode = unicode;
            this.isWhite = isWhite;
            text.setText(unicode);
            if (this.isWhite) {
                text.setFill(Color.ORANGERED);
             }
             else {
                 text.setFill(Color.BLACK);
             }
        }
        
        public void updatePiece(Piece piece) {
            this.piece = piece;
        }
        
        public Piece returnPiece() {
            return piece;
        }
        
        public void highlightOn() {
            text.setFill(Color.YELLOWGREEN);
            border.setStroke(Color.YELLOWGREEN);
            highlighted = true;
        }
        
        public void highlightOff() {
            border.setStroke(Color.BLACK);
            if (this.isWhite) {
                text.setFill(Color.ORANGERED);
            }
            else {
                text.setFill(Color.BLACK);
            }
            highlighted = false;
        }
        
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public String getUnicode() {
            return unicode;
        }
        public boolean isWhite() {
            return isWhite;
        }
        
        public String toString() {
            return unicode;
        }
        public boolean returnHighlight() {
            return highlighted;
        }
        
        public Object Clone() {
            Tile clonedTile = null;
            try {
                clonedTile = (Tile) super.clone();
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return clonedTile;
        }
}
