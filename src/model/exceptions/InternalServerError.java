package model.exceptions;

public class InternalServerError  extends RuntimeException{
    public InternalServerError(String message) {
        super(message);
    }
}
