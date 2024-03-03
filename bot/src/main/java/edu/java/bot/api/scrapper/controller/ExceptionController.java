package edu.java.bot.api.scrapper.controller;

import edu.java.bot.api.scrapper.dto.response.ErrorResponseDto;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleException(Throwable throwable) {

        HttpStatus httpStatus;
        String message;

        if (throwable instanceof MethodArgumentNotValidException e) {

            httpStatus = HttpStatus.BAD_REQUEST;
            message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));

        } else {

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = throwable.getMessage();

        }

        return ResponseEntity.status(httpStatus)
            .body(
                ErrorResponseDto.builder()
                    .code(httpStatus.value())
                    .message(message)
                    .build()
            );
    }

}
