package ua.polina.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpIndividualDto {
    @NotBlank
    @Size(max=40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    @NotBlank
    @Size(min=3, max=20)
    private String surname;

    @NotBlank
    @Size(min=3, max=20)
    private String firstName;

    @NotBlank
    @Size(min=3, max=20)
    private String secondName;

    @NotBlank
    @Pattern(regexp = "^[А-Я]{2}[0-9]{6}$", message = "Illegal passport!")
    private String passport;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Illegal identification code")
    private String identCode;

    private String address;

    private Long inspectorId;
}
