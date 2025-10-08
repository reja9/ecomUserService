package dev.reja.ecom.userService.exceptions;

public class UnauthorizedEsception extends  RuntimeException{
    public UnauthorizedEsception(String message){
        super(message);
    }
}
