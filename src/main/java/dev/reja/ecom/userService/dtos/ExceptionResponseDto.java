package dev.reja.ecom.userService.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDto {

    private String message;
    private int code;

    public ExceptionResponseDto(String message,int code){
        this.message=message;
        this.code=code;
    }
}
