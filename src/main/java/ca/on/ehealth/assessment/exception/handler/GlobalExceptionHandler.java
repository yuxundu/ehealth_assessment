package ca.on.ehealth.assessment.exception.handler;

import ca.on.ehealth.assessment.exception.ErrorMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Process Global controller exception
 *
 * @author yuxundu
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Constraint violation exception handler
     *
     * @param ex ConstraintViolationException
     * @return List<ValidationError> - list of ValidationError built
     * from set of ConstraintViolation
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object>  handleConstraintViolation(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        if (ex instanceof ConstraintViolationException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            return this.handleConstraintViolation((ConstraintViolationException)ex, headers, status, request);
        }
        else {
            throw ex;
        }
    }

    protected ResponseEntity<Object> handleConstraintViolation( ConstraintViolationException ex, HttpHeaders headers,HttpStatus status,WebRequest request){
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<String> errors = violations.
                stream()
                .map(violation ->
                        Objects.requireNonNull(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                        .reduce((first, second) -> second)
                                        .orElse(null))
                        + ":" + violation.getMessage())
                .collect(Collectors.toList());
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, headers, status);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ":" + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ": " + objectError.getDefaultMessage();
            errors.add(error);
        }
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ErrorMessage errorMessage = new ErrorMessage(unsupported, supported);
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        String msg = null;
        Throwable cause = ex.getCause();

        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            msg = jpe.getOriginalMessage();
        }

        // special case of JsonMappingException below, too much class detail in error messages
        else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if (mie.getPath() != null && mie.getPath().size() > 0) {
                msg = "Invalid request field: " + mie.getPath().get(0).getFieldName();
            }

            // just in case, haven't seen this condition
            else {
                msg = "Invalid request message";
            }
        }

        else if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            msg = jme.getOriginalMessage();
            if (jme.getPath() != null && jme.getPath().size() > 0) {
                msg = "Invalid request field: " + jme.getPath().get(0).getFieldName() +
                        ": " + msg;
            }
        }
        ErrorMessage errorMessage = new ErrorMessage(msg);
        return new ResponseEntity<>(errorMessage, headers, status);
    }

}
