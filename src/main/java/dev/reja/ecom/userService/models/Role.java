package dev.reja.ecom.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseModel{
    private String role;
}
