package uk.ac.reigate.dto


class ErrorMessageDto {
    
    String message
    
    String devMessage
    
    ErrorMessageDto(String message, String devMessage) {
        this.message = message;
        this.devMessage = devMessage;
    }
}
