package ua.polina.inspector;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class InspectorDto {

    @NotBlank
    @Size(max = 40)
    @Email
    private String userEmail;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    private String surname;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String secondName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate employmentDate;
}
