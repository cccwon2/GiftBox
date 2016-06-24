package me.memorytalk.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.common.constant.GlobalConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({
            ArithmeticException.class, IllegalArgumentException.class, IllegalStateException.class, IIOException.class,
            IndexOutOfBoundsException.class, InvalidKeyException.class, IOException.class,
            JsonParseException.class, JsonMappingException.class, NullPointerException.class,
            ParseException.class, StorageException.class, URISyntaxException.class
    })
    public ResponseEntity<?> handleParseException(Exception e) {
        if(e.getCause() == null)
            return handleException(e.getMessage(), HttpStatus.BAD_REQUEST);
        else
            return handleException(e.getMessage(), e.getCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ConflictException.class })
    protected ResponseEntity<RestResponse> handleConflict(Exception e) {
        if(e.getCause() == null)
            return handleException(e.getMessage(), HttpStatus.CONFLICT);
        else
            return handleException(e.getMessage(), e.getCause().getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<RestResponse> handleRuntimeException(Exception e) {
        if(e.getCause() == null)
            return handleException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return handleException(e.getMessage(), e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { NotImplementedException.class })
    protected ResponseEntity<RestResponse> handleNotImplementedException(Exception e) {
        if(e.getCause() == null)
            return handleException(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        else
            return handleException(e.getMessage(), e.getCause().getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }


    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<RestResponse> handleUnknownException(Exception e) {
        if(e.getCause() == null)
            return new ResponseEntity<>(new RestResponse(Boolean.FALSE,
                    GlobalConst.DEFAULT_ERROR_MESSAGE, null), HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new RestResponse(Boolean.FALSE,
                    GlobalConst.DEFAULT_ERROR_MESSAGE, e.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<RestResponse> handleException(String message, HttpStatus status) {
        LOGGER.error(message);
        return new ResponseEntity<>(new RestResponse(Boolean.FALSE, message, null), status);
    }

    private ResponseEntity<RestResponse> handleException(String message, String data, HttpStatus status) {
        LOGGER.error(message);
        return new ResponseEntity<>(new RestResponse(Boolean.FALSE, message, data), status);
    }
}
