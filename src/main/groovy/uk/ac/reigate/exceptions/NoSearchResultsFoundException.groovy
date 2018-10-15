package uk.ac.reigate.exceptions;

public class NoSearchResultsFoundException extends ApiException {
    
    private int code;
    
    public NoSearchResultsFoundException() {
        this(404, "No search results found that match the criteria provided.");
    }
}
