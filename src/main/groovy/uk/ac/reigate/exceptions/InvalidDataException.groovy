package uk.ac.reigate.exceptions;

public class InvalidDataException extends ApiException {
    
    private int code;
    
    public InvalidDataException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
    
    public InvalidDataException() {
        this(400, "Invalid Data Provided");
    }
}
