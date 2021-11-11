package HealthDeclaration.modal.request;

import lombok.Data;

@Data
public class UserChangePassForm {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
