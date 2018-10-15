package uk.ac.reigate.exceptions

class EdiCreationException extends ApiException {
    
    public EdiCreationException(int code, String msg) {
        super(code, msg);
    }
    
    public EdiCreationException(String msg) {
        this(400, msg);
    }
    
    public EdiCreationException() {
        this(400, "Failed to create EDI File");
    }
}
