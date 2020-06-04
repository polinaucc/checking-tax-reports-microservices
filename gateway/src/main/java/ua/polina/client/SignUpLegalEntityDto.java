package ua.polina.client;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpLegalEntityDto {
    @NotBlank
    @Size(max=40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 3, max=20)
    private String name;

    @NotBlank
    @Size(min = 8, max = 8 )
    private String edrpou;

    @NotBlank
    @Size(min =6 , max = 6)
    private String mfo;

    private String address;

    private Long inspectorId;
}
