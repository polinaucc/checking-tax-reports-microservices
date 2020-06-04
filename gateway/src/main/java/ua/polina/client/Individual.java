package ua.polina.client;

import lombok.Data;


@Data
public class Individual {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String passport;
    private String identCode;
    private Long clientId;
}
