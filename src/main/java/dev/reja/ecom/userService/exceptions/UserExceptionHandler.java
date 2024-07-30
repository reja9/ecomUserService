package dev.reja.ecom.userService.exceptions;

import dev.reja.ecom.userService.controllers.UserController;
import dev.reja.ecom.userService.dtos.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(RandomException.class)
    public ResponseEntity handleRandomException(RandomException e){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(e.getMessage(),404);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(RandomException e){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(e.getMessage(),404);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }


}
