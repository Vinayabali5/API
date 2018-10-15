package uk.ac.reigate.api.exceptionhandling

import javax.servlet.http.HttpServletRequest

import org.apache.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import uk.ac.reigate.dto.ErrorDto
import uk.ac.reigate.exceptions.EdiCreationException

@ControllerAdvice
class EdiCreationExceptionHandler {
    
    private final static Logger LOGGER = Logger.getLogger(EdiCreationExceptionHandler.class.getName());
    
    @ExceptionHandler(value = EdiCreationException.class)
    public ResponseEntity<ErrorDto> handleEdiCreateionException(HttpServletRequest req, Exception exception) {
        LOGGER.error("EE EdiCreationExceptionHandler - handleEdiCreateionException");
        EdiCreationException ex = (EdiCreationException) exception
        ResponseEntity<ErrorDto> response = new ResponseEntity<ErrorDto> (new ErrorDto(code: ex.code != null ? ex.code : 404, message: ex.message != null ? ex.message : "Not Found") , HttpStatus.INTERNAL_SERVER_ERROR)
        return response
    }
}
