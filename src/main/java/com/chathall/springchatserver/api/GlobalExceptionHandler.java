package com.chathall.springchatserver.api;

import com.chathall.springchatserver.exceptions.CustomAppException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<ProblemDetail> handleCustomApp(CustomAppException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getHttpStatus());

        problemDetail.setTitle(ex.getTitle());
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus().value()).body(problemDetail);
    }
}
