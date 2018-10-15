package uk.ac.reigate.exceptions;

public class NotFoundException extends ApiException {
    
    private int code;
    
    public NotFoundException() {
        this(404, "Not Found");
    }
    
    public NotFoundException(String msg) {
        super(404, msg);
    }
    
    public NotFoundException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
