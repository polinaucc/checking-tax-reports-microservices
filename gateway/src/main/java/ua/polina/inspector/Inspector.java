package ua.polina.inspector;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Inspector {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate employmentDate;
    private Long userId;
}
