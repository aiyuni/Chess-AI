package chessAI;

import Pieces.Piece;

/**
 * Helper method that returns 2 different objects using Generics
 * @author Perry
 *
 * @param <U>
 * @param <V>
 */
public class MultiReturn<U, V> {
    
    private U objectOne;
    private V objectTwo;
    
    public MultiReturn(U objectOne, V objectTwo){
        this.objectOne = objectOne;
        this.objectTwo = objectTwo;
    }
    
    public U getFirst() {
        return objectOne;
    }
    
    public V getSecond() {
        return objectTwo;
    }

}
