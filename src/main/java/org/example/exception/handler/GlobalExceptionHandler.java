package org.example.exception.handler;

import org.example.exception.MoreTeamMembersNotAllowed;
import org.example.dto.InfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MoreTeamMembersNotAllowed.class)
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
}
