package dev.reja.ecom.userService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SendUserRolesDto {
    List<UUID> roleIds;
}
