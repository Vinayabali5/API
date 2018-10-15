package uk.ac.reigate.exceptions;

public class ApiException extends RuntimeException {
    
    private int code;
    
    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
