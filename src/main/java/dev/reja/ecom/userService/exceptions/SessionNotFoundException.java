package dev.reja.ecom.userService.exceptions;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String message){
        super(message);
    }
}
