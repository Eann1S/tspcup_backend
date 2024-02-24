package org.example.exception.handler;

import org.example.exception.AccountAlreadyExistsException;
import org.example.exception.TeamCapacityExceededException;
import org.example.dto.InfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMap = getErrorsMapFromException(exception);
        return new ResponseEntity<>(errorsMap, BAD_REQUEST);
    }

    @ExceptionHandler({TeamCapacityExceededException.class, AccountAlreadyExistsException.class})
    public ResponseEntity<InfoDto> handleBadRequestException(Exception e) {
        return createErrorResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InfoDto> handleException(Exception e) {
        return createErrorResponse(e, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<InfoDto> createErrorResponse(Exception e, HttpStatus status) {
        return new ResponseEntity<>(
                new InfoDto(e.getMessage()),
                status
        );
    }

    private Map<String, String> getErrorsMapFromException(MethodArgumentNotValidException exception) {
        List<ObjectError> objectErrors = getObjectErrorsFromException(exception);
        return mapObjectErrorsWithMessages(objectErrors);
    }

    private List<ObjectError> getObjectErrorsFromException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return bindingResult.getAllErrors();
    }

    @SuppressWarnings("DataFlowIssue")
    private Map<String, String> mapObjectErrorsWithMessages(List<ObjectError> objectErrors) {
        return objectErrors.stream()
                .map(objectError -> (FieldError) objectError)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
