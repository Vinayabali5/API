package uk.ac.reigate.api.exceptionhandling

import javax.naming.CommunicationException
import javax.servlet.http.HttpServletRequest

import org.apache.log4j.Logger

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import uk.ac.reigate.dto.ErrorDto
import uk.ac.reigate.exceptions.DataAlreadyExistsException
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.MappingException
import uk.ac.reigate.exceptions.NotFoundException

@ControllerAdvice
public class GenericExceptionHandlingApi {
    
    private final static Logger LOGGER = Logger.getLogger(GenericExceptionHandlingApi.class.getName());
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleErrorNotFoundException(HttpServletRequest req, Exception exception) {
        LOGGER.info("** ExceptionHandlingApi -handleErrorNotFoundException ");
        NotFoundException ex = (NotFoundException) exception
        return new ResponseEntity<ErrorDto> (new ErrorDto(code: ex.code != null ? ex.code : 404, message: ex.message != null ? ex.message : "Not Found"), HttpStatus.NOT_FOUND)
    }
    
    @ExceptionHandler([
        InvalidDataException.class,
        MappingException.class,
        HttpMessageNotReadableException.class
    ])
    public ResponseEntity<ErrorDto> handleErrorMappingException(HttpServletRequest req, Exception exception) {
        LOGGER.info("** ExceptionHandlingApi - handleErrorMappingException ");
        return new ResponseEntity<ErrorDto> (new ErrorDto(code: 400, message: "Invalid Data Supplied"), HttpStatus.BAD_REQUEST)
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleErrorHttpRequestMethodNotSupportedException(HttpServletRequest req, Exception exception) {
        LOGGER.info("** ExceptionHandlingApi -handleErrorHttpRequestMethodNotSupportedException ");
        return new ResponseEntity<ErrorDto> (new ErrorDto(code: 405, message: "Method Not Supported"), HttpStatus.METHOD_NOT_ALLOWED)
    }
    
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleErrorDataAlreadyExistsException(HttpServletRequest req, Exception exception) {
        LOGGER.info("** ExceptionHandlingApi -handleErrorException");
        DataAlreadyExistsException ex = (DataAlreadyExistsException) exception
        return new ResponseEntity<ErrorDto> (new ErrorDto(code: 409, message: ex.message != null ? ex.message : "Data Already Exists"), HttpStatus.CONFLICT)
    }
    
    /**
     * This method is used as an exception handler for communication errors. This is a problem that can occur when the API server cannot communicate with 
     * one of the other required server (typically the LDAP server).
     * 
     * @param req the HttpServletRequest that triggered the exception
     * @param exception the Exception itself
     * @return a ResponseEntity informing the user of the problem
     */
    @ExceptionHandler(CommunicationException.class)
    public ResponseEntity<ErrorDto> handleErrorCommunicationException(HttpServletRequest req, Exception exception) {
        LOGGER.info("** ExceptionHandlingApi - handleErrorException");
        CommunicationException ex = (CommunicationException) exception
        return new ResponseEntity<ErrorDto> (new ErrorDto(code: 666, message: ex.message != null ? ex.message : "Error communicating with server."), HttpStatus.SERVICE_UNAVAILABLE)
    }
    
    //    @ExceptionHandler
    //    public ResponseEntity<ErrorDto> handleErrorException(HttpServletRequest req, Exception exception) {
    //        LOGGER.info("** ExceptionHandlingApi -handleErrorException");
    //        exception.printStackTrace()
    //        return new ResponseEntity<ErrorDto> (new ErrorDto(code: 500, message: "Unknown Error Occurred"), HttpStatus.INTERNAL_SERVER_ERROR)
    //    }
}