package guru.springframework.msscbeerservice.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(final ConstraintViolationException exception) {
        final var error = exception.getConstraintViolations().stream()
            .map(cv -> cv.getPropertyPath() + " : " + cv.getMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> validationErrorHandler(final MethodArgumentNotValidException e) {
        final var errors = e.getBindingResult().getAllErrors().stream().map(FieldError.class::cast)
            .map(fieldError -> String.format("Bad Request %s : %s : Rejected value : ---> %s", fieldError.getField(),
                fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
            .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> bindException(final BindException e) {
        return new ResponseEntity<>(e.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
