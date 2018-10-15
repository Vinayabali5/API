package uk.ac.reigate.exceptions;

public class DataAlreadyExistsException extends ApiException {
    
    private int code;
    
    public DataAlreadyExistsException() {
        this(409, "Data Alreay Exists");
    }
    
    public DataAlreadyExistsException(String msg) {
        super(409, msg);
    }
    
    public DataAlreadyExistsException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
