package ua.polina.client;

import lombok.Data;


@Data
public class LegalEntity {
    private Long id;
    private String name;
    private String edrpou;
    private String mfo;
    private String address;
    private Long clientId;

    @Override
    public String toString() {
        return name;
    }
}
