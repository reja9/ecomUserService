package dev.reja.ecom.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name ="Ecom_user")
@Getter
@Setter
public class User extends BaseModel{

    private  String name;
    private String email;
    private String contactNo;
    private String password;
    private String userName;

    @ManyToMany
    private Set<Role> roles=new HashSet<>();

}
