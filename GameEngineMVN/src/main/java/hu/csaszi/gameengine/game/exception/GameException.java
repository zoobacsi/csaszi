package hu.csaszi.gameengine.game.exception;

public class GameException extends Exception {

    public GameException(String message){
        super(message);
    }

    public GameException(String message, Throwable throwable){
        super(message, throwable);
    }

    public GameException(Throwable throwable){
        super(throwable);
    }
}
