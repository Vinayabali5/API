package uk.ac.reigate.exceptions;

public class MappingException extends ApiException {
    
    private int code;
    
    public MappingException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
    
    public MappingException() {
        this(400, "Invalid Data Provided");
    }
}
