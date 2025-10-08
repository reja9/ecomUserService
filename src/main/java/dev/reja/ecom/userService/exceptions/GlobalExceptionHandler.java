package dev.reja.ecom.userService.exceptions;

import dev.reja.ecom.userService.dtos.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedEsception.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedEsception e){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(e.getMessage(),401);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.UNAUTHORIZED);
    }
}
